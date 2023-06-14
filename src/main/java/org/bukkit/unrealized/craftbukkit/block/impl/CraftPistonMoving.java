/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.BlockFace;
import org.bukkit.unrealized.block.data.Directional;
import org.bukkit.unrealized.block.data.type.TechnicalPiston;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftPistonMoving extends CraftBlockData implements TechnicalPiston, Directional {

    public CraftPistonMoving() {
        super();
    }

    public CraftPistonMoving(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.type.CraftTechnicalPiston

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> TYPE = getEnum(net.minecraft.world.level.block.piston.BlockPistonMoving.class, "type");

    @Override
    public Type getType() {
        return get(TYPE, Type.class);
    }

    @Override
    public void setType(Type type) {
        set(TYPE, type);
    }

    // org.bukkit.craftbukkit.block.data.CraftDirectional

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> FACING = getEnum(net.minecraft.world.level.block.piston.BlockPistonMoving.class, "facing");

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
