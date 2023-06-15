package cx.rain.silk.mixins.statics.server.world;

import cx.rain.silk.mixins.interfaces.entity.IEntityMixin;
import cx.rain.silk.mixins.interfaces.world.IWorldMixin;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.bukkit.block.BlockState;

import java.util.List;

public class ServerWorldStatics {
    public static void makeObsidianPlatform(ServerWorld worldserver, Entity entity) {
        // CraftBukkit end
        BlockPos blockposition = ServerWorld.END_SPAWN_POS;
        int i = blockposition.getX();
        int j = blockposition.getY() - 2;
        int k = blockposition.getZ();

        // CraftBukkit start
        org.bukkit.craftbukkit.util.BlockStateListPopulator blockList = new org.bukkit.craftbukkit.util.BlockStateListPopulator(worldserver);
        BlockPos.iterate(i - 2, j + 1, k - 2, i + 2, j + 3, k + 2).forEach((blockposition1) -> {
            blockList.setBlockState(blockposition1, Blocks.AIR.getDefaultState(), 3);
        });
        BlockPos.iterate(i - 2, j, k - 2, i + 2, j, k + 2).forEach((blockposition1) -> {
            blockList.setBlockState(blockposition1, Blocks.OBSIDIAN.getDefaultState(), 3);
        });
        org.bukkit.World bworld = ((IWorldMixin) worldserver).getWorld();
        org.bukkit.event.world.PortalCreateEvent portalEvent = new org.bukkit.event.world.PortalCreateEvent((List<BlockState>) (List) blockList.getList(), bworld, (entity == null) ? null : ((IEntityMixin) entity).getBukkitEntity(), org.bukkit.event.world.PortalCreateEvent.CreateReason.END_PLATFORM);

        ((IWorldMixin) worldserver).getCraftServer().getPluginManager().callEvent(portalEvent);
        if (!portalEvent.isCancelled()) {
            blockList.updateList();
        }
        // CraftBukkit end
    }
}
