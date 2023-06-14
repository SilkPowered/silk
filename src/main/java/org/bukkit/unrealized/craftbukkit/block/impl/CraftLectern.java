/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.BlockFace;
import org.bukkit.unrealized.block.data.Directional;
import org.bukkit.unrealized.block.data.Powerable;
import org.bukkit.unrealized.block.data.type.Lectern;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftLectern extends CraftBlockData implements Lectern, Directional, Powerable {

    public CraftLectern() {
        super();
    }

    public CraftLectern(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.type.CraftLectern

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean HAS_BOOK = getBoolean(net.minecraft.world.level.block.BlockLectern.class, "has_book");

    @Override
    public boolean hasBook() {
        return get(HAS_BOOK);
    }

    // org.bukkit.craftbukkit.block.data.CraftDirectional

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> FACING = getEnum(net.minecraft.world.level.block.BlockLectern.class, "facing");

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

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean POWERED = getBoolean(net.minecraft.world.level.block.BlockLectern.class, "powered");

    @Override
    public boolean isPowered() {
        return get(POWERED);
    }

    @Override
    public void setPowered(boolean powered) {
        set(POWERED, powered);
    }
}
