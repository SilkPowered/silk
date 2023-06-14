package org.bukkit.unrealized.craftbukkit.block.data.type;

import org.bukkit.unrealized.block.data.type.BigDripleaf;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public abstract class CraftBigDripleaf extends CraftBlockData implements BigDripleaf {

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> TILT = getEnum("tilt");

    @Override
    public Tilt getTilt() {
        return get(TILT, Tilt.class);
    }

    @Override
    public void setTilt(Tilt tilt) {
        set(TILT, tilt);
    }
}