package org.bukkit.craftbukkit.generator;

import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import org.bukkit.generator.BiomeParameterPoint;

public class CraftBiomeParameterPoint implements BiomeParameterPoint {

    private final double temperature;
    private final double humidity;
    private final double continentalness;
    private final double erosion;
    private final double depth;
    private final double weirdness;
    private final MultiNoiseUtil.MultiNoiseSampler sampler;

    public static BiomeParameterPoint createBiomeParameterPoint(MultiNoiseUtil.MultiNoiseSampler sampler, MultiNoiseUtil.NoiseValuePoint targetPoint) {
        return new CraftBiomeParameterPoint(sampler, MultiNoiseUtil.toFloat(targetPoint.temperatureNoise()), MultiNoiseUtil.toFloat(targetPoint.humidityNoise()), MultiNoiseUtil.toFloat(targetPoint.continentalnessNoise()), MultiNoiseUtil.toFloat(targetPoint.erosionNoise()), MultiNoiseUtil.toFloat(targetPoint.depth()), MultiNoiseUtil.toFloat(targetPoint.weirdnessNoise()));
    }

    private CraftBiomeParameterPoint(MultiNoiseUtil.MultiNoiseSampler sampler, double temperature, double humidity, double continentalness, double erosion, double depth, double weirdness) {
        this.sampler = sampler;
        this.temperature = temperature;
        this.humidity = humidity;
        this.continentalness = continentalness;
        this.erosion = erosion;
        this.depth = depth;
        this.weirdness = weirdness;
    }

    @Override
    public double getTemperature() {
        return this.temperature;
    }

    @Override
    public double getMaxTemperature() {
        return this.sampler.temperature().maxValue();
    }

    @Override
    public double getMinTemperature() {
        return this.sampler.temperature().minValue();
    }

    @Override
    public double getHumidity() {
        return this.humidity;
    }

    @Override
    public double getMaxHumidity() {
        return this.sampler.humidity().maxValue();
    }

    @Override
    public double getMinHumidity() {
        return this.sampler.humidity().minValue();
    }

    @Override
    public double getContinentalness() {
        return this.continentalness;
    }

    @Override
    public double getMaxContinentalness() {
        return this.sampler.continentalness().maxValue();
    }

    @Override
    public double getMinContinentalness() {
        return this.sampler.continentalness().minValue();
    }

    @Override
    public double getErosion() {
        return this.erosion;
    }

    @Override
    public double getMaxErosion() {
        return this.sampler.erosion().maxValue();
    }

    @Override
    public double getMinErosion() {
        return this.sampler.erosion().minValue();
    }

    @Override
    public double getDepth() {
        return this.depth;
    }

    @Override
    public double getMaxDepth() {
        return this.sampler.depth().maxValue();
    }

    @Override
    public double getMinDepth() {
        return this.sampler.depth().minValue();
    }

    @Override
    public double getWeirdness() {
        return this.weirdness;
    }

    @Override
    public double getMaxWeirdness() {
        return this.sampler.weirdness().maxValue();
    }

    @Override
    public double getMinWeirdness() {
        return this.sampler.weirdness().minValue();
    }
}
