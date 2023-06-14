package org.bukkit.unrealized.craftbukkit.block.data.type;

import org.bukkit.unrealized.block.data.type.Door;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public abstract class CraftDoor extends CraftBlockData implements Door {

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> HINGE = getEnum("hinge");

    @Override
    public Hinge getHinge() {
        return get(HINGE, Hinge.class);
    }

    @Override
    public void setHinge(Hinge hinge) {
        set(HINGE, hinge);
    }
}
