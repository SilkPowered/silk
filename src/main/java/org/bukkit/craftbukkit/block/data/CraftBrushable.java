package org.bukkit.craftbukkit.block.data;

import org.bukkit.block.data.Brushable;

public abstract class CraftBrushable extends CraftBlockData implements Brushable {

    private static final net.minecraft.state.property.IntProperty DUSTED = getInteger("dusted");

    @Override
    public int getDusted() {
        return get(DUSTED);
    }

    @Override
    public void setDusted(int dusted) {
        set(DUSTED, dusted);
    }

    @Override
    public int getMaximumDusted() {
        return getMax(DUSTED);
    }
}
