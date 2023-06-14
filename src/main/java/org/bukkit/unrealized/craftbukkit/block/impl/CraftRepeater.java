/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.BlockFace;
import org.bukkit.unrealized.block.data.Directional;
import org.bukkit.unrealized.block.data.Powerable;
import org.bukkit.unrealized.block.data.type.Repeater;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftRepeater extends CraftBlockData implements Repeater, Directional, Powerable {

    public CraftRepeater() {
        super();
    }

    public CraftRepeater(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.type.CraftRepeater

    private static final net.minecraft.world.level.block.state.properties.BlockStateInteger DELAY = getInteger(net.minecraft.world.level.block.BlockRepeater.class, "delay");
    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean LOCKED = getBoolean(net.minecraft.world.level.block.BlockRepeater.class, "locked");

    @Override
    public int getDelay() {
        return get(DELAY);
    }

    @Override
    public void setDelay(int delay) {
        set(DELAY, delay);
    }

    @Override
    public int getMinimumDelay() {
        return getMin(DELAY);
    }

    @Override
    public int getMaximumDelay() {
        return getMax(DELAY);
    }

    @Override
    public boolean isLocked() {
        return get(LOCKED);
    }

    @Override
    public void setLocked(boolean locked) {
        set(LOCKED, locked);
    }

    // org.bukkit.craftbukkit.block.data.CraftDirectional

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> FACING = getEnum(net.minecraft.world.level.block.BlockRepeater.class, "facing");

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

    // org.bukkit.craftbukkit.block.data.CraftPowerable

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean POWERED = getBoolean(net.minecraft.world.level.block.BlockRepeater.class, "powered");

    @Override
    public boolean isPowered() {
        return get(POWERED);
    }

    @Override
    public void setPowered(boolean powered) {
        set(POWERED, powered);
    }
}
