package org.bukkit.unrealized.craftbukkit.block.data.type;

import org.bukkit.unrealized.block.data.type.Lectern;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public abstract class CraftLectern extends CraftBlockData implements Lectern {

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean HAS_BOOK = getBoolean("has_book");

    @Override
    public boolean hasBook() {
        return get(HAS_BOOK);
    }
}
