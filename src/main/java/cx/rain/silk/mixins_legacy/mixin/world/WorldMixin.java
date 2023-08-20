package cx.rain.silk.mixins_legacy.mixin.world;

import cx.rain.silk.mixins_legacy.bridge.world.IWorldBridge;
import cx.rain.silk.mixins_legacy.interfaces.world.IWorldMixin;
import cx.rain.silk.mixins_legacy.interfaces.world.chunk.IWorldChunkMixin;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ChunkLevelType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.chunk.WorldChunk;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.block.CapturedBlockState;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.LinkedHashMap;
import java.util.Map;

@Mixin(World.class)
public abstract class WorldMixin implements IWorldMixin, IWorldBridge, HeightLimitView, WorldAccess {
    @Shadow public abstract boolean isInBuildLimit(BlockPos pos);

    @Shadow public abstract boolean isDebugWorld();

    @Shadow @Final public boolean isClient;

    @Shadow public abstract WorldChunk getWorldChunk(BlockPos pos);

    @Shadow public abstract BlockState getBlockState(BlockPos pos);

    @Shadow public abstract void scheduleBlockRerenderIfNeeded(BlockPos pos, BlockState old, BlockState updated);

    @Shadow public abstract void updateListeners(BlockPos var1, BlockState var2, BlockState var3, int var4);

    @Shadow public abstract void updateNeighbor(BlockPos pos, Block sourceBlock, BlockPos sourcePos);

    @Shadow public abstract void updateComparators(BlockPos pos, Block block);

    @Shadow public abstract void onBlockChanged(BlockPos pos, BlockState oldBlock, BlockState newBlock);

    private CraftWorld world;

    @Override
    public CraftWorld getWorld() {
        return world;
    }

    @Override
    public CraftServer getCraftServer() {
        return (CraftServer) Bukkit.getServer();
    }


    private boolean silk$preventPoiUpdated = false; // CraftBukkit - SPIGOT-5710
    private boolean silk$captureTreeGeneration = false;
    private boolean silk$captureBlockStates = false;
    private Map<BlockPos, CapturedBlockState> silk$capturedBlockStates = new LinkedHashMap<>();

    private static BlockPos silk$lastPhysicsProblem;

    @Override
    public boolean silk$getPreventPoiUpdated() {
        return silk$preventPoiUpdated;
    }

    @Override
    public void silk$setPreventPoiUpdated(boolean value) {
        silk$preventPoiUpdated = value;
    }

    @Override
    public boolean silk$getCaptureTreeGeneration() {
        return silk$captureTreeGeneration;
    }

    @Override
    public void silk$setCaptureTreeGeneration(boolean value) {
        silk$captureTreeGeneration = value;
    }

    @Override
    public boolean silk$getCaptureBlockStates() {
        return silk$captureBlockStates;
    }

    @Override
    public void silk$setCaptureBlockStates(boolean value) {
        silk$captureBlockStates = value;
    }

    @Override
    public Map<BlockPos, CapturedBlockState> silk$getCapturedBlockStates() {
        return silk$capturedBlockStates;
    }

