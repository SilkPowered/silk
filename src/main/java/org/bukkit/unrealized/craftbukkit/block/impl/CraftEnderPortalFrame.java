/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.BlockFace;
import org.bukkit.unrealized.block.data.Directional;
import org.bukkit.unrealized.block.data.type.EndPortalFrame;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftEnderPortalFrame extends CraftBlockData implements EndPortalFrame, Directional {

    public CraftEnderPortalFrame() {
        super();
    }

    public CraftEnderPortalFrame(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.type.CraftEndPortalFrame

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean EYE = getBoolean(net.minecraft.world.level.block.BlockEnderPortalFrame.class, "eye");

    @Override
    public boolean hasEye() {
        return get(EYE);
    }

    @Override
    public void setEye(boolean eye) {
        set(EYE, eye);
    }

    // org.bukkit.craftbukkit.block.data.CraftDirectional

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> FACING = getEnum(net.minecraft.world.level.block.BlockEnderPortalFrame.class, "facing");

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
}
