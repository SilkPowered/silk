package org.bukkit.craftbukkit.tag;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import org.bukkit.Registry;
import org.bukkit.craftbukkit.util.CraftNamespacedKey;
import org.bukkit.entity.EntityType;

public class CraftEntityTag extends CraftTag<net.minecraft.entity.EntityType<?>, EntityType> {

    public CraftEntityTag(net.minecraft.registry.Registry<net.minecraft.entity.EntityType<?>> registry, TagKey<net.minecraft.entity.EntityType<?>> tag) {
        super(registry, tag);
    }

    @Override
    public boolean isTagged(EntityType entity) {
        return registry.getHolderOrThrow(RegistryKey.of(RegistryKeys.ENTITY_TYPE, CraftNamespacedKey.toMinecraft(entity.getKey()))).isIn(tag);
    }

    @Override
    public Set<EntityType> getValues() {
        return getHandle().stream().map((nms) -> Registry.ENTITY_TYPE.get(CraftNamespacedKey.fromMinecraft(net.minecraft.entity.EntityType.getId(nms.comp_349())))).filter(Objects::nonNull).collect(Collectors.toUnmodifiableSet());
    }
}
