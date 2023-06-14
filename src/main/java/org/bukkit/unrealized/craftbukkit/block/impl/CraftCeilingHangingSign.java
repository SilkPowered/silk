/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.BlockFace;
import org.bukkit.unrealized.block.data.Attachable;
import org.bukkit.unrealized.block.data.Rotatable;
import org.bukkit.unrealized.block.data.Waterlogged;
import org.bukkit.unrealized.block.data.type.HangingSign;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftCeilingHangingSign extends CraftBlockData implements HangingSign, Attachable, Rotatable, Waterlogged {

    public CraftCeilingHangingSign() {
        super();
    }

    public CraftCeilingHangingSign(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.CraftAttachable

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean ATTACHED = getBoolean(net.minecraft.world.level.block.CeilingHangingSignBlock.class, "attached");

    @Override
    public boolean isAttached() {
        return get(ATTACHED);
    }

    @Override
    public void setAttached(boolean attached) {
        set(ATTACHED, attached);
    }

    // org.bukkit.craftbukkit.block.data.CraftRotatable

    private static final net.minecraft.world.level.block.state.properties.BlockStateInteger ROTATION = getInteger(net.minecraft.world.level.block.CeilingHangingSignBlock.class, "rotation");

    @Override
    public BlockFace getRotation() {
        int data = get(ROTATION);
        switch (data) {
            case 0x0:
                return BlockFace.SOUTH;
            case 0x1:
                return BlockFace.SOUTH_SOUTH_WEST;
            case 0x2:
                return BlockFace.SOUTH_WEST;
            case 0x3:
                return BlockFace.WEST_SOUTH_WEST;
            case 0x4:
                return BlockFace.WEST;
            case 0x5:
                return BlockFace.WEST_NORTH_WEST;
            case 0x6:
                return BlockFace.NORTH_WEST;
            case 0x7:
                return BlockFace.NORTH_NORTH_WEST;
            case 0x8:
                return BlockFace.NORTH;
            case 0x9:
                return BlockFace.NORTH_NORTH_EAST;
            case 0xA:
                return BlockFace.NORTH_EAST;
            case 0xB:
                return BlockFace.EAST_NORTH_EAST;
            case 0xC:
                return BlockFace.EAST;
            case 0xD:
                return BlockFace.EAST_SOUTH_EAST;
            case 0xE:
                return BlockFace.SOUTH_EAST;
            case 0xF:
                return BlockFace.SOUTH_SOUTH_EAST;
            default:
                throw new IllegalArgumentException("Unknown rotation " + data);
        }
    }

    @Override
    public void setRotation(BlockFace rotation) {
        int val;
        switch (rotation) {
            case SOUTH:
                val = 0x0;
                break;
            case SOUTH_SOUTH_WEST:
                val = 0x1;
                break;
            case SOUTH_WEST:
                val = 0x2;
                break;
            case WEST_SOUTH_WEST:
                val = 0x3;
                break;
            case WEST:
                val = 0x4;
                break;
            case WEST_NORTH_WEST:
                val = 0x5;
                break;
            case NORTH_WEST:
                val = 0x6;
                break;
            case NORTH_NORTH_WEST:
                val = 0x7;
                break;
            case NORTH:
                val = 0x8;
                break;
            case NORTH_NORTH_EAST:
                val = 0x9;
                break;
            case NORTH_EAST:
                val = 0xA;
                break;
            case EAST_NORTH_EAST:
                val = 0xB;
                break;
            case EAST:
                val = 0xC;
                break;
            case EAST_SOUTH_EAST:
                val = 0xD;
                break;
            case SOUTH_EAST:
                val = 0xE;
                break;
            case SOUTH_SOUTH_EAST:
                val = 0xF;
                break;
            default:
                throw new IllegalArgumentException("Illegal rotation " + rotation);
        }
        set(ROTATION, val);
    }

    // org.bukkit.craftbukkit.block.data.CraftWaterlogged

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean WATERLOGGED = getBoolean(net.minecraft.world.level.block.CeilingHangingSignBlock.class, "waterlogged");

    @Override
    public boolean isWaterlogged() {
        return get(WATERLOGGED);
    }

    @Override
    public void setWaterlogged(boolean waterlogged) {
        set(WATERLOGGED, waterlogged);
    }
}
