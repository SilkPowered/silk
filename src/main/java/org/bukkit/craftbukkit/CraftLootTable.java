package org.bukkit.craftbukkit;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.craftbukkit.util.CraftLocation;
import org.bukkit.craftbukkit.util.RandomSourceWrapper;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;

public class CraftLootTable implements org.bukkit.loot.LootTable {

    private final LootTable handle;
    private final NamespacedKey key;

    public CraftLootTable(NamespacedKey key, LootTable handle) {
        this.handle = handle;
        this.key = key;
    }

    public LootTable getHandle() {
        return handle;
    }

    @Override
    public Collection<ItemStack> populateLoot(Random random, LootContext context) {
        Preconditions.checkArgument(context != null, "LootContext cannot be null");
        LootContextParameterSet nmsContext = convertContext(context, random);
        List<net.minecraft.item.ItemStack> nmsItems = handle.getRandomItems(nmsContext);
        Collection<ItemStack> bukkit = new ArrayList<>(nmsItems.size());

        for (net.minecraft.item.ItemStack item : nmsItems) {
            if (item.isEmpty()) {
                continue;
            }
            bukkit.add(CraftItemStack.asBukkitCopy(item));
        }

        return bukkit;
    }

    @Override
    public void fillInventory(Inventory inventory, Random random, LootContext context) {
        Preconditions.checkArgument(inventory != null, "Inventory cannot be null");
        Preconditions.checkArgument(context != null, "LootContext cannot be null");
        LootContextParameterSet nmsContext = convertContext(context, random);
        CraftInventory craftInventory = (CraftInventory) inventory;
        net.minecraft.inventory.Inventory handle = craftInventory.getInventory();

        // TODO: When events are added, call event here w/ custom reason?
        getHandle().fillInventory(handle, nmsContext, random.nextLong(), true);
    }

    @Override
    public NamespacedKey getKey() {
        return key;
    }

    private LootContextParameterSet convertContext(LootContext context, Random random) {
        Preconditions.checkArgument(context != null, "LootContext cannot be null");
        Location loc = context.getLocation();
        Preconditions.checkArgument(loc.getWorld() != null, "LootContext.getLocation#getWorld cannot be null");
        ServerWorld handle = ((CraftWorld) loc.getWorld()).getHandle();

        LootContextParameterSet.a builder = new LootContextParameterSet.a(handle);
        if (random != null) {
            // builder = builder.withRandom(new RandomSourceWrapper(random));
        }
        setMaybe(builder, LootContextParameters.ORIGIN, CraftLocation.toVec3D(loc));
        if (getHandle() != LootTable.EMPTY) {
            // builder.luck(context.getLuck());

            if (context.getLootedEntity() != null) {
                Entity nmsLootedEntity = ((CraftEntity) context.getLootedEntity()).getHandle();
                setMaybe(builder, LootContextParameters.THIS_ENTITY, nmsLootedEntity);
                setMaybe(builder, LootContextParameters.DAMAGE_SOURCE, handle.damageSources().generic());
                setMaybe(builder, LootContextParameters.ORIGIN, nmsLootedEntity.position());
            }

            if (context.getKiller() != null) {
                PlayerEntity nmsKiller = ((CraftHumanEntity) context.getKiller()).getHandle();
                setMaybe(builder, LootContextParameters.KILLER_ENTITY, nmsKiller);
                // If there is a player killer, damage source should reflect that in case loot tables use that information
                setMaybe(builder, LootContextParameters.DAMAGE_SOURCE, handle.damageSources().playerAttack(nmsKiller));
                setMaybe(builder, LootContextParameters.LAST_DAMAGE_PLAYER, nmsKiller); // SPIGOT-5603 - Set minecraft:killed_by_player
                setMaybe(builder, LootContextParameters.TOOL, nmsKiller.getUseItem()); // SPIGOT-6925 - Set minecraft:match_tool
            }

            // SPIGOT-5603 - Use LootContext#lootingModifier
            if (context.getLootingModifier() != LootContext.DEFAULT_LOOT_MODIFIER) {
                setMaybe(builder, LootContextParameters.LOOTING_MOD, context.getLootingModifier());
            }
        }

        // SPIGOT-5603 - Avoid IllegalArgumentException in LootTableInfo#build()
        LootContextType.Builder nmsBuilder = new LootContextType.Builder();
        for (LootContextParameter<?> param : getHandle().getParamSet().getRequired()) {
            nmsBuilder.required(param);
        }
        for (LootContextParameter<?> param : getHandle().getParamSet().getAllowed()) {
            if (!getHandle().getParamSet().getRequired().contains(param)) {
                nmsBuilder.optional(param);
            }
        }
        nmsBuilder.optional(LootContextParameters.LOOTING_MOD);

        return builder.create(getHandle().getParamSet());
    }

    private <T> void setMaybe(LootContextParameterSet.a builder, LootContextParameter<T> param, T value) {
        if (getHandle().getParamSet().getRequired().contains(param) || getHandle().getParamSet().getAllowed().contains(param)) {
            builder.withParameter(param, value);
        }
    }

    public static LootContext convertContext(net.minecraft.loot.context.LootContext info) {
        Vec3d position = info.getParamOrNull(LootContextParameters.ORIGIN);
        if (position == null) {
            position = info.getParamOrNull(LootContextParameters.THIS_ENTITY).position(); // Every vanilla context has origin or this_entity, see LootContextParameterSets
        }
        Location location = CraftLocation.toBukkit(position, info.getLevel().getWorld());
        LootContext.Builder contextBuilder = new LootContext.Builder(location);

        if (info.hasParam(LootContextParameters.KILLER_ENTITY)) {
            CraftEntity killer = info.getParamOrNull(LootContextParameters.KILLER_ENTITY).getBukkitEntity();
            if (killer instanceof CraftHumanEntity) {
                contextBuilder.killer((CraftHumanEntity) killer);
            }
        }

        if (info.hasParam(LootContextParameters.THIS_ENTITY)) {
            contextBuilder.lootedEntity(info.getParamOrNull(LootContextParameters.THIS_ENTITY).getBukkitEntity());
        }

        if (info.hasParam(LootContextParameters.LOOTING_MOD)) {
            contextBuilder.lootingModifier(info.getParamOrNull(LootContextParameters.LOOTING_MOD));
        }

        contextBuilder.luck(info.getLuck());
        return contextBuilder.build();
    }

    @Override
    public String toString() {
        return getKey().toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof org.bukkit.loot.LootTable)) {
            return false;
        }

        org.bukkit.loot.LootTable table = (org.bukkit.loot.LootTable) obj;
        return table.getKey().equals(this.getKey());
    }
}
