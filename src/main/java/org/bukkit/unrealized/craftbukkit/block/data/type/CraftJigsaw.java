package org.bukkit.unrealized.craftbukkit.block.data.type;

import org.bukkit.unrealized.block.data.type.Jigsaw;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public abstract class CraftJigsaw extends CraftBlockData implements Jigsaw {

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> ORIENTATION = getEnum("orientation");

    @Override
    public Orientation getOrientation() {
        return get(ORIENTATION, Orientation.class);
    }

    @Override
    public void setOrientation(Orientation orientation) {
        set(ORIENTATION, orientation);
    }
}
