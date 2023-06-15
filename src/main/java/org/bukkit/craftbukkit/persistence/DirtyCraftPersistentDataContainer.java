package org.bukkit.craftbukkit.persistence;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;

/**
 * A child class of the persistent data container that recalls if it has been
 * mutated from an external caller.
 */
public final class DirtyCraftPersistentDataContainer extends CraftPersistentDataContainer {

    private boolean dirty;

    public DirtyCraftPersistentDataContainer(Map<String, NbtElement> customTags, CraftPersistentDataTypeRegistry registry) {
        super(customTags, registry);
    }

    public DirtyCraftPersistentDataContainer(CraftPersistentDataTypeRegistry registry) {
        super(registry);
    }

    public boolean dirty() {
        return this.dirty;
    }

    public void dirty(final boolean dirty) {
        this.dirty = dirty;
    }

    @Override
    public <T, Z> void set(NamespacedKey key, PersistentDataType<T, Z> type, Z value) {
        super.set(key, type, value);
        this.dirty(true);
    }

    @Override
    public void remove(NamespacedKey key) {
        super.remove(key);
        this.dirty(true);
    }

    @Override
    public void put(String key, NbtElement base) {
        super.put(key, base);
        this.dirty(true);
    }

    @Override
    public void putAll(NbtCompound compound) {
        super.putAll(compound);
        this.dirty(true);
    }

    @Override
    public void putAll(Map<String, NbtElement> map) {
        super.putAll(map);
        this.dirty(true);
    }
}
