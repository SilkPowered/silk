/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.data.type.StructureBlock;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftStructure extends CraftBlockData implements StructureBlock {

    public CraftStructure() {
        super();
    }

    public CraftStructure(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.type.CraftStructureBlock

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> MODE = getEnum(net.minecraft.world.level.block.BlockStructure.class, "mode");

    @Override
    public Mode getMode() {
        return get(MODE, Mode.class);
    }

    @Override
    public void setMode(Mode mode) {
        set(MODE, mode);
    }
}
