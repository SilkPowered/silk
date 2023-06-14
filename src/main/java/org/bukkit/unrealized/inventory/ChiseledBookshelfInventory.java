package org.bukkit.unrealized.inventory;

import org.bukkit.unrealized.block.ChiseledBookshelf;
import org.jetbrains.annotations.Nullable;

/**
 * Interface to the inventory of a chiseled bookshelf.
 */
public interface ChiseledBookshelfInventory extends Inventory {

    @Nullable
    @Override
    public ChiseledBookshelf getHolder();
}
