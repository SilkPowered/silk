package org.bukkit.unrealized.craftbukkit.inventory;

import net.minecraft.world.IInventory;
import org.bukkit.unrealized.inventory.CartographyInventory;

public class CraftInventoryCartography extends CraftResultInventory implements CartographyInventory {

    public CraftInventoryCartography(IInventory inventory, IInventory resultInventory) {
        super(inventory, resultInventory);
    }
}
