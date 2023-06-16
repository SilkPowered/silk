package cx.rain.silk.mixins.mixin.entity.damage;

import cx.rain.silk.mixins.interfaces.entity.IDamageSourceMixin;
import cx.rain.silk.mixins.interfaces.entity.IDamageSourcesMixin;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DamageSources.class)
public abstract class DamageSourcesMixin implements IDamageSourcesMixin {

    public DamageSource melting;
    public DamageSource poison;

    @Override
    public DamageSource melting() {
        return melting;
    }

    @Override
    public DamageSource poison() {
        return poison;
    }

    @Shadow public abstract DamageSource create(RegistryKey<DamageType> key);

    @Inject(at = @At("TAIL"), method = "<init>")
    private void silk$beforeDamageSourcesInit(DynamicRegistryManager dynamicRegistryManager, CallbackInfo ci) {
        this.melting = ((IDamageSourceMixin) this.create(DamageTypes.ON_FIRE)).melting();
        this.poison = ((IDamageSourceMixin) this.create(DamageTypes.MAGIC)).poison();
    }
}
