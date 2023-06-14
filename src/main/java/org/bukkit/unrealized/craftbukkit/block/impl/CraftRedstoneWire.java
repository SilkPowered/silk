/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.BlockFace;
import org.bukkit.unrealized.block.data.AnaloguePowerable;
import org.bukkit.unrealized.block.data.type.RedstoneWire;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftRedstoneWire extends CraftBlockData implements RedstoneWire, AnaloguePowerable {

    public CraftRedstoneWire() {
        super();
    }

    public CraftRedstoneWire(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.type.CraftRedstoneWire

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> NORTH = getEnum(net.minecraft.world.level.block.BlockRedstoneWire.class, "north");
    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> EAST = getEnum(net.minecraft.world.level.block.BlockRedstoneWire.class, "east");
    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> SOUTH = getEnum(net.minecraft.world.level.block.BlockRedstoneWire.class, "south");
    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> WEST = getEnum(net.minecraft.world.level.block.BlockRedstoneWire.class, "west");

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

    // org.bukkit.craftbukkit.block.data.CraftAnaloguePowerable

    private static final net.minecraft.world.level.block.state.properties.BlockStateInteger POWER = getInteger(net.minecraft.world.level.block.BlockRedstoneWire.class, "power");

    @Override
    public int getPower() {
        return get(POWER);
    }

    @Override
    public void setPower(int power) {
        set(POWER, power);
    }

    @Override
    public int getMaximumPower() {
        return getMax(POWER);
    }
}
