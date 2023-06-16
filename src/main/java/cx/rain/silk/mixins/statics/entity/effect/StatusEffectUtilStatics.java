package cx.rain.silk.mixins.statics.entity.effect;

import cx.rain.silk.mixins.interfaces.entity.ILivingEntityMixin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.bukkit.event.entity.EntityPotionEffectEvent;

import javax.annotation.Nullable;
import java.util.List;

public class StatusEffectUtilStatics {
    public static List<ServerPlayerEntity> addEffectToPlayersWithinDistance(ServerWorld world,
                                                                            @Nullable Entity entity,
                                                                            Vec3d origin,
                                                                            double range,
                                                                            StatusEffectInstance statusEffectInstance,
                                                                            int duration,
                                                                            EntityPotionEffectEvent.Cause cause) {
        StatusEffect statusEffect = statusEffectInstance.getEffectType();
        List<ServerPlayerEntity> list = world.getPlayers(player -> !(!player.interactionManager.isSurvivalLike()
                || entity != null && entity.isTeammate(player)
                || !origin.isInRange(player.getPos(), range)
                || player.hasStatusEffect(statusEffect)
                && player.getStatusEffect(statusEffect).getAmplifier() >= statusEffectInstance.getAmplifier()
                && !player.getStatusEffect(statusEffect).isDurationBelow(duration - 1)));
        list.forEach(player -> ((ILivingEntityMixin) player).addStatusEffect(new StatusEffectInstance(statusEffectInstance), entity, cause));
        return list;
    }
}
