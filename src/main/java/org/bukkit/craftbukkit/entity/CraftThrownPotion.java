package org.bukkit.craftbukkit.entity;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.util.Collection;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.potion.PotionUtil;
import org.bukkit.Material;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.craftbukkit.potion.CraftPotionUtil;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class CraftThrownPotion extends CraftThrowableProjectile implements ThrownPotion {
    public CraftThrownPotion(CraftServer server, PotionEntity entity) {
        super(server, entity);
    }

    @Override
    public Collection<PotionEffect> getEffects() {
        ImmutableList.Builder<PotionEffect> builder = ImmutableList.builder();
        for (StatusEffectInstance effect : PotionUtil.getPotionEffects(getHandle().getItem())) {
            builder.add(CraftPotionUtil.toBukkit(effect));
        }
        return builder.build();
    }

    @Override
    public ItemStack getItem() {
        return CraftItemStack.asBukkitCopy(getHandle().getItem());
    }

    @Override
    public void setItem(ItemStack item) {
        Preconditions.checkArgument(item != null, "ItemStack cannot be null");
        Preconditions.checkArgument(item.getType() == Material.LINGERING_POTION || item.getType() == Material.SPLASH_POTION, "ItemStack material must be Material.LINGERING_POTION or Material.SPLASH_POTION but was Material.%s", item.getType());

        getHandle().setItem(CraftItemStack.asNMSCopy(item));
    }

    @Override
    public PotionEntity getHandle() {
        return (PotionEntity) entity;
    }
}
