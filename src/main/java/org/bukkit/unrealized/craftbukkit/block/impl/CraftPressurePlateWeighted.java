/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.data.AnaloguePowerable;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftPressurePlateWeighted extends CraftBlockData implements AnaloguePowerable {

    public CraftPressurePlateWeighted() {
        super();
    }

    public CraftPressurePlateWeighted(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.CraftAnaloguePowerable

    private static final net.minecraft.world.level.block.state.properties.BlockStateInteger POWER = getInteger(net.minecraft.world.level.block.BlockPressurePlateWeighted.class, "power");

    @Override
    public int getPower() {
        return get(POWER);
    }

    @Override
    public void setPower(int power) {
        set(POWER, power);
    }

    @Override
    public int getMaximumPower() {
        return getMax(POWER);
    }
}
