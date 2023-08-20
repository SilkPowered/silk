package cx.rain.silk.mixins_legacy.wrappers.world;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import org.bukkit.craftbukkit.event.CraftPortalEvent;

public class TeleportTargetWrapper extends TeleportTarget {
    public final ServerWorld world;
    public final CraftPortalEvent portalEventInfo;

    public TeleportTargetWrapper(Vec3d position, Vec3d velocity, float yaw, float pitch, ServerWorld world, CraftPortalEvent portalEventInfo) {
        super(position, velocity, yaw, pitch);

        this.world = world;
        this.portalEventInfo = portalEventInfo;
    }
}
