package org.bukkit.craftbukkit;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.locks.LockSupport;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerEntityManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.thread.TaskExecutor;
import net.minecraft.world.ChunkSerializer;
import net.minecraft.world.Heightmap;
import net.minecraft.world.LightType;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.chunk.ChunkNibbleArray;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.PalettedContainer;
import net.minecraft.world.chunk.ReadableContainer;
import net.minecraft.world.chunk.WrapperProtoChunk;
import net.minecraft.world.chunk.light.LightingProvider;
import net.minecraft.world.storage.EntityChunkDataAccess;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.CraftBlock;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;

public class CraftChunk implements Chunk {
    private final ServerWorld worldServer;
    private final int x;
    private final int z;
    private static final PalettedContainer<net.minecraft.block.BlockState> emptyBlockIDs = new PalettedContainer<>(net.minecraft.block.Block.BLOCK_STATE_REGISTRY, Blocks.AIR.getDefaultState(), PalettedContainer.d.SECTION_STATES);
    private static final byte[] emptyLight = new byte[2048];

    public CraftChunk(net.minecraft.world.chunk.WorldChunk chunk) {
        worldServer = chunk.level;
        x = chunk.getPos().x;
        z = chunk.getPos().z;
    }

    public CraftChunk(ServerWorld worldServer, int x, int z) {
        this.worldServer = worldServer;
        this.x = x;
        this.z = z;
    }

    @Override
    public World getWorld() {
        return worldServer.getWorld();
    }

    public CraftWorld getCraftWorld() {
        return (CraftWorld) getWorld();
    }

