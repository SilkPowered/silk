/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.data.Hatchable;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftSnifferEgg extends CraftBlockData implements Hatchable {

    public CraftSnifferEgg() {
        super();
    }

    public CraftSnifferEgg(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.CraftHatchable

    private static final net.minecraft.world.level.block.state.properties.BlockStateInteger HATCH = getInteger(net.minecraft.world.level.block.SnifferEggBlock.class, "hatch");

    @Override
    public int getHatch() {
        return get(HATCH);
    }

    @Override
    public void setHatch(int hatch) {
        set(HATCH, hatch);
    }

    @Override
    public int getMaximumHatch() {
        return getMax(HATCH);
    }
}
