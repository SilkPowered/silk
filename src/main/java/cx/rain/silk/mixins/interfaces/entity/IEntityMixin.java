package cx.rain.silk.mixins.interfaces.entity;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.PositionImpl;
import org.bukkit.entity.Entity;

public interface IEntityMixin {
    Entity getBukkitEntity();

    void moveTo(double x, double y, double z, float yaw, float pitch);

    net.minecraft.entity.Entity teleportTo(ServerWorld world, PositionImpl location);
}
