package org.bukkit.craftbukkit.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import org.bukkit.util.BlockVector;

public final class CraftBlockVector {

    private CraftBlockVector() {
    }

    public static BlockPos toBlockPosition(BlockVector blockVector) {
        return new BlockPos(blockVector.getBlockX(), blockVector.getBlockY(), blockVector.getBlockZ());
    }

    public static BlockVector toBukkit(Vec3i baseBlockPosition) {
        return new BlockVector(baseBlockPosition.getX(), baseBlockPosition.getY(), baseBlockPosition.getZ());
    }
}
