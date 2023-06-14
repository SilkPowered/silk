/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.data.Levelled;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftComposter extends CraftBlockData implements Levelled {

    public CraftComposter() {
        super();
    }

    public CraftComposter(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.CraftLevelled

    private static final net.minecraft.world.level.block.state.properties.BlockStateInteger LEVEL = getInteger(net.minecraft.world.level.block.BlockComposter.class, "level");

    @Override
    public int getLevel() {
        return get(LEVEL);
    }

    @Override
    public void setLevel(int level) {
        set(LEVEL, level);
    }

    @Override
    public int getMaximumLevel() {
        return getMax(LEVEL);
    }
}
