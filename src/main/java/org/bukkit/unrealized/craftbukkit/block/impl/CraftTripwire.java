/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.BlockFace;
import org.bukkit.unrealized.block.data.Attachable;
import org.bukkit.unrealized.block.data.MultipleFacing;
import org.bukkit.unrealized.block.data.Powerable;
import org.bukkit.unrealized.block.data.type.Tripwire;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftTripwire extends CraftBlockData implements Tripwire, Attachable, MultipleFacing, Powerable {

    public CraftTripwire() {
        super();
    }

    public CraftTripwire(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.type.CraftTripwire

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean DISARMED = getBoolean(net.minecraft.world.level.block.BlockTripwire.class, "disarmed");

    @Override
    public boolean isDisarmed() {
        return get(DISARMED);
    }

    @Override
    public void setDisarmed(boolean disarmed) {
        set(DISARMED, disarmed);
    }

    // org.bukkit.craftbukkit.block.data.CraftAttachable

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean ATTACHED = getBoolean(net.minecraft.world.level.block.BlockTripwire.class, "attached");

    @Override
    public boolean isAttached() {
        return get(ATTACHED);
    }

    @Override
    public void setAttached(boolean attached) {
        set(ATTACHED, attached);
    }

    // org.bukkit.craftbukkit.block.data.CraftMultipleFacing

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean[] FACES = new net.minecraft.world.level.block.state.properties.BlockStateBoolean[]{
        getBoolean(net.minecraft.world.level.block.BlockTripwire.class, "north", true), getBoolean(net.minecraft.world.level.block.BlockTripwire.class, "east", true), getBoolean(net.minecraft.world.level.block.BlockTripwire.class, "south", true), getBoolean(net.minecraft.world.level.block.BlockTripwire.class, "west", true), getBoolean(net.minecraft.world.level.block.BlockTripwire.class, "up", true), getBoolean(net.minecraft.world.level.block.BlockTripwire.class, "down", true)
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

    // org.bukkit.craftbukkit.block.data.CraftPowerable

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean POWERED = getBoolean(net.minecraft.world.level.block.BlockTripwire.class, "powered");

    @Override
    public boolean isPowered() {
        return get(POWERED);
    }

    @Override
    public void setPowered(boolean powered) {
        set(POWERED, powered);
    }
}
