package org.bukkit.unrealized.craftbukkit.inventory;

import net.minecraft.world.IInventory;
import org.bukkit.unrealized.inventory.LoomInventory;

public class CraftInventoryLoom extends CraftResultInventory implements LoomInventory {

    public CraftInventoryLoom(IInventory inventory, IInventory resultInventory) {
        super(inventory, resultInventory);
    }
}
