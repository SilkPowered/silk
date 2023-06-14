package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.vehicle.EntityMinecartChest;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.craftbukkit.inventory.CraftInventory;
import org.bukkit.unrealized.entity.EntityType;
import org.bukkit.unrealized.entity.minecart.StorageMinecart;
import org.bukkit.unrealized.inventory.Inventory;

@SuppressWarnings("deprecation")
public class CraftMinecartChest extends CraftMinecartContainer implements StorageMinecart {
    private final CraftInventory inventory;

    public CraftMinecartChest(CraftServer server, EntityMinecartChest entity) {
        super(server, entity);
        inventory = new CraftInventory(entity);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public String toString() {
        return "CraftMinecartChest{" + "inventory=" + inventory + '}';
    }

    @Override
    public EntityType getType() {
        return EntityType.MINECART_CHEST;
    }
}
