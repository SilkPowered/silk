package org.bukkit.craftbukkit.util;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldProperties;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkManager;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.light.LightingProvider;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.tick.EmptyTickSchedulers;
import net.minecraft.world.tick.QueryableTickScheduler;

public class DummyGeneratorAccess implements StructureWorldAccess {

    public static final StructureWorldAccess INSTANCE = new DummyGeneratorAccess();

    protected DummyGeneratorAccess() {
    }

    @Override
    public long getSeed() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ServerWorld toServerWorld() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public long getTickOrder() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public QueryableTickScheduler<Block> getBlockTickScheduler() {
        return EmptyTickSchedulers.getClientTickScheduler();
    }

    @Override
    public void scheduleBlockTick(BlockPos blockposition, Block block, int i) {
        // Used by BlockComposter
    }

    @Override
    public QueryableTickScheduler<Fluid> getFluidTickScheduler() {
        return EmptyTickSchedulers.getClientTickScheduler();
    }

    @Override
    public WorldProperties getLevelProperties() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public LocalDifficulty getLocalDifficulty(BlockPos blockposition) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public MinecraftServer getServer() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ChunkManager getChunkManager() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Random getRandom() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void playSound(PlayerEntity entityhuman, BlockPos blockposition, SoundEvent soundeffect, SoundCategory soundcategory, float f, float f1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addParticle(ParticleEffect particleparam, double d0, double d1, double d2, double d3, double d4, double d5) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void syncWorldEvent(PlayerEntity entityhuman, int i, BlockPos blockposition, int j) {
        // Used by PowderSnowBlock.removeFluid
    }

    @Override
    public void gameEvent(GameEvent gameevent, Vec3d vec3d, GameEvent.a gameevent_a) {
        // Used by BlockComposter
    }

    @Override
    public List<Entity> getOtherEntities(Entity entity, Box aabb, Predicate<? super Entity> prdct) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <T extends Entity> List<T> getEntitiesByType(TypeFilter<Entity, T> ett, Box aabb, Predicate<? super T> prdct) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<? extends PlayerEntity> getPlayers() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Chunk getChunk(int i, int i1, ChunkStatus cs, boolean bln) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getTopY(Heightmap.Type type, int i, int i1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getAmbientDarkness() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public BiomeAccess getBiomeAccess() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public RegistryEntry<Biome> getGeneratorStoredBiome(int i, int i1, int i2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isClient() {
        return false;
    }

    @Override
    public int getSeaLevel() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DimensionType getDimension() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DynamicRegistryManager getRegistryManager() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public FeatureSet getEnabledFeatures() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public float getBrightness(Direction ed, boolean bln) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public LightingProvider getLightingProvider() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public BlockEntity getBlockEntity(BlockPos blockposition) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public BlockState getBlockState(BlockPos blockposition) {
        return Blocks.AIR.getDefaultState(); // SPIGOT-6515
    }

    @Override
    public FluidState getFluidState(BlockPos blockposition) {
        return Fluids.EMPTY.getDefaultState(); // SPIGOT-6634
    }

    @Override
    public WorldBorder getWorldBorder() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean testBlockState(BlockPos bp, Predicate<BlockState> prdct) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean testFluidState(BlockPos bp, Predicate<FluidState> prdct) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean setBlockState(BlockPos blockposition, BlockState iblockdata, int i, int j) {
        return false;
    }

    @Override
    public boolean removeBlock(BlockPos blockposition, boolean flag) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean breakBlock(BlockPos blockposition, boolean flag, Entity entity, int i) {
        return false; // SPIGOT-6515
    }
}
