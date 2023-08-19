package org.bukkit.craftbukkit.inventory.trim;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.jetbrains.annotations.NotNull;

public class CraftTrimMaterial implements TrimMaterial {

    private final NamespacedKey key;
    private final net.minecraft.item.trim.ArmorTrimMaterial handle;

    public CraftTrimMaterial(NamespacedKey key, net.minecraft.item.trim.ArmorTrimMaterial handle) {
        this.key = key;
        this.handle = handle;
    }

    @Override
    @NotNull
    public NamespacedKey getKey() {
        return key;
    }

    public net.minecraft.item.trim.ArmorTrimMaterial getHandle() {
        return handle;
    }
}
