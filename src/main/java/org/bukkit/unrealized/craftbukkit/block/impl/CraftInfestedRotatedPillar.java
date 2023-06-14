/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.Axis;
import org.bukkit.unrealized.block.data.Orientable;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftInfestedRotatedPillar extends CraftBlockData implements Orientable {

    public CraftInfestedRotatedPillar() {
        super();
    }

    public CraftInfestedRotatedPillar(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.CraftOrientable

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> AXIS = getEnum(net.minecraft.world.level.block.InfestedRotatedPillarBlock.class, "axis");

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
