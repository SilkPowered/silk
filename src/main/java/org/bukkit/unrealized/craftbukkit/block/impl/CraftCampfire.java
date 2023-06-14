/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.BlockFace;
import org.bukkit.unrealized.block.data.Directional;
import org.bukkit.unrealized.block.data.Lightable;
import org.bukkit.unrealized.block.data.Waterlogged;
import org.bukkit.unrealized.block.data.type.Campfire;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftCampfire extends CraftBlockData implements Campfire, Directional, Lightable, Waterlogged {

    public CraftCampfire() {
        super();
    }

    public CraftCampfire(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.type.CraftCampfire

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean SIGNAL_FIRE = getBoolean(net.minecraft.world.level.block.BlockCampfire.class, "signal_fire");

    @Override
    public boolean isSignalFire() {
        return get(SIGNAL_FIRE);
    }

    @Override
    public void setSignalFire(boolean signalFire) {
        set(SIGNAL_FIRE, signalFire);
    }

    // org.bukkit.craftbukkit.block.data.CraftDirectional

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> FACING = getEnum(net.minecraft.world.level.block.BlockCampfire.class, "facing");

    @Override
    public BlockFace getFacing() {
        return get(FACING, BlockFace.class);
    }

    @Override
    public void setFacing(BlockFace facing) {
        set(FACING, facing);
    }

    @Override
    public java.util.Set<BlockFace> getFaces() {
        return getValues(FACING, BlockFace.class);
    }

    // org.bukkit.craftbukkit.block.data.CraftLightable

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean LIT = getBoolean(net.minecraft.world.level.block.BlockCampfire.class, "lit");

    @Override
    public boolean isLit() {
        return get(LIT);
    }

    @Override
    public void setLit(boolean lit) {
        set(LIT, lit);
    }

    // org.bukkit.craftbukkit.block.data.CraftWaterlogged

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean WATERLOGGED = getBoolean(net.minecraft.world.level.block.BlockCampfire.class, "waterlogged");

    @Override
    public boolean isWaterlogged() {
        return get(WATERLOGGED);
    }

    @Override
    public void setWaterlogged(boolean waterlogged) {
        set(WATERLOGGED, waterlogged);
    }
}
