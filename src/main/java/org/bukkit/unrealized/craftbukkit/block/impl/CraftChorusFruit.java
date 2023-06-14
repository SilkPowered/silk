/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.BlockFace;
import org.bukkit.unrealized.block.data.MultipleFacing;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftChorusFruit extends CraftBlockData implements MultipleFacing {

    public CraftChorusFruit() {
        super();
    }

    public CraftChorusFruit(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.CraftMultipleFacing

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean[] FACES = new net.minecraft.world.level.block.state.properties.BlockStateBoolean[]{
        getBoolean(net.minecraft.world.level.block.BlockChorusFruit.class, "north", true), getBoolean(net.minecraft.world.level.block.BlockChorusFruit.class, "east", true), getBoolean(net.minecraft.world.level.block.BlockChorusFruit.class, "south", true), getBoolean(net.minecraft.world.level.block.BlockChorusFruit.class, "west", true), getBoolean(net.minecraft.world.level.block.BlockChorusFruit.class, "up", true), getBoolean(net.minecraft.world.level.block.BlockChorusFruit.class, "down", true)
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
}
