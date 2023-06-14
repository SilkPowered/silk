/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.BlockFace;
import org.bukkit.unrealized.block.data.AnaloguePowerable;
import org.bukkit.unrealized.block.data.Directional;
import org.bukkit.unrealized.block.data.Waterlogged;
import org.bukkit.unrealized.block.data.type.CalibratedSculkSensor;
import org.bukkit.unrealized.block.data.type.SculkSensor;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftCalibratedSculkSensor extends CraftBlockData implements CalibratedSculkSensor, Directional, SculkSensor, AnaloguePowerable, Waterlogged {

    public CraftCalibratedSculkSensor() {
        super();
    }

    public CraftCalibratedSculkSensor(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.CraftDirectional

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> FACING = getEnum(net.minecraft.world.level.block.CalibratedSculkSensorBlock.class, "facing");

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

    // org.bukkit.craftbukkit.block.data.type.CraftSculkSensor

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> PHASE = getEnum(net.minecraft.world.level.block.CalibratedSculkSensorBlock.class, "sculk_sensor_phase");

    @Override
    public Phase getPhase() {
        return get(PHASE, Phase.class);
    }

    @Override
    public void setPhase(Phase phase) {
        set(PHASE, phase);
    }

    // org.bukkit.craftbukkit.block.data.CraftAnaloguePowerable

    private static final net.minecraft.world.level.block.state.properties.BlockStateInteger POWER = getInteger(net.minecraft.world.level.block.CalibratedSculkSensorBlock.class, "power");

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

    // org.bukkit.craftbukkit.block.data.CraftWaterlogged

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean WATERLOGGED = getBoolean(net.minecraft.world.level.block.CalibratedSculkSensorBlock.class, "waterlogged");

    @Override
    public boolean isWaterlogged() {
        return get(WATERLOGGED);
    }

    @Override
    public void setWaterlogged(boolean waterlogged) {
        set(WATERLOGGED, waterlogged);
    }
}
