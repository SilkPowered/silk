/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.data.type.Snow;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftSnow extends CraftBlockData implements Snow {

    public CraftSnow() {
        super();
    }

    public CraftSnow(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.type.CraftSnow

    private static final net.minecraft.world.level.block.state.properties.BlockStateInteger LAYERS = getInteger(net.minecraft.world.level.block.BlockSnow.class, "layers");

    @Override
    public int getLayers() {
        return get(LAYERS);
    }

    @Override
    public void setLayers(int layers) {
        set(LAYERS, layers);
    }

    @Override
    public int getMinimumLayers() {
        return getMin(LAYERS);
    }

    @Override
    public int getMaximumLayers() {
        return getMax(LAYERS);
    }
}
