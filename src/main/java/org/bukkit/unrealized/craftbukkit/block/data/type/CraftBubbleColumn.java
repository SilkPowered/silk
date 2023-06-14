package org.bukkit.unrealized.craftbukkit.block.data.type;

import org.bukkit.unrealized.block.data.type.BubbleColumn;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public abstract class CraftBubbleColumn extends CraftBlockData implements BubbleColumn {

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean DRAG = getBoolean("drag");

    @Override
    public boolean isDrag() {
        return get(DRAG);
    }

    @Override
    public void setDrag(boolean drag) {
        set(DRAG, drag);
    }
}
