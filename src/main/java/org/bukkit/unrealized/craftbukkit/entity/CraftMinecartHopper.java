package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.vehicle.EntityMinecartHopper;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.craftbukkit.inventory.CraftInventory;
import org.bukkit.unrealized.entity.EntityType;
import org.bukkit.unrealized.entity.minecart.HopperMinecart;
import org.bukkit.unrealized.inventory.Inventory;

public final class CraftMinecartHopper extends CraftMinecartContainer implements HopperMinecart {
    private final CraftInventory inventory;

    public CraftMinecartHopper(CraftServer server, EntityMinecartHopper entity) {
        super(server, entity);
        inventory = new CraftInventory(entity);
    }

    @Override
    public String toString() {
        return "CraftMinecartHopper{" + "inventory=" + inventory + '}';
    }

    @Override
    public EntityType getType() {
        return EntityType.MINECART_HOPPER;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public boolean isEnabled() {
        return ((EntityMinecartHopper) getHandle()).isEnabled();
    }

    @Override
    public void setEnabled(boolean enabled) {
        ((EntityMinecartHopper) getHandle()).setEnabled(enabled);
    }
}
