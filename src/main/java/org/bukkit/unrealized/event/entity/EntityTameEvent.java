package org.bukkit.unrealized.event.entity;

import org.bukkit.unrealized.entity.AnimalTamer;
import org.bukkit.unrealized.entity.LivingEntity;
import org.bukkit.unrealized.event.Cancellable;
import org.bukkit.unrealized.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Thrown when a LivingEntity is tamed
 */
public class EntityTameEvent extends EntityEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private final AnimalTamer owner;

    public EntityTameEvent(@NotNull final LivingEntity entity, @NotNull final AnimalTamer owner) {
        super(entity);
        this.owner = owner;
    }

    @NotNull
    @Override
    public LivingEntity getEntity() {
        return (LivingEntity) entity;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    /**
     * Gets the owning AnimalTamer
     *
     * @return the owning AnimalTamer
     */
    @NotNull
    public AnimalTamer getOwner() {
        return owner;
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
