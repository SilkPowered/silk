package org.bukkit.craftbukkit.tag;

import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.TagKey;
import org.bukkit.Fluid;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;

public class CraftFluidTag extends CraftTag<net.minecraft.fluid.Fluid, Fluid> {

    public CraftFluidTag(Registry<net.minecraft.fluid.Fluid> registry, TagKey<net.minecraft.fluid.Fluid> tag) {
        super(registry, tag);
    }

    @Override
    public boolean isTagged(Fluid fluid) {
        return CraftMagicNumbers.getFluid(fluid).isIn(tag);
    }

    @Override
    public Set<Fluid> getValues() {
        return getHandle().stream().map((fluid) -> CraftMagicNumbers.getFluid(fluid.comp_349())).collect(Collectors.toUnmodifiableSet());
    }
}
