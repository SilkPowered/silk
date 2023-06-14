/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.data.type.TNT;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftTNT extends CraftBlockData implements TNT {

    public CraftTNT() {
        super();
    }

    public CraftTNT(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.type.CraftTNT

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean UNSTABLE = getBoolean(net.minecraft.world.level.block.BlockTNT.class, "unstable");

    @Override
    public boolean isUnstable() {
        return get(UNSTABLE);
    }

    @Override
    public void setUnstable(boolean unstable) {
        set(UNSTABLE, unstable);
    }
}