package org.bukkit.unrealized.event.server;

import org.bukkit.unrealized.plugin.RegisteredServiceProvider;
import org.bukkit.unrealized.plugin.ServicesManager;
import org.jetbrains.annotations.NotNull;

/**
 * An event relating to a registered service. This is called in a {@link
 * ServicesManager}
 */
public abstract class ServiceEvent extends ServerEvent {
    private final RegisteredServiceProvider<?> provider;

    public ServiceEvent(@NotNull final RegisteredServiceProvider<?> provider) {
        this.provider = provider;
    }

    @NotNull
    public RegisteredServiceProvider<?> getProvider() {
        return provider;
    }
}
