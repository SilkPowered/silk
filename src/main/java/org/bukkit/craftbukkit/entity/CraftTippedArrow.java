package org.bukkit.craftbukkit.entity;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.util.List;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.bukkit.Color;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.potion.CraftPotionUtil;
import org.bukkit.entity.Arrow;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CraftTippedArrow extends CraftArrow implements Arrow {

    public CraftTippedArrow(CraftServer server, ArrowEntity entity) {
        super(server, entity);
    }

    @Override
    public ArrowEntity getHandle() {
        return (ArrowEntity) entity;
    }

    @Override
    public String toString() {
        return "CraftTippedArrow";
    }

    @Override
    public boolean addCustomEffect(PotionEffect effect, boolean override) {
        int effectId = effect.getType().getId();
        StatusEffectInstance existing = null;
        for (StatusEffectInstance mobEffect : getHandle().effects) {
            if (StatusEffect.getRawId(mobEffect.getEffectType()) == effectId) {
                existing = mobEffect;
            }
        }
        if (existing != null) {
            if (!override) {
                return false;
            }
            getHandle().effects.remove(existing);
        }
        getHandle().addEffect(CraftPotionUtil.fromBukkit(effect));
        getHandle().initColor();
        return true;
    }

    @Override
    public void clearCustomEffects() {
        getHandle().effects.clear();
        getHandle().initColor();
    }

    @Override
    public List<PotionEffect> getCustomEffects() {
        ImmutableList.Builder<PotionEffect> builder = ImmutableList.builder();
        for (StatusEffectInstance effect : getHandle().effects) {
            builder.add(CraftPotionUtil.toBukkit(effect));
        }
        return builder.build();
    }

    @Override
    public boolean hasCustomEffect(PotionEffectType type) {
        for (StatusEffectInstance effect : getHandle().effects) {
            if (CraftPotionUtil.equals(effect.getEffectType(), type)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasCustomEffects() {
        return !getHandle().effects.isEmpty();
    }

    @Override
    public boolean removeCustomEffect(PotionEffectType effect) {
        int effectId = effect.getId();
        StatusEffectInstance existing = null;
        for (StatusEffectInstance mobEffect : getHandle().effects) {
            if (StatusEffect.getRawId(mobEffect.getEffectType()) == effectId) {
                existing = mobEffect;
            }
        }
        if (existing == null) {
            return false;
        }
        getHandle().effects.remove(existing);
        getHandle().initColor();
        return true;
    }

    @Override
    public void setBasePotionData(PotionData data) {
        Preconditions.checkArgument(data != null, "PotionData cannot be null");
        this.getHandle().potion = Registries.POTION.a(new Identifier(CraftPotionUtil.fromBukkit(data)));
    }

    @Override
    public PotionData getBasePotionData() {
        return CraftPotionUtil.toBukkit(Registries.POTION.b(this.getHandle().potion).toString());
    }

    @Override
    public void setColor(Color color) {
        int colorRGB = (color == null) ? -1 : color.asRGB();
        getHandle().setColor(colorRGB);
    }

    @Override
    public Color getColor() {
        if (getHandle().getColor() <= -1) {
            return null;
        }
        return Color.fromRGB(getHandle().getColor());
    }
}
