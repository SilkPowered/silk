/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.Axis;
import org.bukkit.unrealized.block.data.Orientable;
import org.bukkit.unrealized.block.data.Waterlogged;
import org.bukkit.unrealized.block.data.type.Chain;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftChain extends CraftBlockData implements Chain, Orientable, Waterlogged {

    public CraftChain() {
        super();
    }

    public CraftChain(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.CraftOrientable

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> AXIS = getEnum(net.minecraft.world.level.block.BlockChain.class, "axis");

    @Override
    public Axis getAxis() {
        return get(AXIS, Axis.class);
    }

    @Override
    public void setAxis(Axis axis) {
        set(AXIS, axis);
    }

    @Override
    public java.util.Set<Axis> getAxes() {
        return getValues(AXIS, Axis.class);
    }

    // org.bukkit.craftbukkit.block.data.CraftWaterlogged

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean WATERLOGGED = getBoolean(net.minecraft.world.level.block.BlockChain.class, "waterlogged");

    @Override
    public boolean isWaterlogged() {
        return get(WATERLOGGED);
    }

    @Override
    public void setWaterlogged(boolean waterlogged) {
        set(WATERLOGGED, waterlogged);
    }
}
