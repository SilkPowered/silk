/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.craftbukkit.block.impl;

public final class CraftTallSeagrass extends org.bukkit.craftbukkit.block.data.CraftBlockData implements org.bukkit.block.data.Bisected {

    public CraftTallSeagrass() {
        super();
    }

    public CraftTallSeagrass(net.minecraft.block.BlockState state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.CraftBisected

    private static final net.minecraft.state.property.EnumProperty<?> HALF = getEnum(net.minecraft.block.TallSeagrassBlock.class, "half");

    @Override
    public org.bukkit.block.data.Bisected.Half getHalf() {
        return get(HALF, org.bukkit.block.data.Bisected.Half.class);
    }

    @Override
    public void setHalf(org.bukkit.block.data.Bisected.Half half) {
        set(HALF, half);
    }
}
