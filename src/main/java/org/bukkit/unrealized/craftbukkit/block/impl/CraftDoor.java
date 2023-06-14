/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.BlockFace;
import org.bukkit.unrealized.block.data.Bisected;
import org.bukkit.unrealized.block.data.Directional;
import org.bukkit.unrealized.block.data.Openable;
import org.bukkit.unrealized.block.data.Powerable;
import org.bukkit.unrealized.block.data.type.Door;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftDoor extends CraftBlockData implements Door, Bisected, Directional, Openable, Powerable {

    public CraftDoor() {
        super();
    }

    public CraftDoor(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.type.CraftDoor

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> HINGE = getEnum(net.minecraft.world.level.block.BlockDoor.class, "hinge");

    @Override
    public Hinge getHinge() {
        return get(HINGE, Hinge.class);
    }

    @Override
    public void setHinge(Hinge hinge) {
        set(HINGE, hinge);
    }

    // org.bukkit.craftbukkit.block.data.CraftBisected

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> HALF = getEnum(net.minecraft.world.level.block.BlockDoor.class, "half");

    @Override
    public Half getHalf() {
        return get(HALF, Half.class);
    }

    @Override
    public void setHalf(Half half) {
        set(HALF, half);
    }

    // org.bukkit.craftbukkit.block.data.CraftDirectional

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> FACING = getEnum(net.minecraft.world.level.block.BlockDoor.class, "facing");

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

    // org.bukkit.craftbukkit.block.data.CraftOpenable

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean OPEN = getBoolean(net.minecraft.world.level.block.BlockDoor.class, "open");

    @Override
    public boolean isOpen() {
        return get(OPEN);
    }

    @Override
    public void setOpen(boolean open) {
        set(OPEN, open);
    }

    // org.bukkit.craftbukkit.block.data.CraftPowerable

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean POWERED = getBoolean(net.minecraft.world.level.block.BlockDoor.class, "powered");

    @Override
    public boolean isPowered() {
        return get(POWERED);
    }

    @Override
    public void setPowered(boolean powered) {
        set(POWERED, powered);
    }
}
