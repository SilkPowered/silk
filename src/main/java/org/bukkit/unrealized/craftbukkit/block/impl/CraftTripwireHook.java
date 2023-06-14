/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.BlockFace;
import org.bukkit.unrealized.block.data.Attachable;
import org.bukkit.unrealized.block.data.Directional;
import org.bukkit.unrealized.block.data.Powerable;
import org.bukkit.unrealized.block.data.type.TripwireHook;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftTripwireHook extends CraftBlockData implements TripwireHook, Attachable, Directional, Powerable {

    public CraftTripwireHook() {
        super();
    }

    public CraftTripwireHook(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.CraftAttachable

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean ATTACHED = getBoolean(net.minecraft.world.level.block.BlockTripwireHook.class, "attached");

    @Override
    public boolean isAttached() {
        return get(ATTACHED);
    }

    @Override
    public void setAttached(boolean attached) {
        set(ATTACHED, attached);
    }

    // org.bukkit.craftbukkit.block.data.CraftDirectional

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> FACING = getEnum(net.minecraft.world.level.block.BlockTripwireHook.class, "facing");

    @Override
    public BlockFace getFacing() {
        return get(FACING, BlockFace.class);
    }

    @Override
    public void setFacing(BlockFace facing) {
        set(FACING, facing);
    }

    @Override
    public java.util.Set<BlockFace> getFaces() {
        return getValues(FACING, BlockFace.class);
    }

    // org.bukkit.craftbukkit.block.data.CraftPowerable

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean POWERED = getBoolean(net.minecraft.world.level.block.BlockTripwireHook.class, "powered");

    @Override
    public boolean isPowered() {
        return get(POWERED);
    }

    @Override
    public void setPowered(boolean powered) {
        set(POWERED, powered);
    }
}