    @Inject(at = @At("HEAD"),
            method = "setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;II)Z",
            cancellable = true)
    private void silk$beforeSetBlockState(BlockPos pos, BlockState state, int flags, int maxUpdateDepth,
                               CallbackInfoReturnable<Boolean> cir) {
        if (silk$captureTreeGeneration) {
            CapturedBlockState blockstate = silk$capturedBlockStates.get(pos);
            if (blockstate == null) {
                blockstate = CapturedBlockState.getTreeBlockState((World) (Object) this, pos, flags);
                this.silk$capturedBlockStates.put(pos.toImmutable(), blockstate);
            }
            blockstate.setData(state);
            cir.setReturnValue(true);
        }

        if (this.isOutOfHeightLimit(pos)) {
            cir.setReturnValue(false);
        } else if (!this.isClient && this.isDebugWorld()) {
            cir.setReturnValue(false);
        } else {
            WorldChunk chunk = this.getWorldChunk(pos);
            Block block = state.getBlock();

            boolean captured = false;
            if (this.silk$captureBlockStates && !this.silk$capturedBlockStates.containsKey(pos)) {
                CapturedBlockState blockstate = CapturedBlockState.getBlockState((World) (Object) this, pos, flags);
                this.silk$capturedBlockStates.put(pos.toImmutable(), blockstate);
                captured = true;
            }

            BlockState iblockdata1 = ((IWorldChunkMixin) chunk).setBlockState(pos, state, (flags & 64) != 0, (flags & 1024) == 0); // CraftBukkit custom NO_PLACE flag

            if (iblockdata1 == null) {
                // CraftBukkit start - remove blockstate if failed (or the same)
                if (this.silk$captureBlockStates && captured) {
                    this.silk$capturedBlockStates.remove(pos);
                }
                // CraftBukkit end
                cir.setReturnValue(false);
            } else {
                BlockState iblockdata2 = this.getBlockState(pos);

                // CraftBukkit start
                if (!this.silk$captureBlockStates) { // Don't notify clients or update physics while capturing blockstates
                    // Modularize client and physic updates
                    // Spigot start
                    try {
                        notifyAndUpdatePhysics(pos, chunk, iblockdata1, state, iblockdata2, flags, maxUpdateDepth);
                    } catch (StackOverflowError ex) {
                        silk$lastPhysicsProblem = new BlockPos(pos);
                    }
                    // Spigot end
                }
                // CraftBukkit end

                cir.setReturnValue(true);
            }
        }
    }

    @Override
    public void notifyAndUpdatePhysics(BlockPos pos, WorldChunk chunk,
                                       BlockState oldBlockState, BlockState newBlockState, BlockState actualBlockState,
                                       int flags, int maxUpdateDepth) {
        BlockState iblockdata = newBlockState;
        BlockState iblockdata1 = oldBlockState;
        BlockState iblockdata2 = actualBlockState;
        if (iblockdata2 == iblockdata) {
            if (iblockdata1 != iblockdata2) {
                this.scheduleBlockRerenderIfNeeded(pos, iblockdata1, iblockdata2);
            }

            if ((flags & 2) != 0
                    && (!this.isClient || (flags & 4) == 0)
                    && (this.isClient || chunk == null || (chunk.getLevelType() != null && chunk.getLevelType().isAfter(ChunkLevelType.BLOCK_TICKING)))) { // allow chunk to be null here as chunk.isReady() is false when we send our notification during block placement
                this.updateListeners(pos, iblockdata1, iblockdata, flags);
            }

            if ((flags & 1) != 0) {
                this.updateNeighbors(pos, iblockdata1.getBlock());
                if (!this.isClient && iblockdata.hasComparatorOutput()) {
                    this.updateComparators(pos, newBlockState.getBlock());
                }
            }

            if ((flags & 16) == 0 && maxUpdateDepth > 0) {
                int k = flags & -34;

                iblockdata1.prepare(this, pos, k, maxUpdateDepth - 1); // Don't call an event for the old block to limit event spam
                CraftWorld world = this.getWorld();
                if (world != null) {
                    BlockPhysicsEvent event = new BlockPhysicsEvent(world.getBlockAt(pos.getX(), pos.getY(), pos.getZ()), CraftBlockData.fromData(iblockdata));
                    this.getCraftServer().getPluginManager().callEvent(event);

                    if (event.isCancelled()) {
                        return;
                    }
                }
                iblockdata.updateNeighbors(this, pos, k, maxUpdateDepth - 1);
                iblockdata.prepare(this, pos, k, maxUpdateDepth - 1);
            }

            if (!silk$preventPoiUpdated) {
                this.onBlockChanged(pos, iblockdata1, iblockdata2);
            }
        }
    }
}
