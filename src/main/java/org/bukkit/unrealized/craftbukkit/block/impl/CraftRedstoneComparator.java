/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.BlockFace;
import org.bukkit.unrealized.block.data.Directional;
import org.bukkit.unrealized.block.data.Powerable;
import org.bukkit.unrealized.block.data.type.Comparator;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftRedstoneComparator extends CraftBlockData implements Comparator, Directional, Powerable {

    public CraftRedstoneComparator() {
        super();
    }

    public CraftRedstoneComparator(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.type.CraftComparator

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> MODE = getEnum(net.minecraft.world.level.block.BlockRedstoneComparator.class, "mode");

    @Override
    public Mode getMode() {
        return get(MODE, Mode.class);
    }

    @Override
    public void setMode(Mode mode) {
        set(MODE, mode);
    }

    // org.bukkit.craftbukkit.block.data.CraftDirectional

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> FACING = getEnum(net.minecraft.world.level.block.BlockRedstoneComparator.class, "facing");

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

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean POWERED = getBoolean(net.minecraft.world.level.block.BlockRedstoneComparator.class, "powered");

    @Override
    public boolean isPowered() {
        return get(POWERED);
    }

    @Override
    public void setPowered(boolean powered) {
        set(POWERED, powered);
    }
}