    public net.minecraft.world.chunk.Chunk getHandle(ChunkStatus chunkStatus) {
        net.minecraft.world.chunk.Chunk chunkAccess = worldServer.getChunk(x, z, chunkStatus);

        // SPIGOT-7332: Get unwrapped extension
        if (chunkAccess instanceof WrapperProtoChunk extension) {
            return extension.getWrappedChunk();
        }

        return chunkAccess;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getZ() {
        return z;
    }

    @Override
    public String toString() {
        return "CraftChunk{" + "x=" + getX() + "z=" + getZ() + '}';
    }

    @Override
    public Block getBlock(int x, int y, int z) {
        validateChunkCoordinates(worldServer.C_(), worldServer.getTopY(), x, y, z);

        return new CraftBlock(worldServer, new BlockPos((this.x << 4) | x, y, (this.z << 4) | z));
    }

    @Override
    public boolean isEntitiesLoaded() {
        return getCraftWorld().getHandle().entityManager.isLoaded(ChunkPos.toLong(x, z));
    }

    @Override
    public Entity[] getEntities() {
        if (!isLoaded()) {
            getWorld().getChunkAt(x, z); // Transient load for this tick
        }

        ServerEntityManager<net.minecraft.entity.Entity> entityManager = getCraftWorld().getHandle().entityManager;
        long pair = ChunkPos.toLong(x, z);

        if (entityManager.isLoaded(pair)) {
            return entityManager.getEntities(new ChunkPos(x, z)).stream()
                    .map(net.minecraft.entity.Entity::getBukkitEntity)
                    .filter(Objects::nonNull).toArray(Entity[]::new);
        }

        entityManager.readIfFresh(pair); // Start entity loading

        // SPIGOT-6772: Use entity mailbox and re-schedule entities if they get unloaded
        TaskExecutor<Runnable> mailbox = ((EntityChunkDataAccess) entityManager.permanentStorage).entityDeserializerQueue;
        BooleanSupplier supplier = () -> {
            // only execute inbox if our entities are not present
            if (entityManager.isLoaded(pair)) {
                return true;
            }

            if (!entityManager.isPending(pair)) {
                // Our entities got unloaded, this should normally not happen.
                entityManager.readIfFresh(pair); // Re-start entity loading
            }

            // tick loading inbox, which loads the created entities to the world
            // (if present)
            entityManager.tick();
            // check if our entities are loaded
            return entityManager.isLoaded(pair);
        };

        // now we wait until the entities are loaded,
        // the converting from NBT to entity object is done on the main Thread which is why we wait
        while (!supplier.getAsBoolean()) {
            if (mailbox.getQueueSize() != 0) {
                mailbox.run();
            } else {
                Thread.yield();
                LockSupport.parkNanos("waiting for entity loading", 100000L);
            }
        }

        return entityManager.getEntities(new ChunkPos(x, z)).stream()
                .map(net.minecraft.entity.Entity::getBukkitEntity)
                .filter(Objects::nonNull).toArray(Entity[]::new);
    }

    @Override
    public BlockState[] getTileEntities() {
        if (!isLoaded()) {
            getWorld().getChunkAt(x, z); // Transient load for this tick
        }
        int index = 0;
        net.minecraft.world.chunk.Chunk chunk = getHandle(ChunkStatus.FULL);

        BlockState[] entities = new BlockState[chunk.blockEntities.size()];

        for (BlockPos position : chunk.blockEntities.keySet()) {
            entities[index++] = worldServer.getWorld().getBlockAt(position.getX(), position.getY(), position.getZ()).getState();
        }

        return entities;
    }

    @Override
    public boolean isGenerated() {
        net.minecraft.world.chunk.Chunk chunk = getHandle(ChunkStatus.EMPTY);
        return chunk.getStatus().isAtLeast(ChunkStatus.FULL);
    }

    @Override
    public boolean isLoaded() {
        return getWorld().isChunkLoaded(this);
    }

    @Override
    public boolean load() {
        return getWorld().loadChunk(getX(), getZ(), true);
    }

    @Override
    public boolean load(boolean generate) {
        return getWorld().loadChunk(getX(), getZ(), generate);
    }

    @Override
    public boolean unload() {
        return getWorld().unloadChunk(getX(), getZ());
    }

    @Override
    public boolean isSlimeChunk() {
        // 987234911L is deterimined in EntitySlime when seeing if a slime can spawn in a chunk
        return ChunkRandom.getSlimeRandom(getX(), getZ(), getWorld().getSeed(), worldServer.spigotConfig.slimeSeed).nextInt(10) == 0;
    }

    @Override
    public boolean unload(boolean save) {
        return getWorld().unloadChunk(getX(), getZ(), save);
    }

    @Override
    public boolean isForceLoaded() {
        return getWorld().isChunkForceLoaded(getX(), getZ());
    }

    @Override
    public void setForceLoaded(boolean forced) {
        getWorld().setChunkForceLoaded(getX(), getZ(), forced);
    }

    @Override
    public boolean addPluginChunkTicket(Plugin plugin) {
        return getWorld().addPluginChunkTicket(getX(), getZ(), plugin);
    }

    @Override
    public boolean removePluginChunkTicket(Plugin plugin) {
        return getWorld().removePluginChunkTicket(getX(), getZ(), plugin);
    }

    @Override
    public Collection<Plugin> getPluginChunkTickets() {
        return getWorld().getPluginChunkTickets(getX(), getZ());
    }

    @Override
    public long getInhabitedTime() {
        return getHandle(ChunkStatus.EMPTY).getInhabitedTime();
    }

    @Override
    public void setInhabitedTime(long ticks) {
        Preconditions.checkArgument(ticks >= 0, "ticks cannot be negative");

        getHandle(ChunkStatus.STRUCTURE_STARTS).setInhabitedTime(ticks);
    }

    @Override
    public boolean contains(BlockData block) {
        Preconditions.checkArgument(block != null, "Block cannot be null");

        Predicate<net.minecraft.block.BlockState> nms = Predicates.equalTo(((CraftBlockData) block).getState());
        for (ChunkSection section : getHandle(ChunkStatus.FULL).getSectionArray()) {
            if (section != null && section.getBlockStateContainer().a(nms)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean contains(Biome biome) {
        Preconditions.checkArgument(biome != null, "Biome cannot be null");

        net.minecraft.world.chunk.Chunk chunk = getHandle(ChunkStatus.BIOMES);
        Predicate<RegistryEntry<net.minecraft.world.biome.Biome>> nms = Predicates.equalTo(CraftBlock.biomeToBiomeBase(chunk.biomeRegistry, biome));
        for (ChunkSection section : chunk.getSectionArray()) {
            if (section != null && section.getBiomeContainer().hasAny(nms)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public ChunkSnapshot getChunkSnapshot() {
        return getChunkSnapshot(true, false, false);
    }

    @Override
    public ChunkSnapshot getChunkSnapshot(boolean includeMaxBlockY, boolean includeBiome, boolean includeBiomeTempRain) {
        net.minecraft.world.chunk.Chunk chunk = getHandle(ChunkStatus.FULL);

        ChunkSection[] cs = chunk.getSectionArray();
        PalettedContainer[] sectionBlockIDs = new PalettedContainer[cs.length];
        byte[][] sectionSkyLights = new byte[cs.length][];
        byte[][] sectionEmitLights = new byte[cs.length][];
        boolean[] sectionEmpty = new boolean[cs.length];
        ReadableContainer<RegistryEntry<net.minecraft.world.biome.Biome>>[] biome = (includeBiome || includeBiomeTempRain) ? new PalettedContainer[cs.length] : null;

        Registry<net.minecraft.world.biome.Biome> iregistry = worldServer.B_().get(RegistryKeys.BIOME);
        Codec<ReadableContainer<RegistryEntry<net.minecraft.world.biome.Biome>>> biomeCodec = PalettedContainer.codecRO(iregistry.getIndexedEntries(), iregistry.createEntryCodec(), PalettedContainer.d.SECTION_BIOMES, iregistry.getHolderOrThrow(BiomeKeys.PLAINS));

        for (int i = 0; i < cs.length; i++) {
            NbtCompound data = new NbtCompound();

            data.put("block_states", ChunkSerializer.BLOCK_STATE_CODEC.encodeStart(NbtOps.INSTANCE, cs[i].getBlockStateContainer()).get().left().get());
            sectionBlockIDs[i] = ChunkSerializer.BLOCK_STATE_CODEC.parse(NbtOps.INSTANCE, data.getCompound("block_states")).get().left().get();

            LightingProvider lightengine = worldServer.s_();
            ChunkNibbleArray skyLightArray = lightengine.get(LightType.SKY).getLightSection(ChunkSectionPos.from(x, i, z));
            if (skyLightArray == null) {
                sectionSkyLights[i] = emptyLight;
            } else {
                sectionSkyLights[i] = new byte[2048];
                System.arraycopy(skyLightArray.asByteArray(), 0, sectionSkyLights[i], 0, 2048);
            }
            ChunkNibbleArray emitLightArray = lightengine.get(LightType.BLOCK).getLightSection(ChunkSectionPos.from(x, i, z));
            if (emitLightArray == null) {
                sectionEmitLights[i] = emptyLight;
            } else {
                sectionEmitLights[i] = new byte[2048];
                System.arraycopy(emitLightArray.asByteArray(), 0, sectionEmitLights[i], 0, 2048);
            }

            if (biome != null) {
                data.put("biomes", biomeCodec.encodeStart(NbtOps.INSTANCE, cs[i].getBiomeContainer()).get().left().get());
                biome[i] = biomeCodec.parse(NbtOps.INSTANCE, data.getCompound("biomes")).get().left().get();
            }
        }

        Heightmap hmap = null;

        if (includeMaxBlockY) {
            hmap = new Heightmap(chunk, Heightmap.Type.MOTION_BLOCKING);
            hmap.setTo(chunk, Heightmap.Type.MOTION_BLOCKING, chunk.heightmaps.get(Heightmap.Type.MOTION_BLOCKING).asLongArray());
        }

        World world = getWorld();
        return new CraftChunkSnapshot(getX(), getZ(), chunk.C_(), chunk.getTopY(), world.getName(), world.getFullTime(), sectionBlockIDs, sectionSkyLights, sectionEmitLights, sectionEmpty, hmap, iregistry, biome);
    }

    @Override
    public PersistentDataContainer getPersistentDataContainer() {
        return getHandle(ChunkStatus.STRUCTURE_STARTS).persistentDataContainer;
    }

    @Override
    public LoadLevel getLoadLevel() {
        net.minecraft.world.chunk.WorldChunk chunk = worldServer.getChunkIfLoaded(getX(), getZ());
        if (chunk == null) {
            return LoadLevel.UNLOADED;
        }
        return LoadLevel.values()[chunk.getLevelType().ordinal()];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CraftChunk that = (CraftChunk) o;

        if (x != that.x) return false;
        if (z != that.z) return false;
        return worldServer.equals(that.worldServer);
    }

    @Override
    public int hashCode() {
        int result = worldServer.hashCode();
        result = 31 * result + x;
        result = 31 * result + z;
        return result;
    }

    public static ChunkSnapshot getEmptyChunkSnapshot(int x, int z, CraftWorld world, boolean includeBiome, boolean includeBiomeTempRain) {
        net.minecraft.world.chunk.Chunk actual = world.getHandle().getChunk(x, z, (includeBiome || includeBiomeTempRain) ? ChunkStatus.BIOMES : ChunkStatus.EMPTY);

        /* Fill with empty data */
        int hSection = actual.countVerticalSections();
        PalettedContainer[] blockIDs = new PalettedContainer[hSection];
        byte[][] skyLight = new byte[hSection][];
        byte[][] emitLight = new byte[hSection][];
        boolean[] empty = new boolean[hSection];
        Registry<net.minecraft.world.biome.Biome> iregistry = world.getHandle().B_().get(RegistryKeys.BIOME);
        PalettedContainer<RegistryEntry<net.minecraft.world.biome.Biome>>[] biome = (includeBiome || includeBiomeTempRain) ? new PalettedContainer[hSection] : null;
        Codec<ReadableContainer<RegistryEntry<net.minecraft.world.biome.Biome>>> biomeCodec = PalettedContainer.codecRO(iregistry.getIndexedEntries(), iregistry.createEntryCodec(), PalettedContainer.d.SECTION_BIOMES, iregistry.getHolderOrThrow(BiomeKeys.PLAINS));

        for (int i = 0; i < hSection; i++) {
            blockIDs[i] = emptyBlockIDs;
            skyLight[i] = emptyLight;
            emitLight[i] = emptyLight;
            empty[i] = true;

            if (biome != null) {
                biome[i] = (PalettedContainer<RegistryEntry<net.minecraft.world.biome.Biome>>) biomeCodec.parse(NbtOps.INSTANCE, biomeCodec.encodeStart(NbtOps.INSTANCE, actual.getSection(i).getBiomeContainer()).get().left().get()).get().left().get();
            }
        }

        return new CraftChunkSnapshot(x, z, world.getMinHeight(), world.getMaxHeight(), world.getName(), world.getFullTime(), blockIDs, skyLight, emitLight, empty, new Heightmap(actual, Heightmap.Type.MOTION_BLOCKING), iregistry, biome);
    }

    static void validateChunkCoordinates(int minY, int maxY, int x, int y, int z) {
        Preconditions.checkArgument(0 <= x && x <= 15, "x out of range (expected 0-15, got %s)", x);
        Preconditions.checkArgument(minY <= y && y <= maxY, "y out of range (expected %s-%s, got %s)", minY, maxY, y);
        Preconditions.checkArgument(0 <= z && z <= 15, "z out of range (expected 0-15, got %s)", z);
    }

    static {
        Arrays.fill(emptyLight, (byte) 0xFF);
    }
}
