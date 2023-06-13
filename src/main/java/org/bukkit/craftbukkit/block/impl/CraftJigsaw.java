/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.craftbukkit.block.impl;

public final class CraftJigsaw extends org.bukkit.craftbukkit.block.data.CraftBlockData implements org.bukkit.block.data.type.Jigsaw {

    public CraftJigsaw() {
        super();
    }

    public CraftJigsaw(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.type.CraftJigsaw

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> ORIENTATION = getEnum(net.minecraft.world.level.block.BlockJigsaw.class, "orientation");

    @Override
    public Orientation getOrientation() {
        return get(ORIENTATION, Orientation.class);
    }

    @Override
    public void setOrientation(Orientation orientation) {
        set(ORIENTATION, orientation);
    }
}
