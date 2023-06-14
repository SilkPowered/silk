package org.bukkit.unrealized.craftbukkit.block.data;

import org.bukkit.unrealized.block.data.Openable;

public abstract class CraftOpenable extends CraftBlockData implements Openable {

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean OPEN = getBoolean("open");

    @Override
    public boolean isOpen() {
        return get(OPEN);
    }

    @Override
    public void setOpen(boolean open) {
        set(OPEN, open);
    }
}
