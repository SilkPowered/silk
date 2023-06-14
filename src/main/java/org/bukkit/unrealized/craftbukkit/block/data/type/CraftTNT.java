package org.bukkit.unrealized.craftbukkit.block.data.type;

import org.bukkit.unrealized.block.data.type.TNT;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public abstract class CraftTNT extends CraftBlockData implements TNT {

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean UNSTABLE = getBoolean("unstable");

    @Override
    public boolean isUnstable() {
        return get(UNSTABLE);
    }

    @Override
    public void setUnstable(boolean unstable) {
        set(UNSTABLE, unstable);
    }
}
