/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.BlockFace;
import org.bukkit.unrealized.block.data.Waterlogged;
import org.bukkit.unrealized.block.data.type.Wall;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftCobbleWall extends CraftBlockData implements Wall, Waterlogged {

    public CraftCobbleWall() {
        super();
    }

    public CraftCobbleWall(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.type.CraftWall

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean UP = getBoolean(net.minecraft.world.level.block.BlockCobbleWall.class, "up");
    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?>[] HEIGHTS = new net.minecraft.world.level.block.state.properties.BlockStateEnum[]{
        getEnum(net.minecraft.world.level.block.BlockCobbleWall.class, "north"), getEnum(net.minecraft.world.level.block.BlockCobbleWall.class, "east"), getEnum(net.minecraft.world.level.block.BlockCobbleWall.class, "south"), getEnum(net.minecraft.world.level.block.BlockCobbleWall.class, "west")
    };

    @Override
    public boolean isUp() {
        return get(UP);
    }

    @Override
    public void setUp(boolean up) {
        set(UP, up);
    }

    @Override
    public Height getHeight(BlockFace face) {
        return get(HEIGHTS[face.ordinal()], Height.class);
    }

    @Override
    public void setHeight(BlockFace face, Height height) {
        set(HEIGHTS[face.ordinal()], height);
    }

    // org.bukkit.craftbukkit.block.data.CraftWaterlogged

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean WATERLOGGED = getBoolean(net.minecraft.world.level.block.BlockCobbleWall.class, "waterlogged");

    @Override
    public boolean isWaterlogged() {
        return get(WATERLOGGED);
    }

    @Override
    public void setWaterlogged(boolean waterlogged) {
        set(WATERLOGGED, waterlogged);
    }
}
