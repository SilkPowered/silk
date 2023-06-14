package org.bukkit.unrealized.plugin;

import org.bukkit.unrealized.event.Event;
import org.bukkit.unrealized.event.EventException;
import org.bukkit.unrealized.event.Listener;
import org.jetbrains.annotations.NotNull;

/**
 * Interface which defines the class for event call backs to plugins
 */
public interface EventExecutor {
    public void execute(@NotNull Listener listener, @NotNull Event event) throws EventException;
}
