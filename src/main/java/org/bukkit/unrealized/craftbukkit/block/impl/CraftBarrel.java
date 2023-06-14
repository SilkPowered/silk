/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.BlockFace;
import org.bukkit.unrealized.block.data.Directional;
import org.bukkit.unrealized.block.data.Openable;
import org.bukkit.unrealized.block.data.type.Barrel;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftBarrel extends CraftBlockData implements Barrel, Directional, Openable {

    public CraftBarrel() {
        super();
    }

    public CraftBarrel(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.CraftDirectional

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> FACING = getEnum(net.minecraft.world.level.block.BlockBarrel.class, "facing");

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

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean OPEN = getBoolean(net.minecraft.world.level.block.BlockBarrel.class, "open");

    @Override
    public boolean isOpen() {
        return get(OPEN);
    }

    @Override
    public void setOpen(boolean open) {
        set(OPEN, open);
    }
}
