/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.BlockFace;
import org.bukkit.unrealized.block.data.Waterlogged;
import org.bukkit.unrealized.block.data.type.PointedDripstone;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftPointedDripstone extends CraftBlockData implements PointedDripstone, Waterlogged {

    public CraftPointedDripstone() {
        super();
    }

    public CraftPointedDripstone(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.type.CraftPointedDripstone

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> VERTICAL_DIRECTION = getEnum(net.minecraft.world.level.block.PointedDripstoneBlock.class, "vertical_direction");
    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> THICKNESS = getEnum(net.minecraft.world.level.block.PointedDripstoneBlock.class, "thickness");

    @Override
    public BlockFace getVerticalDirection() {
        return get(VERTICAL_DIRECTION, BlockFace.class);
    }

    @Override
    public void setVerticalDirection(BlockFace direction) {
        set(VERTICAL_DIRECTION, direction);
    }

    @Override
    public java.util.Set<BlockFace> getVerticalDirections() {
        return getValues(VERTICAL_DIRECTION, BlockFace.class);
    }

    @Override
    public Thickness getThickness() {
        return get(THICKNESS, Thickness.class);
    }

    @Override
    public void setThickness(Thickness thickness) {
        set(THICKNESS, thickness);
    }

    // org.bukkit.craftbukkit.block.data.CraftWaterlogged

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean WATERLOGGED = getBoolean(net.minecraft.world.level.block.PointedDripstoneBlock.class, "waterlogged");

    @Override
    public boolean isWaterlogged() {
        return get(WATERLOGGED);
    }

    @Override
    public void setWaterlogged(boolean waterlogged) {
        set(WATERLOGGED, waterlogged);
    }
}
