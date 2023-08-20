package cx.rain.silk.mixins_legacy.interfaces.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import javax.annotation.Nullable;
import java.util.Optional;

public interface ILivingEntityMixin extends IEntityMixin {

    float getBukkitYaw();

    void inactiveTick();

    void onEquipStack(EquipmentSlot equipmentSlot, ItemStack oldStack, ItemStack newStack, boolean silent);

    boolean clearStatusEffects(EntityPotionEffectEvent.Cause cause);

    boolean addStatusEffect(StatusEffectInstance effect, EntityPotionEffectEvent.Cause cause);
    boolean addStatusEffect(StatusEffectInstance effect, @Nullable Entity entity, EntityPotionEffectEvent.Cause cause);

    StatusEffect removeStatusEffectInternal(StatusEffect effect, EntityPotionEffectEvent.Cause cause);
    StatusEffect removeStatusEffect(StatusEffect effect, EntityPotionEffectEvent.Cause cause);

    void heal(float f, EntityRegainHealthEvent.RegainReason reason);

    int getExpReward();

    SoundEvent getHurtSound0(DamageSource damagesource);
    SoundEvent getDeathSound0();
    SoundEvent getFallDamageSound0(int fallHeight);
    SoundEvent getDrinkingSound0(ItemStack itemstack);
    SoundEvent getEatingSound0(ItemStack itemstack);

    boolean damageEntity0(final DamageSource damagesource, float amount);

    void setArrowCount(int count, boolean flag);

    void equipStack(EquipmentSlot slot, ItemStack stack, boolean isSilent);

    Optional<Boolean> teleport(double x, double y, double z, boolean particleEffects,
                               PlayerTeleportEvent.TeleportCause cause);

}
