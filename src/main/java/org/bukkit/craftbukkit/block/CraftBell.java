package org.bukkit.craftbukkit.block;

import com.google.common.base.Preconditions;
import net.minecraft.block.BellBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BellBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.Direction;
import org.bukkit.World;
import org.bukkit.block.Bell;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Entity;

public class CraftBell extends CraftBlockEntityState<BellBlockEntity> implements Bell {

    public CraftBell(World world, BellBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    @Override
    public boolean ring(Entity entity, BlockFace direction) {
        Preconditions.checkArgument(direction == null || direction.isCartesian(), "direction must be cartesian, given %s", direction);

        BlockEntity tileEntity = getTileEntityFromWorld();
        if (tileEntity == null) {
            return false;
        }

        net.minecraft.entity.Entity nmsEntity = (entity != null) ? ((CraftEntity) entity).getHandle() : null;
        Direction enumDirection = CraftBlock.blockFaceToNotch(direction);

        return ((BellBlock) Blocks.BELL).ring(nmsEntity, world.getHandle(), getPosition(), enumDirection);
    }

    @Override
    public boolean ring(Entity entity) {
        return ring(entity, null);
    }

    @Override
    public boolean ring(BlockFace direction) {
        return ring(null, direction);
    }

    @Override
    public boolean ring() {
        return ring(null, null);
    }

    @Override
    public boolean isShaking() {
        return getSnapshot().shaking;
    }

    @Override
    public int getShakingTicks() {
        return getSnapshot().ticks;
    }

    @Override
    public boolean isResonating() {
        return getSnapshot().resonating;
    }

    @Override
    public int getResonatingTicks() {
        return isResonating() ? getSnapshot().ticks : 0;
    }
}
