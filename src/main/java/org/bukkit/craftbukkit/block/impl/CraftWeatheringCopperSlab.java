/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.craftbukkit.block.impl;

public final class CraftWeatheringCopperSlab extends org.bukkit.craftbukkit.block.data.CraftBlockData implements org.bukkit.block.data.type.Slab, org.bukkit.block.data.Waterlogged {

    public CraftWeatheringCopperSlab() {
        super();
    }

    public CraftWeatheringCopperSlab(net.minecraft.block.BlockState state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.type.CraftSlab

    private static final net.minecraft.state.property.EnumProperty<?> TYPE = getEnum(net.minecraft.block.OxidizableSlabBlock.class, "type");

    @Override
    public org.bukkit.block.data.type.Slab.Type getType() {
        return get(TYPE, org.bukkit.block.data.type.Slab.Type.class);
    }

    @Override
    public void setType(org.bukkit.block.data.type.Slab.Type type) {
        set(TYPE, type);
    }

    // org.bukkit.craftbukkit.block.data.CraftWaterlogged

    private static final net.minecraft.state.property.BooleanProperty WATERLOGGED = getBoolean(net.minecraft.block.OxidizableSlabBlock.class, "waterlogged");

    @Override
    public boolean isWaterlogged() {
        return get(WATERLOGGED);
    }

    @Override
    public void setWaterlogged(boolean waterlogged) {
        set(WATERLOGGED, waterlogged);
    }
}
