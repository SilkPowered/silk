package cx.rain.silk.mixins_legacy.interfaces.world.chunk;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public interface IWorldChunkMixin {
    BlockState setBlockState(BlockPos pos, BlockState state, boolean flag, boolean doPlace);
}
