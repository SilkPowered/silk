package org.bukkit.unrealized.event.player;

import org.bukkit.unrealized.block.Lectern;
import org.bukkit.unrealized.entity.Player;
import org.bukkit.unrealized.event.Cancellable;
import org.bukkit.unrealized.event.HandlerList;
import org.bukkit.unrealized.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This event is called when a player clicks the button to take a book of a
 * Lectern. If this event is cancelled the book remains on the lectern.
 */
public class PlayerTakeLecternBookEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    //
    private boolean cancelled;
    private final Lectern lectern;

    public PlayerTakeLecternBookEvent(@NotNull Player who, @NotNull Lectern lectern) {
        super(who);
        this.lectern = lectern;
    }

    /**
     * Gets the lectern involved.
     *
     * @return the Lectern
     */
    @NotNull
    public Lectern getLectern() {
        return lectern;
    }

    /**
     * Gets the current ItemStack on the lectern.
     *
     * @return the ItemStack on the Lectern
     */
    @Nullable
    public ItemStack getBook() {
        return lectern.getInventory().getItem(0);
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
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
