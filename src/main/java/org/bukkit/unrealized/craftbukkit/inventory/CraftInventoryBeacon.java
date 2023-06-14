package org.bukkit.unrealized.craftbukkit.inventory;

import net.minecraft.world.IInventory;
import org.bukkit.unrealized.inventory.BeaconInventory;
import org.bukkit.unrealized.inventory.ItemStack;

public class CraftInventoryBeacon extends CraftInventory implements BeaconInventory {
    public CraftInventoryBeacon(IInventory beacon) {
        super(beacon);
    }

    @Override
    public void setItem(ItemStack item) {
        setItem(0, item);
    }

    @Override
    public ItemStack getItem() {
        return getItem(0);
    }
}
