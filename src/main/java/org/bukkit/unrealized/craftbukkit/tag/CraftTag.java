package org.bukkit.unrealized.craftbukkit.tag;

import net.minecraft.core.HolderSet;
import net.minecraft.core.IRegistry;
import net.minecraft.tags.TagKey;
import org.bukkit.unrealized.Keyed;
import org.bukkit.unrealized.NamespacedKey;
import org.bukkit.unrealized.Tag;
import org.bukkit.unrealized.craftbukkit.util.CraftNamespacedKey;

public abstract class CraftTag<N, B extends Keyed> implements Tag<B> {

    protected final IRegistry<N> registry;
    protected final TagKey<N> tag;
    //
    private HolderSet.Named<N> handle;

    public CraftTag(IRegistry<N> registry, TagKey<N> tag) {
        this.registry = registry;
        this.tag = tag;
        this.handle = registry.getTag(this.tag).orElseThrow();
    }

    protected HolderSet.Named<N> getHandle() {
        return handle;
    }

    @Override
    public NamespacedKey getKey() {
        return CraftNamespacedKey.fromMinecraft(tag.location());
    }
}
