package org.bukkit.unrealized.craftbukkit.block.data.type;

import org.bukkit.unrealized.block.data.type.Comparator;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public abstract class CraftComparator extends CraftBlockData implements Comparator {

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> MODE = getEnum("mode");

    @Override
    public Mode getMode() {
        return get(MODE, Mode.class);
    }

    @Override
    public void setMode(Mode mode) {
        set(MODE, mode);
    }
}
