package org.bukkit.unrealized.craftbukkit.inventory;

import net.minecraft.world.IInventory;
import org.bukkit.unrealized.inventory.GrindstoneInventory;

public class CraftInventoryGrindstone extends CraftResultInventory implements GrindstoneInventory {

    public CraftInventoryGrindstone(IInventory inventory, IInventory resultInventory) {
        super(inventory, resultInventory);
    }
}
