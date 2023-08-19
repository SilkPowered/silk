package org.bukkit.craftbukkit.entity.memory;

import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.registry.Registries;
import org.bukkit.craftbukkit.util.CraftNamespacedKey;
import org.bukkit.entity.memory.MemoryKey;

public final class CraftMemoryKey {

    private CraftMemoryKey() {}

    public static <T, U> MemoryModuleType<U> fromMemoryKey(MemoryKey<T> memoryKey) {
        return (MemoryModuleType<U>) Registries.MEMORY_MODULE_TYPE.get(CraftNamespacedKey.toMinecraft(memoryKey.getKey()));
    }

    public static <T, U> MemoryKey<U> toMemoryKey(MemoryModuleType<T> memoryModuleType) {
        return MemoryKey.getByKey(CraftNamespacedKey.fromMinecraft(Registries.MEMORY_MODULE_TYPE.getKey(memoryModuleType)));
    }
}
