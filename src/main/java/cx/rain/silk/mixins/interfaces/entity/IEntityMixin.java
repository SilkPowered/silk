package cx.rain.silk.mixins.interfaces.entity;

import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.PositionImpl;

public interface IEntityMixin {
    org.bukkit.entity.Entity getBukkitEntity();

//    void moveTo(double x, double y, double z, float yaw, float pitch);
//
//    net.minecraft.entity.Entity teleportTo(ServerWorld world, PositionImpl location);

    boolean canCollideWithBukkit(Entity entity);
}
