package org.bukkit.craftbukkit.inventory.trim;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.jetbrains.annotations.NotNull;

public class CraftTrimPattern implements TrimPattern {

    private final NamespacedKey key;
    private final net.minecraft.item.trim.ArmorTrimPattern handle;

    public CraftTrimPattern(NamespacedKey key, net.minecraft.item.trim.ArmorTrimPattern handle) {
        this.key = key;
        this.handle = handle;
    }

    @Override
    @NotNull
    public NamespacedKey getKey() {
        return key;
    }

    public net.minecraft.item.trim.ArmorTrimPattern getHandle() {
        return handle;
    }
}
