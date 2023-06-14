package org.bukkit.unrealized.craftbukkit.block.data.type;

import org.bukkit.unrealized.block.data.type.CommandBlock;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public abstract class CraftCommandBlock extends CraftBlockData implements CommandBlock {

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean CONDITIONAL = getBoolean("conditional");

    @Override
    public boolean isConditional() {
        return get(CONDITIONAL);
    }

    @Override
    public void setConditional(boolean conditional) {
        set(CONDITIONAL, conditional);
    }
}
