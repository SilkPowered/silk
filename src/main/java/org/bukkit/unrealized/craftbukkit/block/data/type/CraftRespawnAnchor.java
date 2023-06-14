package org.bukkit.unrealized.craftbukkit.block.data.type;

import org.bukkit.unrealized.block.data.type.RespawnAnchor;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public abstract class CraftRespawnAnchor extends CraftBlockData implements RespawnAnchor {

    private static final net.minecraft.world.level.block.state.properties.BlockStateInteger CHARGES = getInteger("charges");

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
