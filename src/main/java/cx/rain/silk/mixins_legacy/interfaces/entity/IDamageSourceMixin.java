package cx.rain.silk.mixins_legacy.interfaces.entity;

import net.minecraft.entity.damage.DamageSource;

public interface IDamageSourceMixin {
    boolean isSweep();
    DamageSource sweep();

    boolean isMelting();
    DamageSource melting();

    boolean isPoison();
    DamageSource poison();
}
