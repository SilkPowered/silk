/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.data.type.Farmland;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftSoil extends CraftBlockData implements Farmland {

    public CraftSoil() {
        super();
    }

    public CraftSoil(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.type.CraftFarmland

    private static final net.minecraft.world.level.block.state.properties.BlockStateInteger MOISTURE = getInteger(net.minecraft.world.level.block.BlockSoil.class, "moisture");

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
