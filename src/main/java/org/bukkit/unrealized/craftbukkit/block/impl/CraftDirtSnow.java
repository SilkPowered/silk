/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.data.Snowable;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftDirtSnow extends CraftBlockData implements Snowable {

    public CraftDirtSnow() {
        super();
    }

    public CraftDirtSnow(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.CraftSnowable

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean SNOWY = getBoolean(net.minecraft.world.level.block.BlockDirtSnow.class, "snowy");

    @Override
    public boolean isSnowy() {
        return get(SNOWY);
    }

    @Override
    public void setSnowy(boolean snowy) {
        set(SNOWY, snowy);
    }
}