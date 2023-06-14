package org.bukkit.unrealized.craftbukkit.block.data;

import org.bukkit.unrealized.Axis;
import org.bukkit.unrealized.block.data.Orientable;

public class CraftOrientable extends CraftBlockData implements Orientable {

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> AXIS = getEnum("axis");

    @Override
    public Axis getAxis() {
        return get(AXIS, Axis.class);
    }

    @Override
    public void setAxis(Axis axis) {
        set(AXIS, axis);
    }

    @Override
    public java.util.Set<Axis> getAxes() {
        return getValues(AXIS, Axis.class);
    }
}
