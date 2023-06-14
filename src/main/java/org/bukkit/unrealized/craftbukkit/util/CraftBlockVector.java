package org.bukkit.unrealized.craftbukkit.util;

import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import org.bukkit.unrealized.util.BlockVector;

public final class CraftBlockVector {

    private CraftBlockVector() {
    }

    public static BlockPosition toBlockPosition(BlockVector blockVector) {
        return new BlockPosition(blockVector.getBlockX(), blockVector.getBlockY(), blockVector.getBlockZ());
    }

    public static BlockVector toBukkit(BaseBlockPosition baseBlockPosition) {
        return new BlockVector(baseBlockPosition.getX(), baseBlockPosition.getY(), baseBlockPosition.getZ());
    }
}
