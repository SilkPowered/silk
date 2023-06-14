package org.bukkit.unrealized.craftbukkit.block.data;

import org.bukkit.unrealized.block.data.Lightable;

public abstract class CraftLightable extends CraftBlockData implements Lightable {

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean LIT = getBoolean("lit");

    @Override
    public boolean isLit() {
        return get(LIT);
    }

    @Override
    public void setLit(boolean lit) {
        set(LIT, lit);
    }
}
