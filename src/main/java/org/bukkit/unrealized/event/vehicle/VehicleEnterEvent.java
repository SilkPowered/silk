package org.bukkit.unrealized.event.vehicle;

import org.bukkit.entity.Entity;
import org.bukkit.unrealized.entity.Vehicle;
import org.bukkit.unrealized.event.Cancellable;
import org.bukkit.unrealized.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Raised when an entity enters a vehicle.
 */
public class VehicleEnterEvent extends VehicleEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private final Entity entered;

    public VehicleEnterEvent(@NotNull final Vehicle vehicle, @NotNull final Entity entered) {
        super(vehicle);
        this.entered = entered;
    }

    /**
     * Gets the Entity that entered the vehicle.
     *
     * @return the Entity that entered the vehicle
     */
    @NotNull
    public Entity getEntered() {
        return entered;
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
