package org.bukkit.unrealized.craftbukkit.persistence;

import org.bukkit.unrealized.persistence.PersistentDataAdapterContext;

public final class CraftPersistentDataAdapterContext implements PersistentDataAdapterContext {

    private final CraftPersistentDataTypeRegistry registry;

    public CraftPersistentDataAdapterContext(CraftPersistentDataTypeRegistry registry) {
        this.registry = registry;
    }

    /**
     * Creates a new and empty tag container instance
     *
     * @return the fresh container instance
     */
    @Override
    public CraftPersistentDataContainer newPersistentDataContainer() {
        return new CraftPersistentDataContainer(this.registry);
    }
}
