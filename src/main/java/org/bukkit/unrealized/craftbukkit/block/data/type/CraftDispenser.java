package org.bukkit.unrealized.craftbukkit.block.data.type;

import org.bukkit.unrealized.block.data.type.Dispenser;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public abstract class CraftDispenser extends CraftBlockData implements Dispenser {

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean TRIGGERED = getBoolean("triggered");

    @Override
    public boolean isTriggered() {
        return get(TRIGGERED);
    }

    @Override
    public void setTriggered(boolean triggered) {
        set(TRIGGERED, triggered);
    }
}
