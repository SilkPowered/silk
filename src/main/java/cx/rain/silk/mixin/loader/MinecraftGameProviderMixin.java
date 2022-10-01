package cx.rain.silk.mixin.loader;

import cx.rain.silk.Silk;
import net.fabricmc.loader.impl.game.minecraft.MinecraftGameProvider;
import net.fabricmc.loader.impl.launch.FabricLauncher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftGameProvider.class)
public abstract class MinecraftGameProviderMixin {
    @Inject(at = @At("HEAD"), method = "locateGame", remap = false)
    private void silk$beforeLocateGame(FabricLauncher launcher, String[] args, CallbackInfoReturnable<Boolean> cir) {
        Silk.get().getLoaderLogger().info("Hello, world!");
    }
}
