package org.bukkit.unrealized.craftbukkit.block.data.type;

import org.bukkit.unrealized.block.data.type.Farmland;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public abstract class CraftFarmland extends CraftBlockData implements Farmland {

    private static final net.minecraft.world.level.block.state.properties.BlockStateInteger MOISTURE = getInteger("moisture");

    @Override
    public int getMoisture() {
        return get(MOISTURE);
    }

    @Override
    public void setMoisture(int moisture) {
        set(MOISTURE, moisture);
    }

    @Override
    public int getMaximumMoisture() {
        return getMax(MOISTURE);
    }
}
