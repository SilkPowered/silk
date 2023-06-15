package org.bukkit.craftbukkit.block;

import cx.rain.silk.mixins.interfaces.world.IWorldAccessMixin;
import cx.rain.silk.mixins.interfaces.world.IWorldMixin;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import org.bukkit.Material;
import org.bukkit.block.Block;

public final class CapturedBlockState extends CraftBlockState {

    private final boolean treeBlock;

    public CapturedBlockState(Block block, int flag, boolean treeBlock) {
        super(block, flag);

        this.treeBlock = treeBlock;
    }

    @Override
    public boolean update(boolean force, boolean applyPhysics) {
        boolean result = super.update(force, applyPhysics);

        // SPIGOT-5537: Horrible hack to manually add bees given World.captureTreeGeneration does not support tiles
        if (this.treeBlock && getType() == Material.BEE_NEST) {
            StructureWorldAccess generatoraccessseed = this.world.getHandle();
            BlockPos blockposition1 = this.getPosition();
            Random random = generatoraccessseed.getRandom();

            // Begin copied block from WorldGenFeatureTreeBeehive
            BlockEntity tileentity = generatoraccessseed.getBlockEntity(blockposition1);

            if (tileentity instanceof BeehiveBlockEntity tileentitybeehive) {
                int j = 2 + random.nextInt(2);

                for (int k = 0; k < j; ++k) {
                    BeeEntity entitybee = new BeeEntity(EntityType.BEE, ((IWorldAccessMixin) generatoraccessseed).getMinecraftWorld());

                    tileentitybeehive.tryEnterHive(entitybee, false, random.nextInt(599));
                }
            }
            // End copied block
        }

        return result;
    }

    public static CapturedBlockState getBlockState(World world, BlockPos pos, int flag) {
        return new CapturedBlockState(((IWorldMixin) world).getWorld().getBlockAt(pos.getX(), pos.getY(), pos.getZ()), flag, false);
    }

    public static CapturedBlockState getTreeBlockState(World world, BlockPos pos, int flag) {
        return new CapturedBlockState(((IWorldMixin) world).getWorld().getBlockAt(pos.getX(), pos.getY(), pos.getZ()), flag, true);
    }
}