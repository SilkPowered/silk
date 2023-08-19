package org.bukkit.craftbukkit.tag;

import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.TagKey;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.craftbukkit.util.CraftNamespacedKey;

public abstract class CraftTag<N, B extends Keyed> implements Tag<B> {

    protected final Registry<N> registry;
    protected final TagKey<N> tag;
    //
    private RegistryEntryList.Named<N> handle;

    public CraftTag(Registry<N> registry, TagKey<N> tag) {
        this.registry = registry;
        this.tag = tag;
        this.handle = registry.getEntryList(this.tag).orElseThrow();
    }

    protected RegistryEntryList.Named<N> getHandle() {
        return handle;
    }

    @Override
    public NamespacedKey getKey() {
        return CraftNamespacedKey.fromMinecraft(tag.id());
    }
}
