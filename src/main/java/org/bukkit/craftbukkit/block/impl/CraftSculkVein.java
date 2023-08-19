/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.craftbukkit.block.impl;

public final class CraftSculkVein extends org.bukkit.craftbukkit.block.data.CraftBlockData implements org.bukkit.block.data.type.SculkVein, org.bukkit.block.data.MultipleFacing, org.bukkit.block.data.Waterlogged {

    public CraftSculkVein() {
        super();
    }

    public CraftSculkVein(net.minecraft.block.BlockState state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.CraftMultipleFacing

    private static final net.minecraft.state.property.BooleanProperty[] FACES = new net.minecraft.state.property.BooleanProperty[]{
        getBoolean(net.minecraft.block.SculkVeinBlock.class, "north", true), getBoolean(net.minecraft.block.SculkVeinBlock.class, "east", true), getBoolean(net.minecraft.block.SculkVeinBlock.class, "south", true), getBoolean(net.minecraft.block.SculkVeinBlock.class, "west", true), getBoolean(net.minecraft.block.SculkVeinBlock.class, "up", true), getBoolean(net.minecraft.block.SculkVeinBlock.class, "down", true)
    };

    @Override
    public boolean hasFace(org.bukkit.block.BlockFace face) {
        net.minecraft.state.property.BooleanProperty state = FACES[face.ordinal()];
        if (state == null) {
            throw new IllegalArgumentException("Non-allowed face " + face + ". Check MultipleFacing.getAllowedFaces.");
        }
        return get(state);
    }

    @Override
    public void setFace(org.bukkit.block.BlockFace face, boolean has) {
        net.minecraft.state.property.BooleanProperty state = FACES[face.ordinal()];
        if (state == null) {
            throw new IllegalArgumentException("Non-allowed face " + face + ". Check MultipleFacing.getAllowedFaces.");
        }
        set(state, has);
    }

    @Override
    public java.util.Set<org.bukkit.block.BlockFace> getFaces() {
        com.google.common.collect.ImmutableSet.Builder<org.bukkit.block.BlockFace> faces = com.google.common.collect.ImmutableSet.builder();

        for (int i = 0; i < FACES.length; i++) {
            if (FACES[i] != null && get(FACES[i])) {
                faces.add(org.bukkit.block.BlockFace.values()[i]);
            }
        }

        return faces.build();
    }

    @Override
    public java.util.Set<org.bukkit.block.BlockFace> getAllowedFaces() {
        com.google.common.collect.ImmutableSet.Builder<org.bukkit.block.BlockFace> faces = com.google.common.collect.ImmutableSet.builder();

        for (int i = 0; i < FACES.length; i++) {
            if (FACES[i] != null) {
                faces.add(org.bukkit.block.BlockFace.values()[i]);
            }
        }

        return faces.build();
    }

    // org.bukkit.craftbukkit.block.data.CraftWaterlogged

    private static final net.minecraft.state.property.BooleanProperty WATERLOGGED = getBoolean(net.minecraft.block.SculkVeinBlock.class, "waterlogged");

    @Override
    public boolean isWaterlogged() {
        return get(WATERLOGGED);
    }

    @Override
    public void setWaterlogged(boolean waterlogged) {
        set(WATERLOGGED, waterlogged);
    }
}
