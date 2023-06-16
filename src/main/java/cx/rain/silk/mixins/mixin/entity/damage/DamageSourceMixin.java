package cx.rain.silk.mixins.mixin.entity.damage;

import cx.rain.silk.mixins.interfaces.entity.IDamageSourceMixin;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(DamageSource.class)
public abstract class DamageSourceMixin implements IDamageSourceMixin {
    private boolean sweep;
    private boolean melting;
    private boolean poison;

    @Override
    public boolean isSweep() {
        return sweep;
    }

    @Override
    public DamageSource sweep() {
        this.sweep = true;
        return (DamageSource) (Object) this;
    }

    @Override
    public boolean isMelting() {
        return melting;
    }

    @Override
    public DamageSource melting() {
        this.melting = true;
        return (DamageSource) (Object) this;
    }

    @Override
    public boolean isPoison() {
        return poison;
    }

    @Override
    public DamageSource poison() {
        this.poison = true;
        return (DamageSource) (Object) this;
    }
}
