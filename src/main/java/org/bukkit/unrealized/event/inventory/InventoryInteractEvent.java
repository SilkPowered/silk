package org.bukkit.unrealized.event.inventory;

import org.bukkit.unrealized.entity.HumanEntity;
import org.bukkit.unrealized.event.Cancellable;
import org.bukkit.unrealized.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;

/**
 * An abstract base class for events that describe an interaction between a
 * HumanEntity and the contents of an Inventory.
 */
public abstract class InventoryInteractEvent extends InventoryEvent implements Cancellable {
    private Result result = Result.DEFAULT;

    public InventoryInteractEvent(@NotNull InventoryView transaction) {
        super(transaction);
    }

    /**
     * Gets the player who performed the click.
     *
     * @return The clicking player.
     */
    @NotNull
    public HumanEntity getWhoClicked() {
        return getView().getPlayer();
    }

    /**
     * Sets the result of this event. This will change whether or not this
     * event is considered cancelled.
     *
     * @param newResult the new {@link Result} for this event
     * @see #isCancelled()
     */
    public void setResult(@NotNull Result newResult) {
        result = newResult;
    }

    /**
     * Gets the {@link Result} of this event. The Result describes the
     * behavior that will be applied to the inventory in relation to this
     * event.
     *
     * @return the Result of this event.
     */
    @NotNull
    public Result getResult() {
        return result;
    }

    /**
     * Gets whether or not this event is cancelled. This is based off of the
     * Result value returned by {@link #getResult()}.  Result.ALLOW and
     * Result.DEFAULT will result in a returned value of false, but
     * Result.DENY will result in a returned value of true.
     * <p>
     * {@inheritDoc}
     *
     * @return whether the event is cancelled
     */
    @Override
    public boolean isCancelled() {
        return getResult() == Result.DENY;
    }

    /**
     * Proxy method to {@link #setResult(Result)} for the Cancellable
     * interface. {@link #setResult(Result)} is preferred, as it allows
     * you to specify the Result beyond Result.DENY and Result.ALLOW.
     * <p>
     * {@inheritDoc}
     *
     * @param toCancel result becomes DENY if true, ALLOW if false
     */
    @Override
    public void setCancelled(boolean toCancel) {
        setResult(toCancel ? Result.DENY : Result.ALLOW);
    }

}