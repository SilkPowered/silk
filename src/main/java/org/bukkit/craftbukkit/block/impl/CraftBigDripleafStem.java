/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.craftbukkit.block.impl;

public final class CraftBigDripleafStem extends org.bukkit.craftbukkit.block.data.CraftBlockData implements org.bukkit.block.data.type.Dripleaf, org.bukkit.block.data.Directional, org.bukkit.block.data.Waterlogged {

    public CraftBigDripleafStem() {
        super();
    }

    public CraftBigDripleafStem(net.minecraft.block.BlockState state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.CraftDirectional

    private static final net.minecraft.state.property.EnumProperty<?> FACING = getEnum(net.minecraft.block.BigDripleafStemBlock.class, "facing");

    @Override
    public org.bukkit.block.BlockFace getFacing() {
        return get(FACING, org.bukkit.block.BlockFace.class);
    }

    @Override
    public void setFacing(org.bukkit.block.BlockFace facing) {
        set(FACING, facing);
    }

    @Override
    public java.util.Set<org.bukkit.block.BlockFace> getFaces() {
        return getValues(FACING, org.bukkit.block.BlockFace.class);
    }

    // org.bukkit.craftbukkit.block.data.CraftWaterlogged

    private static final net.minecraft.state.property.BooleanProperty WATERLOGGED = getBoolean(net.minecraft.block.BigDripleafStemBlock.class, "waterlogged");

    @Override
    public boolean isWaterlogged() {
        return get(WATERLOGGED);
    }

    @Override
    public void setWaterlogged(boolean waterlogged) {
        set(WATERLOGGED, waterlogged);
    }
}
