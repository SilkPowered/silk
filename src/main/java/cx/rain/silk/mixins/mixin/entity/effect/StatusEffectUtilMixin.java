package cx.rain.silk.mixins.mixin.entity.effect;

import cx.rain.silk.mixins.statics.entity.effect.StatusEffectUtilStatics;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(StatusEffectUtil.class)
public abstract class StatusEffectUtilMixin {
    @Inject(at = @At("HEAD"), method = "addEffectToPlayersWithinDistance", cancellable = true)
    private static void silk$beforeAddEffectToPlayersWithinDistance(ServerWorld world, @Nullable Entity entity,
                                                                    Vec3d origin, double range,
                                                                    StatusEffectInstance statusEffectInstance,
                                                                    int duration,
                                                                    CallbackInfoReturnable<List<ServerPlayerEntity>> cir) {
        cir.setReturnValue(StatusEffectUtilStatics.addEffectToPlayersWithinDistance(world, entity, origin, range,
                statusEffectInstance, duration, EntityPotionEffectEvent.Cause.UNKNOWN));
    }
}
