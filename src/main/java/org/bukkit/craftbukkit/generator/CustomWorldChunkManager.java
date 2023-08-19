package org.bukkit.craftbukkit.generator;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import org.bukkit.block.Biome;
import org.bukkit.craftbukkit.block.CraftBlock;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;

public class CustomWorldChunkManager extends BiomeSource {

    private final WorldInfo worldInfo;
    private final BiomeProvider biomeProvider;
    private final Registry<net.minecraft.world.biome.Biome> registry;

    private static List<RegistryEntry<net.minecraft.world.biome.Biome>> biomeListToBiomeBaseList(List<Biome> biomes, Registry<net.minecraft.world.biome.Biome> registry) {
        List<RegistryEntry<net.minecraft.world.biome.Biome>> biomeBases = new ArrayList<>();

        for (Biome biome : biomes) {
            Preconditions.checkArgument(biome != Biome.CUSTOM, "Cannot use the biome %s", biome);
            biomeBases.add(CraftBlock.biomeToBiomeBase(registry, biome));
        }

        return biomeBases;
    }

    public CustomWorldChunkManager(WorldInfo worldInfo, BiomeProvider biomeProvider, Registry<net.minecraft.world.biome.Biome> registry) {
        this.worldInfo = worldInfo;
        this.biomeProvider = biomeProvider;
        this.registry = registry;
    }

    @Override
    protected Codec<? extends BiomeSource> getCodec() {
        throw new UnsupportedOperationException("Cannot serialize CustomWorldChunkManager");
    }

    @Override
    public RegistryEntry<net.minecraft.world.biome.Biome> getBiome(int x, int y, int z, MultiNoiseUtil.MultiNoiseSampler sampler) {
        Biome biome = biomeProvider.getBiome(worldInfo, x << 2, y << 2, z << 2, CraftBiomeParameterPoint.createBiomeParameterPoint(sampler, sampler.sample(x, y, z)));
        Preconditions.checkArgument(biome != Biome.CUSTOM, "Cannot set the biome to %s", biome);

        return CraftBlock.biomeToBiomeBase(registry, biome);
    }

    @Override
    protected Stream<RegistryEntry<net.minecraft.world.biome.Biome>> biomeStream() {
        return biomeListToBiomeBaseList(biomeProvider.getBiomes(worldInfo), registry).stream();
    }
}
