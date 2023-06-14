package org.bukkit.unrealized.craftbukkit.block.data;

import org.bukkit.unrealized.block.data.Powerable;

public abstract class CraftPowerable extends CraftBlockData implements Powerable {

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean POWERED = getBoolean("powered");

    @Override
    public boolean isPowered() {
        return get(POWERED);
    }

    @Override
    public void setPowered(boolean powered) {
        set(POWERED, powered);
    }
}
