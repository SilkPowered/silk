package org.bukkit.unrealized.craftbukkit.block.data.type;

import org.bukkit.unrealized.block.data.type.Snow;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public class CraftSnow extends CraftBlockData implements Snow {

    private static final net.minecraft.world.level.block.state.properties.BlockStateInteger LAYERS = getInteger("layers");

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
