package cx.rain.silk.mixins.mixin.world.chunk;

import cx.rain.silk.mixins.bridge.world.IWorldBridge;
import cx.rain.silk.mixins.interfaces.world.chunk.IWorldChunkMixin;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.registry.Registry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.profiler.ReadableProfiler;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.UpgradeData;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.chunk.light.ChunkBlockLightProvider;
import net.minecraft.world.gen.chunk.BlendingData;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(WorldChunk.class)
public abstract class WorldChunkMixin extends Chunk implements IWorldChunkMixin {
    @Shadow @Final private World world;

    @Shadow public abstract @Nullable BlockEntity getBlockEntity(BlockPos pos, WorldChunk.CreationType creationType);

    @Shadow public abstract void addBlockEntity(BlockEntity blockEntity);

    @Shadow protected abstract <T extends BlockEntity> void updateTicker(T blockEntity);

    public WorldChunkMixin(ChunkPos pos, UpgradeData upgradeData, HeightLimitView heightLimitView, Registry<Biome> biomeRegistry, long inhabitedTime, @Nullable ChunkSection[] sectionArray, @Nullable BlendingData blendingData) {
        super(pos, upgradeData, heightLimitView, biomeRegistry, inhabitedTime, sectionArray, blendingData);
    }

    @Override
    public BlockState setBlockState(BlockPos pos, BlockState state, boolean flag, boolean doPlace) {
        int i = pos.getY();
        ChunkSection chunksection = this.getSection(this.getSectionIndex(i));
        boolean flag1 = chunksection.isEmpty();

        if (flag1 && state.isAir()) {
            return null;
        } else {
            int j = pos.getX() & 15;
            int k = i & 15;
            int l = pos.getZ() & 15;
            BlockState iblockdata1 = chunksection.setBlockState(j, k, l, state);

            if (iblockdata1 == state) {
                return null;
            } else {
                Block block = state.getBlock();

                ((Heightmap) this.heightmaps.get(Heightmap.Type.MOTION_BLOCKING)).trackUpdate(j, i, l, state);
                ((Heightmap) this.heightmaps.get(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES)).trackUpdate(j, i, l, state);
                ((Heightmap) this.heightmaps.get(Heightmap.Type.OCEAN_FLOOR)).trackUpdate(j, i, l, state);
                ((Heightmap) this.heightmaps.get(Heightmap.Type.WORLD_SURFACE)).trackUpdate(j, i, l, state);
                boolean flag2 = chunksection.isEmpty();

                if (flag1 != flag2) {
                    this.world.getChunkManager().getLightingProvider().setSectionStatus(pos, flag2);
                }

                if (ChunkBlockLightProvider.needsLightUpdate(this, pos, iblockdata1, state)) {
                    Profiler gameprofilerfiller = this.world.getProfiler();

                    gameprofilerfiller.push("updateSkyLightSources");
                    this.chunkSkyLight.isSkyLightAccessible(this, j, i, l);
                    gameprofilerfiller.swap("queueCheckLight");
                    this.world.getChunkManager().getLightingProvider().checkBlock(pos);
                    gameprofilerfiller.pop();
                }

                boolean flag3 = iblockdata1.hasBlockEntity();

                if (!this.world.isClient) {
                    iblockdata1.onStateReplaced(this.world, pos, state, flag);
                } else if (!iblockdata1.isOf(block) && flag3) {
                    this.removeBlockEntity(pos);
                }

                if (!chunksection.getBlockState(j, k, l).isOf(block)) {
                    return null;
                } else {
                    // CraftBukkit - Don't place while processing the BlockPlaceEvent, unless it's a BlockContainer. Prevents blocks such as TNT from activating when cancelled.
                    if (!this.world.isClient && doPlace && (!((IWorldBridge) this.world).silk$getCaptureBlockStates() || block instanceof net.minecraft.block.BlockWithEntity)) {
                        state.onBlockAdded(this.world, pos, iblockdata1, flag);
                    }

                    if (state.hasBlockEntity()) {
                        BlockEntity tileentity = this.getBlockEntity(pos, WorldChunk.CreationType.CHECK);

                        if (tileentity == null) {
                            tileentity = ((BlockEntityProvider) block).createBlockEntity(pos, state);
                            if (tileentity != null) {
                                this.addBlockEntity(tileentity);
                            }
                        } else {
                            tileentity.setCachedState(state);
                            this.updateTicker(tileentity);
                        }
                    }

                    this.needsSaving = true;
                    return iblockdata1;
                }
            }
        }
    }
}
