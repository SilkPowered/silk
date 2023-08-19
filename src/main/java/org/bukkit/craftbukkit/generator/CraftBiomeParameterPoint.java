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

    public static BiomeParameterPoint createBiomeParameterPoint(MultiNoiseUtil.MultiNoiseSampler sampler, MultiNoiseUtil.h targetPoint) {
        return new CraftBiomeParameterPoint(sampler, MultiNoiseUtil.toFloat(targetPoint.temperature()), MultiNoiseUtil.toFloat(targetPoint.humidity()), MultiNoiseUtil.toFloat(targetPoint.continentalness()), MultiNoiseUtil.toFloat(targetPoint.erosion()), MultiNoiseUtil.toFloat(targetPoint.depth()), MultiNoiseUtil.toFloat(targetPoint.weirdness()));
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
        return this.sampler.comp_364().comp_378();
    }

    @Override
    public double getMinTemperature() {
        return this.sampler.comp_364().comp_377();
    }

    @Override
    public double getHumidity() {
        return this.humidity;
    }

    @Override
    public double getMaxHumidity() {
        return this.sampler.comp_365().comp_378();
    }

    @Override
    public double getMinHumidity() {
        return this.sampler.comp_365().comp_377();
    }

    @Override
    public double getContinentalness() {
        return this.continentalness;
    }

    @Override
    public double getMaxContinentalness() {
        return this.sampler.comp_366().comp_378();
    }

    @Override
    public double getMinContinentalness() {
        return this.sampler.comp_366().comp_377();
    }

    @Override
    public double getErosion() {
        return this.erosion;
    }

    @Override
    public double getMaxErosion() {
        return this.sampler.comp_367().comp_378();
    }

    @Override
    public double getMinErosion() {
        return this.sampler.comp_367().comp_377();
    }

    @Override
    public double getDepth() {
        return this.depth;
    }

    @Override
    public double getMaxDepth() {
        return this.sampler.comp_368().comp_378();
    }

    @Override
    public double getMinDepth() {
        return this.sampler.comp_368().comp_377();
    }

    @Override
    public double getWeirdness() {
        return this.weirdness;
    }

    @Override
    public double getMaxWeirdness() {
        return this.sampler.comp_369().comp_378();
    }

    @Override
    public double getMinWeirdness() {
        return this.sampler.comp_369().comp_377();
    }
}
