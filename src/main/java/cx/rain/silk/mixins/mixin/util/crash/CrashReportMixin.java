package cx.rain.silk.mixins.mixin.util.crash;

import net.minecraft.util.SystemDetails;
import net.minecraft.util.crash.CrashReport;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CrashReport.class)
public abstract class CrashReportMixin {
    @Shadow @Final private SystemDetails systemDetailsSection;

    @Inject(at = @At("TAIL"), method = "<init>")
    private void silk$afterCrashReportInit(String s, Throwable throwable, CallbackInfo ci) {
        systemDetailsSection.addSection("CraftBukkit Information", new org.bukkit.craftbukkit.CraftCrashReport());
    }
}
