/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.data.Brushable;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftBrushable extends CraftBlockData implements Brushable {

    public CraftBrushable() {
        super();
    }

    public CraftBrushable(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.CraftBrushable

    private static final net.minecraft.world.level.block.state.properties.BlockStateInteger DUSTED = getInteger(net.minecraft.world.level.block.BrushableBlock.class, "dusted");

    @Override
    public int getDusted() {
        return get(DUSTED);
    }

    @Override
    public void setDusted(int dusted) {
        set(DUSTED, dusted);
    }

    @Override
    public int getMaximumDusted() {
        return getMax(DUSTED);
    }
}
