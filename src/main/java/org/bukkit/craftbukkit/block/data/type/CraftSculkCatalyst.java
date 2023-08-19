package org.bukkit.craftbukkit.block.data.type;

import org.bukkit.block.data.type.SculkCatalyst;
import org.bukkit.craftbukkit.block.data.CraftBlockData;

public abstract class CraftSculkCatalyst extends CraftBlockData implements SculkCatalyst {

    private static final net.minecraft.state.property.BooleanProperty BLOOM = getBoolean("bloom");

    @Override
    public boolean isBloom() {
        return get(BLOOM);
    }

    @Override
    public void setBloom(boolean bloom) {
        set(BLOOM, bloom);
    }
}
