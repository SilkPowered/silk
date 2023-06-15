package org.bukkit.craftbukkit;

import net.minecraft.world.RaycastContext;
import org.bukkit.FluidCollisionMode;

public final class CraftFluidCollisionMode {

    private CraftFluidCollisionMode() {}

    public static RaycastContext.FluidHandling toNMS(FluidCollisionMode fluidCollisionMode) {
        if (fluidCollisionMode == null) return null;

        switch (fluidCollisionMode) {
            case ALWAYS:
                return RaycastContext.FluidHandling.ANY;
            case SOURCE_ONLY:
                return RaycastContext.FluidHandling.SOURCE_ONLY;
            case NEVER:
                return RaycastContext.FluidHandling.NONE;
            default:
                return null;
        }
    }
}
