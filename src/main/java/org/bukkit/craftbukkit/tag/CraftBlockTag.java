package org.bukkit.craftbukkit.tag;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.TagKey;
import org.bukkit.Material;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;

public class CraftBlockTag extends CraftTag<Block, Material> {

    public CraftBlockTag(Registry<Block> registry, TagKey<Block> tag) {
        super(registry, tag);
    }

    @Override
    public boolean isTagged(Material item) {
        Block block = CraftMagicNumbers.getBlock(item);

        // SPIGOT-6952: A Material is not necessary a block, in this case return false
        if (block == null) {
            return false;
        }

        return block.builtInRegistryHolder().isIn(tag);
    }

    @Override
    public Set<Material> getValues() {
        return getHandle().stream().map((block) -> CraftMagicNumbers.getMaterial(block.comp_349())).collect(Collectors.toUnmodifiableSet());
    }
}
