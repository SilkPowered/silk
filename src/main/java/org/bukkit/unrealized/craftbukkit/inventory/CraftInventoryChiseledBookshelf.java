package org.bukkit.unrealized.craftbukkit.inventory;

import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import org.bukkit.unrealized.block.ChiseledBookshelf;
import org.bukkit.unrealized.inventory.ChiseledBookshelfInventory;

public class CraftInventoryChiseledBookshelf extends CraftInventory implements ChiseledBookshelfInventory {

    public CraftInventoryChiseledBookshelf(ChiseledBookShelfBlockEntity inventory) {
        super(inventory);

    }

    @Override
    public ChiseledBookshelf getHolder() {
        return (ChiseledBookshelf) inventory.getOwner();
    }
}
