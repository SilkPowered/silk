/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.craftbukkit.block.impl;

public final class CraftJigsaw extends org.bukkit.craftbukkit.block.data.CraftBlockData implements org.bukkit.block.data.type.Jigsaw {

    public CraftJigsaw() {
        super();
    }

    public CraftJigsaw(net.minecraft.block.BlockState state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.type.CraftJigsaw

    private static final net.minecraft.state.property.EnumProperty<?> ORIENTATION = getEnum(net.minecraft.block.JigsawBlock.class, "orientation");

    @Override
    public org.bukkit.block.data.type.Jigsaw.Orientation getOrientation() {
        return get(ORIENTATION, org.bukkit.block.data.type.Jigsaw.Orientation.class);
    }

    @Override
    public void setOrientation(org.bukkit.block.data.type.Jigsaw.Orientation orientation) {
        set(ORIENTATION, orientation);
    }
}