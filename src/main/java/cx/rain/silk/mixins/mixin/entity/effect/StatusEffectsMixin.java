package cx.rain.silk.mixins.mixin.entity.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import org.bukkit.craftbukkit.potion.CraftPotionEffectType;
import org.bukkit.potion.PotionEffectType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StatusEffects.class)
public abstract class StatusEffectsMixin {
    @Inject(at = @At("RETURN"), method = "register")
    private static void silk$afterRegister(int rawId, String id, StatusEffect entry,
                                           CallbackInfoReturnable<StatusEffect> cir) {
        PotionEffectType.registerPotionEffectType(new CraftPotionEffectType(cir.getReturnValue()));
    }
}
