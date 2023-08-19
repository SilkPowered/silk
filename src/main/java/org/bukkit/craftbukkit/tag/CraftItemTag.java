package org.bukkit.craftbukkit.tag;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.TagKey;
import org.bukkit.Material;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;

public class CraftItemTag extends CraftTag<Item, Material> {

    public CraftItemTag(Registry<Item> registry, TagKey<Item> tag) {
        super(registry, tag);
    }

    @Override
    public boolean isTagged(Material item) {
        Item minecraft = CraftMagicNumbers.getItem(item);

        // SPIGOT-6952: A Material is not necessary an item, in this case return false
        if (minecraft == null) {
            return false;
        }

        return minecraft.builtInRegistryHolder().isIn(tag);
    }

    @Override
    public Set<Material> getValues() {
        return getHandle().stream().map((item) -> CraftMagicNumbers.getMaterial(item.comp_349())).collect(Collectors.toUnmodifiableSet());
    }
}
