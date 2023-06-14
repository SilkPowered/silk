package org.bukkit.unrealized.craftbukkit.inventory;

import net.minecraft.world.IInventory;
import org.bukkit.unrealized.inventory.StonecutterInventory;

public class CraftInventoryStonecutter extends CraftResultInventory implements StonecutterInventory {

    public CraftInventoryStonecutter(IInventory inventory, IInventory resultInventory) {
        super(inventory, resultInventory);
    }
}
