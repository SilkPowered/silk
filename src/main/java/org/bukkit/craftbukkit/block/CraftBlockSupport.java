package org.bukkit.craftbukkit.block;

import net.minecraft.block.SideShapeType;
import org.bukkit.block.BlockSupport;

public final class CraftBlockSupport {

    private CraftBlockSupport() {
    }

    public static BlockSupport toBukkit(SideShapeType support) {
        return switch (support) {
            case FULL -> BlockSupport.FULL;
            case CENTER -> BlockSupport.CENTER;
            case RIGID -> BlockSupport.RIGID;
            default -> throw new IllegalArgumentException("Unsupported EnumBlockSupport type: " + support + ". This is a bug.");
        };
    }

    public static SideShapeType toNMS(BlockSupport support) {
        return switch (support) {
            case FULL -> SideShapeType.FULL;
            case CENTER -> SideShapeType.CENTER;
            case RIGID -> SideShapeType.RIGID;
            default -> throw new IllegalArgumentException("Unsupported BlockSupport type: " + support + ". This is a bug.");
        };
    }
}
