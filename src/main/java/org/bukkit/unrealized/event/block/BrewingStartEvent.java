package org.bukkit.unrealized.event.block;

import org.bukkit.unrealized.block.Block;
import org.bukkit.unrealized.event.HandlerList;
import org.bukkit.unrealized.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a brewing stand starts to brew.
 */
public class BrewingStartEvent extends InventoryBlockStartEvent {

    private static final HandlerList handlers = new HandlerList();
    private int brewingTime;

    public BrewingStartEvent(@NotNull final Block furnace, @NotNull ItemStack source, int brewingTime) {
        super(furnace, source);
        this.brewingTime = brewingTime;
    }

    /**
     * Gets the total brew time associated with this event.
     *
     * @return the total brew time
     */
    public int getTotalBrewTime() {
        return brewingTime;
    }

    /**
     * Sets the total brew time for this event.
     *
     * @param brewTime the new total brew time
     */
    public void setTotalBrewTime(int brewTime) {
        this.brewingTime = brewTime;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
