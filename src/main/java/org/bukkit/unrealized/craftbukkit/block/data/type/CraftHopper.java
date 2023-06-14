package org.bukkit.unrealized.craftbukkit.block.data.type;

import org.bukkit.unrealized.block.data.type.Hopper;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public abstract class CraftHopper extends CraftBlockData implements Hopper {

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean ENABLED = getBoolean("enabled");

    @Override
    public boolean isEnabled() {
        return get(ENABLED);
    }

    @Override
    public void setEnabled(boolean enabled) {
        set(ENABLED, enabled);
    }
}
