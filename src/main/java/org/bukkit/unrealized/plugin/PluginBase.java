package org.bukkit.unrealized.plugin;

import org.bukkit.unrealized.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a base {@link Plugin}
 * <p>
 * Extend this class if your plugin is not a {@link
 * JavaPlugin}
 */
public abstract class PluginBase implements Plugin {
    @Override
    public final int hashCode() {
        return getName().hashCode();
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Plugin)) {
            return false;
        }
        return getName().equals(((Plugin) obj).getName());
    }

    @Override
    @NotNull
    public final String getName() {
        return getDescription().getName();
    }
}
