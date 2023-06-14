package org.bukkit.unrealized.event.inventory;

import org.bukkit.unrealized.block.Block;
import org.bukkit.unrealized.event.block.BlockCookEvent;
import org.bukkit.unrealized.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Called when an ItemStack is successfully smelted in a furnace.
 */
public class FurnaceSmeltEvent extends BlockCookEvent {

    public FurnaceSmeltEvent(@NotNull final Block furnace, @NotNull final ItemStack source, @NotNull final ItemStack result) {
        super(furnace, source, result);
    }
}
