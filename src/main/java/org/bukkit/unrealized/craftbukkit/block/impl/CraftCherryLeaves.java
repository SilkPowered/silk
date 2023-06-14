/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.data.Waterlogged;
import org.bukkit.unrealized.block.data.type.Leaves;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftCherryLeaves extends CraftBlockData implements Leaves, Waterlogged {

    public CraftCherryLeaves() {
        super();
    }

    public CraftCherryLeaves(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.type.CraftLeaves

    private static final net.minecraft.world.level.block.state.properties.BlockStateInteger DISTANCE = getInteger(net.minecraft.world.level.block.CherryLeavesBlock.class, "distance");
    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean PERSISTENT = getBoolean(net.minecraft.world.level.block.CherryLeavesBlock.class, "persistent");

    @Override
    public boolean isPersistent() {
        return get(PERSISTENT);
    }

    @Override
    public void setPersistent(boolean persistent) {
        set(PERSISTENT, persistent);
    }

    @Override
    public int getDistance() {
        return get(DISTANCE);
    }

    @Override
    public void setDistance(int distance) {
        set(DISTANCE, distance);
    }

    // org.bukkit.craftbukkit.block.data.CraftWaterlogged

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean WATERLOGGED = getBoolean(net.minecraft.world.level.block.CherryLeavesBlock.class, "waterlogged");

    @Override
    public boolean isWaterlogged() {
        return get(WATERLOGGED);
    }

    @Override
    public void setWaterlogged(boolean waterlogged) {
        set(WATERLOGGED, waterlogged);
    }
}
