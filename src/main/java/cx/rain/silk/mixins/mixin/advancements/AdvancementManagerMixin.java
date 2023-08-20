package cx.rain.silk.mixins.mixin.advancements;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AdvancementManager.class)
public abstract class AdvancementManagerMixin {
    // Todo: qyl27: https://hub.spigotmc.org/stash/projects/SPIGOT/repos/craftbukkit/browse/nms-patches/net/minecraft/advancements/Advancements.patch
//    @Inject(at = @At(value = "INVOKE", target = "org.slf4j.Logger.info(Ljava/lang/String;Ljava/lang/Object)V", ordinal = 0), method = "load")
//    public void silk$invokeLoggerInfo(CallbackInfo ci) {
//        return;
//    }
}
