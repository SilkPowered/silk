package cx.rain.silk.mixins_legacy.mixin.world;

import cx.rain.silk.mixins_legacy.interfaces.world.IWorldAccessMixin;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin implements IWorldAccessMixin {
    @Override
    public ServerWorld getLevel() {
        return (ServerWorld) (Object) this;
    }
}
