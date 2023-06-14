package org.bukkit.unrealized.craftbukkit.block.data;

import org.bukkit.unrealized.block.BlockFace;
import org.bukkit.unrealized.block.data.Directional;

public abstract class CraftDirectional extends CraftBlockData implements Directional {

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> FACING = getEnum("facing");

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
