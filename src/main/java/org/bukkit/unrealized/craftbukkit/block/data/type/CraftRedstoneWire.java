package org.bukkit.unrealized.craftbukkit.block.data.type;

import org.bukkit.unrealized.block.BlockFace;
import org.bukkit.unrealized.block.data.type.RedstoneWire;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public abstract class CraftRedstoneWire extends CraftBlockData implements RedstoneWire {

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> NORTH = getEnum("north");
    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> EAST = getEnum("east");
    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> SOUTH = getEnum("south");
    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> WEST = getEnum("west");

    @Override
    public Connection getFace(BlockFace face) {
        switch (face) {
            case NORTH:
                return get(NORTH, Connection.class);
            case EAST:
                return get(EAST, Connection.class);
            case SOUTH:
                return get(SOUTH, Connection.class);
            case WEST:
                return get(WEST, Connection.class);
            default:
                throw new IllegalArgumentException("Cannot have face " + face);
        }
    }

    @Override
    public void setFace(BlockFace face, Connection connection) {
        switch (face) {
            case NORTH:
                set(NORTH, connection);
                break;
            case EAST:
                set(EAST, connection);
                break;
            case SOUTH:
                set(SOUTH, connection);
                break;
            case WEST:
                set(WEST, connection);
                break;
            default:
                throw new IllegalArgumentException("Cannot have face " + face);
        }
    }

    @Override
    public java.util.Set<BlockFace> getAllowedFaces() {
        return com.google.common.collect.ImmutableSet.of(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST);
    }
}
