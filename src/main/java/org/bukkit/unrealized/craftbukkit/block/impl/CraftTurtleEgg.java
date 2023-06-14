/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.data.Hatchable;
import org.bukkit.unrealized.block.data.type.TurtleEgg;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftTurtleEgg extends CraftBlockData implements TurtleEgg, Hatchable {

    public CraftTurtleEgg() {
        super();
    }

    public CraftTurtleEgg(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.type.CraftTurtleEgg

    private static final net.minecraft.world.level.block.state.properties.BlockStateInteger EGGS = getInteger(net.minecraft.world.level.block.BlockTurtleEgg.class, "eggs");

    @Override
    public int getEggs() {
        return get(EGGS);
    }

    @Override
    public void setEggs(int eggs) {
        set(EGGS, eggs);
    }

    @Override
    public int getMinimumEggs() {
        return getMin(EGGS);
    }

    @Override
    public int getMaximumEggs() {
        return getMax(EGGS);
    }

    // org.bukkit.craftbukkit.block.data.CraftHatchable

    private static final net.minecraft.world.level.block.state.properties.BlockStateInteger HATCH = getInteger(net.minecraft.world.level.block.BlockTurtleEgg.class, "hatch");

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
