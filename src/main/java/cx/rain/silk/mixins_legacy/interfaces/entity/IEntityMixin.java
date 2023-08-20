package cx.rain.silk.mixins_legacy.interfaces.entity;

import net.minecraft.entity.Entity;

public interface IEntityMixin {
    org.bukkit.entity.Entity getBukkitEntity();

//    void moveTo(double x, double y, double z, float yaw, float pitch);
//
//    net.minecraft.entity.Entity teleportTo(ServerWorld world, PositionImpl location);

    boolean canCollideWithBukkit(Entity entity);
}
