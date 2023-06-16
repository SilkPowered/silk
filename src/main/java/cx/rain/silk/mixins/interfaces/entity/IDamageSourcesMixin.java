package cx.rain.silk.mixins.interfaces.entity;

import net.minecraft.entity.damage.DamageSource;

public interface IDamageSourcesMixin {
    DamageSource melting();
    DamageSource poison();
}
