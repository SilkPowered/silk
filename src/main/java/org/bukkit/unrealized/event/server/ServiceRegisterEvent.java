package org.bukkit.unrealized.event.server;

import org.bukkit.unrealized.event.HandlerList;
import org.bukkit.unrealized.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;

/**
 * This event is called when a service is registered.
 * <p>
 * Warning: The order in which register and unregister events are called
 * should not be relied upon.
 */
public class ServiceRegisterEvent extends ServiceEvent {
    private static final HandlerList handlers = new HandlerList();

    public ServiceRegisterEvent(@NotNull RegisteredServiceProvider<?> registeredProvider) {
        super(registeredProvider);
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
