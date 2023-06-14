/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.BlockFace;
import org.bukkit.unrealized.block.data.Directional;
import org.bukkit.unrealized.block.data.FaceAttachable;
import org.bukkit.unrealized.block.data.type.Grindstone;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftGrindstone extends CraftBlockData implements Grindstone, Directional, FaceAttachable {

    public CraftGrindstone() {
        super();
    }

    public CraftGrindstone(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.CraftDirectional

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> FACING = getEnum(net.minecraft.world.level.block.BlockGrindstone.class, "facing");

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

    // org.bukkit.craftbukkit.block.data.CraftFaceAttachable

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> ATTACH_FACE = getEnum(net.minecraft.world.level.block.BlockGrindstone.class, "face");

    @Override
    public AttachedFace getAttachedFace() {
        return get(ATTACH_FACE, AttachedFace.class);
    }

    @Override
    public void setAttachedFace(AttachedFace face) {
        set(ATTACH_FACE, face);
    }
}
