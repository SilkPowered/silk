package org.bukkit.craftbukkit;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import org.bukkit.Art;

public class CraftArt {
    private static final BiMap<RegistryEntry<PaintingVariant>, Art> artwork;

    static {
        ImmutableBiMap.Builder<RegistryEntry<PaintingVariant>, Art> artworkBuilder = ImmutableBiMap.builder();
        for (RegistryKey<PaintingVariant> key : Registries.PAINTING_VARIANT.getKeys()) {
            artworkBuilder.put(Registries.PAINTING_VARIANT.getHolderOrThrow(key), Art.getByName(key.getValue().getPath()));
        }

        artwork = artworkBuilder.build();
    }

    public static Art NotchToBukkit(RegistryEntry<PaintingVariant> art) {
        Art bukkit = artwork.get(art);
        Preconditions.checkArgument(bukkit != null);
        return bukkit;
    }

    public static RegistryEntry<PaintingVariant> BukkitToNotch(Art art) {
        RegistryEntry<PaintingVariant> nms = artwork.inverse().get(art);
        Preconditions.checkArgument(nms != null);
        return nms;
    }
}
