package org.bukkit.unrealized.craftbukkit.block.data.type;

import org.bukkit.unrealized.block.data.type.Bamboo;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public abstract class CraftBamboo extends CraftBlockData implements Bamboo {

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> LEAVES = getEnum("leaves");

    @Override
    public Leaves getLeaves() {
        return get(LEAVES, Leaves.class);
    }

    @Override
    public void setLeaves(Leaves leaves) {
        set(LEAVES, leaves);
    }
}
