package org.bukkit.unrealized.craftbukkit.block.data.type;

import org.bukkit.unrealized.block.BlockFace;
import org.bukkit.unrealized.block.data.type.Wall;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public abstract class CraftWall extends CraftBlockData implements Wall {

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean UP = getBoolean("up");
    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?>[] HEIGHTS = new net.minecraft.world.level.block.state.properties.BlockStateEnum[]{
        getEnum("north"), getEnum("east"), getEnum("south"), getEnum("west")
    };

    @Override
    public boolean isUp() {
        return get(UP);
    }

    @Override
    public void setUp(boolean up) {
        set(UP, up);
    }

    @Override
    public Height getHeight(BlockFace face) {
        return get(HEIGHTS[face.ordinal()], Height.class);
    }

    @Override
    public void setHeight(BlockFace face, Height height) {
        set(HEIGHTS[face.ordinal()], height);
    }
}
