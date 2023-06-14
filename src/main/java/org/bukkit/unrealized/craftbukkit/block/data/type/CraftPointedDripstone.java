package org.bukkit.unrealized.craftbukkit.block.data.type;

import org.bukkit.unrealized.block.BlockFace;
import org.bukkit.unrealized.block.data.type.PointedDripstone;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public abstract class CraftPointedDripstone extends CraftBlockData implements PointedDripstone {

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> VERTICAL_DIRECTION = getEnum("vertical_direction");
    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> THICKNESS = getEnum("thickness");

    @Override
    public BlockFace getVerticalDirection() {
        return get(VERTICAL_DIRECTION, BlockFace.class);
    }

    @Override
    public void setVerticalDirection(BlockFace direction) {
        set(VERTICAL_DIRECTION, direction);
    }

    @Override
    public java.util.Set<BlockFace> getVerticalDirections() {
        return getValues(VERTICAL_DIRECTION, BlockFace.class);
    }

    @Override
    public Thickness getThickness() {
        return get(THICKNESS, Thickness.class);
    }

    @Override
    public void setThickness(Thickness thickness) {
        set(THICKNESS, thickness);
    }
}
