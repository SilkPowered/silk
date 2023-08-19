package org.bukkit.craftbukkit.generator;

import java.util.function.Function;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.source.BiomeSource;

// Do not implement functions to this class, add to NormalChunkGenerator
public abstract class InternalChunkGenerator extends net.minecraft.world.gen.chunk.ChunkGenerator {

    public InternalChunkGenerator(BiomeSource worldchunkmanager, Function<RegistryEntry<Biome>, GenerationSettings> function) {
        super(worldchunkmanager, function);
    }
}
