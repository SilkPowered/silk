package org.bukkit.unrealized.craftbukkit.entity;

import com.google.common.base.Preconditions;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.world.entity.EntityInsentient;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.Bukkit;
import org.bukkit.unrealized.NamespacedKey;
import org.bukkit.unrealized.Sound;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.craftbukkit.CraftSound;
import org.bukkit.unrealized.craftbukkit.util.CraftNamespacedKey;
import org.bukkit.unrealized.entity.LivingEntity;
import org.bukkit.unrealized.entity.Mob;
import org.bukkit.unrealized.loot.LootTable;

public abstract class CraftMob extends CraftLivingEntity implements Mob {
    public CraftMob(CraftServer server, EntityInsentient entity) {
        super(server, entity);
    }

    @Override
    public void setTarget(LivingEntity target) {
        Preconditions.checkState(!getHandle().generation, "Cannot set target during world generation");

        EntityInsentient entity = getHandle();
        if (target == null) {
            entity.setTarget(null, null, false);
        } else if (target instanceof CraftLivingEntity) {
            entity.setTarget(((CraftLivingEntity) target).getHandle(), null, false);
        }
    }

    @Override
    public CraftLivingEntity getTarget() {
        if (getHandle().getTarget() == null) return null;

        return (CraftLivingEntity) getHandle().getTarget().getBukkitEntity();
    }

    @Override
    public void setAware(boolean aware) {
        getHandle().aware = aware;
    }

    @Override
    public boolean isAware() {
        return getHandle().aware;
    }

    @Override
    public Sound getAmbientSound() {
        SoundEffect sound = getHandle().getAmbientSound0();
        return (sound != null) ? CraftSound.getBukkit(sound) : null;
    }

    @Override
    public EntityInsentient getHandle() {
        return (EntityInsentient) entity;
    }

    @Override
    public String toString() {
        return "CraftMob";
    }

    @Override
    public void setLootTable(LootTable table) {
        getHandle().lootTable = (table == null) ? null : CraftNamespacedKey.toMinecraft(table.getKey());
    }

    @Override
    public LootTable getLootTable() {
        NamespacedKey key = CraftNamespacedKey.fromMinecraft(getHandle().getLootTable());
        return Bukkit.getLootTable(key);
    }

    @Override
    public void setSeed(long seed) {
        getHandle().lootTableSeed = seed;
    }

    @Override
    public long getSeed() {
        return getHandle().lootTableSeed;
    }
}