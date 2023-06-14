package org.bukkit.unrealized.craftbukkit.tag;

import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.IRegistry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.FluidType;
import org.bukkit.unrealized.Fluid;
import org.bukkit.unrealized.craftbukkit.util.CraftMagicNumbers;

public class CraftFluidTag extends CraftTag<FluidType, Fluid> {

    public CraftFluidTag(IRegistry<FluidType> registry, TagKey<FluidType> tag) {
        super(registry, tag);
    }

    @Override
    public boolean isTagged(Fluid fluid) {
        return CraftMagicNumbers.getFluid(fluid).is(tag);
    }

    @Override
    public Set<Fluid> getValues() {
        return getHandle().stream().map((fluid) -> CraftMagicNumbers.getFluid(fluid.value())).collect(Collectors.toUnmodifiableSet());
    }
}
