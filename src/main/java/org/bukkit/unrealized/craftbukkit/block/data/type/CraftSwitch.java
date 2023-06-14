package org.bukkit.unrealized.craftbukkit.block.data.type;

import org.bukkit.unrealized.block.data.type.Switch;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public abstract class CraftSwitch extends CraftBlockData implements Switch {

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> FACE = getEnum("face");

    @Override
    public Face getFace() {
        return get(FACE, Face.class);
    }

    @Override
    public void setFace(Face face) {
        set(FACE, face);
    }
}
