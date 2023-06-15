package cx.rain.silk.mixins.interfaces.world;

import net.minecraft.block.BlockState;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.WorldChunk;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftServer;

public interface IWorldMixin {
    World getWorld();

    CraftServer getCraftServer();

    void notifyAndUpdatePhysics(BlockPos pos, WorldChunk chunk,
                                BlockState oldBlockState, BlockState newBlockState, BlockState actualBlockState,
                                int flags, int maxUpdateDepth);
}
