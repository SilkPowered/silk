/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.BlockFace;
import org.bukkit.unrealized.block.data.Directional;
import org.bukkit.unrealized.block.data.type.CommandBlock;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftCommand extends CraftBlockData implements CommandBlock, Directional {

    public CraftCommand() {
        super();
    }

    public CraftCommand(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.type.CraftCommandBlock

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean CONDITIONAL = getBoolean(net.minecraft.world.level.block.BlockCommand.class, "conditional");

    @Override
    public boolean isConditional() {
        return get(CONDITIONAL);
    }

    @Override
    public void setConditional(boolean conditional) {
        set(CONDITIONAL, conditional);
    }

    // org.bukkit.craftbukkit.block.data.CraftDirectional

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> FACING = getEnum(net.minecraft.world.level.block.BlockCommand.class, "facing");

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
}
