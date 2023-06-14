package org.bukkit.unrealized.event.inventory;

import org.bukkit.unrealized.event.HandlerList;
import org.bukkit.unrealized.inventory.GrindstoneInventory;
import org.bukkit.unrealized.inventory.InventoryView;
import org.bukkit.unrealized.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Called when an item is put in a slot for repair or unenchanting in a grindstone.
 */
public class PrepareGrindstoneEvent extends PrepareInventoryResultEvent {

    private static final HandlerList handlers = new HandlerList();

    public PrepareGrindstoneEvent(@NotNull InventoryView inventory, @Nullable ItemStack result) {
        super(inventory, result);
    }

    @NotNull
    @Override
    public GrindstoneInventory getInventory() {
        return (GrindstoneInventory) super.getInventory();
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
