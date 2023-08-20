package cx.rain.silk.mixins_legacy.interfaces.world;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
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
