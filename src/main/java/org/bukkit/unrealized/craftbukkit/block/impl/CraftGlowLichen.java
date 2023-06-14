/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.BlockFace;
import org.bukkit.unrealized.block.data.MultipleFacing;
import org.bukkit.unrealized.block.data.Waterlogged;
import org.bukkit.unrealized.block.data.type.GlowLichen;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftGlowLichen extends CraftBlockData implements GlowLichen, MultipleFacing, Waterlogged {

    public CraftGlowLichen() {
        super();
    }

    public CraftGlowLichen(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.CraftMultipleFacing

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean[] FACES = new net.minecraft.world.level.block.state.properties.BlockStateBoolean[]{
        getBoolean(net.minecraft.world.level.block.GlowLichenBlock.class, "north", true), getBoolean(net.minecraft.world.level.block.GlowLichenBlock.class, "east", true), getBoolean(net.minecraft.world.level.block.GlowLichenBlock.class, "south", true), getBoolean(net.minecraft.world.level.block.GlowLichenBlock.class, "west", true), getBoolean(net.minecraft.world.level.block.GlowLichenBlock.class, "up", true), getBoolean(net.minecraft.world.level.block.GlowLichenBlock.class, "down", true)
    };

    @Override
    public boolean hasFace(BlockFace face) {
        net.minecraft.world.level.block.state.properties.BlockStateBoolean state = FACES[face.ordinal()];
        if (state == null) {
            throw new IllegalArgumentException("Non-allowed face " + face + ". Check MultipleFacing.getAllowedFaces.");
        }
        return get(state);
    }

    @Override
    public void setFace(BlockFace face, boolean has) {
        net.minecraft.world.level.block.state.properties.BlockStateBoolean state = FACES[face.ordinal()];
        if (state == null) {
            throw new IllegalArgumentException("Non-allowed face " + face + ". Check MultipleFacing.getAllowedFaces.");
        }
        set(state, has);
    }

    @Override
    public java.util.Set<BlockFace> getFaces() {
        com.google.common.collect.ImmutableSet.Builder<BlockFace> faces = com.google.common.collect.ImmutableSet.builder();

        for (int i = 0; i < FACES.length; i++) {
            if (FACES[i] != null && get(FACES[i])) {
                faces.add(BlockFace.values()[i]);
            }
        }

        return faces.build();
    }

    @Override
    public java.util.Set<BlockFace> getAllowedFaces() {
        com.google.common.collect.ImmutableSet.Builder<BlockFace> faces = com.google.common.collect.ImmutableSet.builder();

        for (int i = 0; i < FACES.length; i++) {
            if (FACES[i] != null) {
                faces.add(BlockFace.values()[i]);
            }
        }

        return faces.build();
    }

    // org.bukkit.craftbukkit.block.data.CraftWaterlogged

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean WATERLOGGED = getBoolean(net.minecraft.world.level.block.GlowLichenBlock.class, "waterlogged");

    @Override
    public boolean isWaterlogged() {
        return get(WATERLOGGED);
    }

    @Override
    public void setWaterlogged(boolean waterlogged) {
        set(WATERLOGGED, waterlogged);
    }
}
