package cx.rain.silk.mixins.mixin.entity.effect;

import cx.rain.silk.mixins.interfaces.entity.IDamageSourcesMixin;
import cx.rain.silk.mixins.interfaces.entity.ILivingEntityMixin;
import cx.rain.silk.mixins.interfaces.entity.player.IServerPlayerEntityMixin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.HealthUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StatusEffect.class)
public class StatusEffectMixin {
    @Inject(at = @At(value = "INVOKE", target = "net.minecraft.entity.LivingEntity.heal(F)V", ordinal = 0),
            method = "applyUpdateEffect")
    private void silk$beforeFirstApplyUpdateEffectHeal(LivingEntity entity, int amplifier, CallbackInfo ci) {
        ((ILivingEntityMixin) entity).heal(1, EntityRegainHealthEvent.RegainReason.MAGIC_REGEN);
    }

    @Inject(at = @At(value = "INVOKE", target = "net.minecraft.entity.LivingEntity.heal(F)V", ordinal = 1),
            method = "applyUpdateEffect")
    private void silk$beforeSecondApplyUpdateEffectHeal(LivingEntity entity, int amplifier, CallbackInfo ci) {
        ((ILivingEntityMixin) entity).heal(Math.max(4 << amplifier, 0), EntityRegainHealthEvent.RegainReason.MAGIC);
    }

    @Inject(at = @At(value = "INVOKE",
            target = "net.minecraft.entity.LivingEntity.damage(Lnet/minecraft/entity/damage/DamageSource;F)Z",
            ordinal = 0),
            method = "applyUpdateEffect")
    private void silk$beforeFirstApplyUpdateEffectDamage(LivingEntity entity, int amplifier, CallbackInfo ci) {
        entity.damage(((IDamageSourcesMixin) entity.getDamageSources()).poison(), 1);
    }

    @Inject(at = @At(value = "INVOKE",
            target = "net.minecraft.entity.player.PlayerEntity.getHungerManager()Lnet/minecraft/entity/player/HungerManager;"),
            method = "applyUpdateEffect")
    private void silk$beforeApplyUpdateEffectGetHungerManager(LivingEntity entity, int amplifier, CallbackInfo ci) {
        var player = (PlayerEntity) entity;

        int oldFoodLevel = player.getHungerManager().getFoodLevel();

        org.bukkit.event.entity.FoodLevelChangeEvent event = CraftEventFactory.callFoodLevelChangeEvent(player, amplifier + 1 + oldFoodLevel);

        if (!event.isCancelled()) {
            player.getHungerManager().add(event.getFoodLevel() - oldFoodLevel, 1);
        }

        ((ServerPlayerEntity) player).networkHandler.sendPacket(new HealthUpdateS2CPacket(((IServerPlayerEntityMixin) player).getBukkitEntity().getScaledHealth(), player.getHungerManager().getFoodLevel(), player.getHungerManager().getSaturationLevel()));
    }

    @Inject(at = @At(value = "INVOKE", target = "net.minecraft.entity.LivingEntity.heal(F)V", ordinal = 0),
            method = "applyInstantEffect")
    private void silk$beforeFirstLivingEntityHeal(Entity source, Entity attacker, LivingEntity target,
                                                  int amplifier, double proximity, CallbackInfo ci) {
        int j = (int) (proximity * (double) (4 << amplifier) + 0.5D);
        ((ILivingEntityMixin) target).heal((float) j, EntityRegainHealthEvent.RegainReason.MAGIC);
    }
}
