/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.data.AnaloguePowerable;
import org.bukkit.unrealized.block.data.Waterlogged;
import org.bukkit.unrealized.block.data.type.SculkSensor;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftSculkSensor extends CraftBlockData implements SculkSensor, AnaloguePowerable, Waterlogged {

    public CraftSculkSensor() {
        super();
    }

    public CraftSculkSensor(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.type.CraftSculkSensor

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> PHASE = getEnum(net.minecraft.world.level.block.SculkSensorBlock.class, "sculk_sensor_phase");

    @Override
    public Phase getPhase() {
        return get(PHASE, Phase.class);
    }

    @Override
    public void setPhase(Phase phase) {
        set(PHASE, phase);
    }

    // org.bukkit.craftbukkit.block.data.CraftAnaloguePowerable

    private static final net.minecraft.world.level.block.state.properties.BlockStateInteger POWER = getInteger(net.minecraft.world.level.block.SculkSensorBlock.class, "power");

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

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean WATERLOGGED = getBoolean(net.minecraft.world.level.block.SculkSensorBlock.class, "waterlogged");

    @Override
    public boolean isWaterlogged() {
        return get(WATERLOGGED);
    }

    @Override
    public void setWaterlogged(boolean waterlogged) {
        set(WATERLOGGED, waterlogged);
    }
}
