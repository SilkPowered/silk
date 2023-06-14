package org.bukkit.unrealized.craftbukkit.block.data;

import org.bukkit.unrealized.block.BlockFace;
import org.bukkit.unrealized.block.data.MultipleFacing;

public abstract class CraftMultipleFacing extends CraftBlockData implements MultipleFacing {

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean[] FACES = new net.minecraft.world.level.block.state.properties.BlockStateBoolean[]{
        getBoolean("north", true), getBoolean("east", true), getBoolean("south", true), getBoolean("west", true), getBoolean("up", true), getBoolean("down", true)
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
