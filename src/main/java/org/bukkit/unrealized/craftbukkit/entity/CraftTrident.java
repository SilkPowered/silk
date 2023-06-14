package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.projectile.EntityThrownTrident;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.unrealized.entity.EntityType;
import org.bukkit.unrealized.entity.Trident;
import org.bukkit.unrealized.inventory.ItemStack;

public class CraftTrident extends CraftArrow implements Trident {

    public CraftTrident(CraftServer server, EntityThrownTrident entity) {
        super(server, entity);
    }

    @Override
    public EntityThrownTrident getHandle() {
        return (EntityThrownTrident) super.getHandle();
    }

    @Override
    public ItemStack getItem() {
        return CraftItemStack.asBukkitCopy(getHandle().tridentItem);
    }

    @Override
    public void setItem(ItemStack itemStack) {
        getHandle().tridentItem = CraftItemStack.asNMSCopy(itemStack);
    }

    @Override
    public String toString() {
        return "CraftTrident";
    }

    @Override
    public EntityType getType() {
        return EntityType.TRIDENT;
    }
}
