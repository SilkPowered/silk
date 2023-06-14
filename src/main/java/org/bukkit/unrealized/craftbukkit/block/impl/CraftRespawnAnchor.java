/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.data.type.RespawnAnchor;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftRespawnAnchor extends CraftBlockData implements RespawnAnchor {

    public CraftRespawnAnchor() {
        super();
    }

    public CraftRespawnAnchor(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.type.CraftRespawnAnchor

    private static final net.minecraft.world.level.block.state.properties.BlockStateInteger CHARGES = getInteger(net.minecraft.world.level.block.BlockRespawnAnchor.class, "charges");

    @Override
    public int getCharges() {
        return get(CHARGES);
    }

    @Override
    public void setCharges(int charges) {
        set(CHARGES, charges);
    }

    @Override
    public int getMaximumCharges() {
        return getMax(CHARGES);
    }
}
