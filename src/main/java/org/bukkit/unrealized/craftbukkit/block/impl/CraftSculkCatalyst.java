/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.data.type.SculkCatalyst;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftSculkCatalyst extends CraftBlockData implements SculkCatalyst {

    public CraftSculkCatalyst() {
        super();
    }

    public CraftSculkCatalyst(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.type.CraftSculkCatalyst

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean BLOOM = getBoolean(net.minecraft.world.level.block.SculkCatalystBlock.class, "bloom");

    @Override
    public boolean isBloom() {
        return get(BLOOM);
    }

    @Override
    public void setBloom(boolean bloom) {
        set(BLOOM, bloom);
    }
}
