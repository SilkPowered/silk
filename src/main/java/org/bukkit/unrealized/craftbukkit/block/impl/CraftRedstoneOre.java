/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.data.Lightable;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftRedstoneOre extends CraftBlockData implements Lightable {

    public CraftRedstoneOre() {
        super();
    }

    public CraftRedstoneOre(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.CraftLightable

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean LIT = getBoolean(net.minecraft.world.level.block.BlockRedstoneOre.class, "lit");

    @Override
    public boolean isLit() {
        return get(LIT);
    }

    @Override
    public void setLit(boolean lit) {
        set(LIT, lit);
    }
}
