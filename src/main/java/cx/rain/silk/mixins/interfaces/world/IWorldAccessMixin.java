package cx.rain.silk.mixins.interfaces.world;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.WorldAccess;

public interface IWorldAccessMixin extends WorldAccess {
    ServerWorld getLevel();

    default ServerWorld getMinecraftWorld() {
        return getLevel();
    }

    default BlockHitResult raycast(RaycastContext context, BlockPos pos) {
        BlockState iblockdata = this.getBlockState(pos);
        FluidState fluid = this.getFluidState(pos);
        Vec3d vec3d = context.getStart();
        Vec3d vec3d1 = context.getEnd();
        VoxelShape voxelshape = context.getBlockShape(iblockdata, this, pos);
        BlockHitResult movingobjectpositionblock = this.raycastBlock(vec3d, vec3d1, pos, voxelshape, iblockdata);
        VoxelShape voxelshape1 = context.getFluidShape(fluid, this, pos);
        BlockHitResult movingobjectpositionblock1 = voxelshape1.raycast(vec3d, vec3d1, pos);
        double d0 = movingobjectpositionblock == null ? Double.MAX_VALUE : context.getStart().distanceTo(movingobjectpositionblock.getPos());
        double d1 = movingobjectpositionblock1 == null ? Double.MAX_VALUE : context.getStart().distanceTo(movingobjectpositionblock1.getPos());

        return d0 <= d1 ? movingobjectpositionblock : movingobjectpositionblock1;
    }
}
