/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.BlockFace;
import org.bukkit.unrealized.block.data.Directional;
import org.bukkit.unrealized.block.data.type.Beehive;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftBeehive extends CraftBlockData implements Beehive, Directional {

    public CraftBeehive() {
        super();
    }

    public CraftBeehive(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.type.CraftBeehive

    private static final net.minecraft.world.level.block.state.properties.BlockStateInteger HONEY_LEVEL = getInteger(net.minecraft.world.level.block.BlockBeehive.class, "honey_level");

    @Override
    public int getHoneyLevel() {
        return get(HONEY_LEVEL);
    }

    @Override
    public void setHoneyLevel(int honeyLevel) {
        set(HONEY_LEVEL, honeyLevel);
    }

    @Override
    public int getMaximumHoneyLevel() {
        return getMax(HONEY_LEVEL);
    }

    // org.bukkit.craftbukkit.block.data.CraftDirectional

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> FACING = getEnum(net.minecraft.world.level.block.BlockBeehive.class, "facing");

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
