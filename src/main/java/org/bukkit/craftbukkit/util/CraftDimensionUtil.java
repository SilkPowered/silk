package org.bukkit.craftbukkit.util;

import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;

public class CraftDimensionUtil {

    private CraftDimensionUtil() {
    }

    public static RegistryKey<World> getMainDimensionKey(World world) {
        RegistryKey<DimensionOptions> typeKey = world.getTypeKey();
        if (typeKey == DimensionOptions.OVERWORLD) {
            return World.OVERWORLD;
        } else if (typeKey == DimensionOptions.NETHER) {
            return World.NETHER;
        } else if (typeKey == DimensionOptions.END) {
            return World.END;
        }

        return world.dimension();
    }
}
