package org.bukkit.unrealized.craftbukkit.block.data.type;

import org.bukkit.unrealized.block.data.type.Sapling;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public abstract class CraftSapling extends CraftBlockData implements Sapling {

    private static final net.minecraft.world.level.block.state.properties.BlockStateInteger STAGE = getInteger("stage");

    @Override
    public int getStage() {
        return get(STAGE);
    }

    @Override
    public void setStage(int stage) {
        set(STAGE, stage);
    }

    @Override
    public int getMaximumStage() {
        return getMax(STAGE);
    }
}