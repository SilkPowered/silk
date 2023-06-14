package org.bukkit.unrealized.craftbukkit.block.data.type;

import org.bukkit.unrealized.block.data.type.TechnicalPiston;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public abstract class CraftTechnicalPiston extends CraftBlockData implements TechnicalPiston {

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> TYPE = getEnum("type");

    @Override
    public Type getType() {
        return get(TYPE, Type.class);
    }

    @Override
    public void setType(Type type) {
        set(TYPE, type);
    }
}
