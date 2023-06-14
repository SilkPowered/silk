/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.data.Waterlogged;
import org.bukkit.unrealized.block.data.type.SeaPickle;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftSeaPickle extends CraftBlockData implements SeaPickle, Waterlogged {

    public CraftSeaPickle() {
        super();
    }

    public CraftSeaPickle(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.type.CraftSeaPickle

    private static final net.minecraft.world.level.block.state.properties.BlockStateInteger PICKLES = getInteger(net.minecraft.world.level.block.BlockSeaPickle.class, "pickles");

    @Override
    public int getPickles() {
        return get(PICKLES);
    }

    @Override
    public void setPickles(int pickles) {
        set(PICKLES, pickles);
    }

    @Override
    public int getMinimumPickles() {
        return getMin(PICKLES);
    }

    @Override
    public int getMaximumPickles() {
        return getMax(PICKLES);
    }

    // org.bukkit.craftbukkit.block.data.CraftWaterlogged

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean WATERLOGGED = getBoolean(net.minecraft.world.level.block.BlockSeaPickle.class, "waterlogged");

    @Override
    public boolean isWaterlogged() {
        return get(WATERLOGGED);
    }

    @Override
    public void setWaterlogged(boolean waterlogged) {
        set(WATERLOGGED, waterlogged);
    }
}