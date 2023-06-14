package org.bukkit.unrealized.event.hanging;

import org.bukkit.unrealized.entity.Hanging;
import org.bukkit.unrealized.event.Event;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a hanging entity-related event.
 */
public abstract class HangingEvent extends Event {
    protected Hanging hanging;

    protected HangingEvent(@NotNull final Hanging painting) {
        this.hanging = painting;
    }

    /**
     * Gets the hanging entity involved in this event.
     *
     * @return the hanging entity
     */
    @NotNull
    public Hanging getEntity() {
        return hanging;
    }
}
