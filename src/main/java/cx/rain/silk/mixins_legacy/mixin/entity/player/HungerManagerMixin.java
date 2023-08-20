package cx.rain.silk.mixins_legacy.mixin.entity.player;

import cx.rain.silk.mixins_legacy.bridge.entity.player.IHungerManagerBridge;
import cx.rain.silk.mixins_legacy.bridge.entity.player.IPlayerEntityBridge;
import cx.rain.silk.mixins_legacy.interfaces.entity.ILivingEntityMixin;
import cx.rain.silk.mixins_legacy.interfaces.entity.player.IPlayerEntityMixin;
import cx.rain.silk.mixins_legacy.interfaces.entity.player.IServerPlayerEntityMixin;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.HealthUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.commons.lang3.Validate;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.event.entity.EntityExhaustionEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HungerManager.class)
public abstract class HungerManagerMixin implements IHungerManagerBridge {
    @Shadow private int foodLevel;
    @Shadow private float saturationLevel;

    @Shadow public abstract void add(int food, float saturationModifier);

    private PlayerEntity silk$player;
    public int saturatedRegenRate = 10;
    public int unsaturatedRegenRate = 80;
    public int starvationRate = 80;

    @Override
    public PlayerEntity silk$getPlayer() {
        return silk$player;
    }

    @Override
    public void silk$setPlayer(PlayerEntity player) {
        Validate.notNull(player);
        silk$player = player;
    }

    @Override
    public int silk$getSaturatedRegenRate() {
        return saturatedRegenRate;
    }

    @Override
    public int silk$getUnsaturatedRegenRate() {
        return unsaturatedRegenRate;
    }

    @Override
    public int silk$getStarvationRate() {
        return starvationRate;
    }

    @Inject(at = @At(value = "INVOKE", target = "net.minecraft.entity.player.HungerManager.add(IF)V"), method = "eat")
    private void silk$invokeEatAdd(Item item, ItemStack stack, CallbackInfo ci) {
        var food = item.getFoodComponent();
        var oldFoodLevel = foodLevel;
        var event = CraftEventFactory.callFoodLevelChangeEvent(silk$player, food.getHunger() + oldFoodLevel, stack);

        if (!event.isCancelled()) {
            this.add(food.getHunger(), food.getSaturationModifier());
        }

        ((IServerPlayerEntityMixin) silk$player).getBukkitEntity().sendHealthUpdate();
    }

    @Inject(at = @At(value = "INVOKE", target = "java.lang.Math.max(II)I", ordinal = 0), method = "update")
    private void silk$invokeUpdateMax(PlayerEntity player, CallbackInfo ci) {
        var event = CraftEventFactory.callFoodLevelChangeEvent(player, Math.max(foodLevel - 1, 0));

        if (event.isCancelled()) {
            return;
        }

        ((ServerPlayerEntity) player).networkHandler.sendPacket(new HealthUpdateS2CPacket(((IServerPlayerEntityMixin) player).getBukkitEntity().getScaledHealth(), foodLevel, saturationLevel));
    }

    @Inject(at = @At(value = "INVOKE", target = "net.minecraft.entity.player.PlayerEntity.heal(F)V",
            ordinal = 0), method = "update")
    private void silk$invokeFirstUpdateHeal(PlayerEntity player, CallbackInfo ci) {
        var f = Math.min(this.saturationLevel, 6.0F);
        ((ILivingEntityMixin) player).heal(f / 6.0F, org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason.SATIATED);
    }

    @Inject(at = @At(value = "INVOKE", target = "net.minecraft.entity.player.HungerManager.addExhaustion(F)V",
            ordinal = 0), method = "update")
    private void silk$invokeFirstUpdateAddExhaustion(PlayerEntity player, CallbackInfo ci) {
        var f = Math.min(this.saturationLevel, 6.0F);
        ((IPlayerEntityMixin) player).addExhaustion(f, EntityExhaustionEvent.ExhaustionReason.REGEN);
    }

    @Inject(at = @At(value = "INVOKE", target = "net.minecraft.entity.player.PlayerEntity.heal(F)V",
            ordinal = 1), method = "update")
    private void silk$invokeSecondUpdateHeal(PlayerEntity player, CallbackInfo ci) {
        ((ILivingEntityMixin) player).heal(1, org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason.SATIATED);
    }

    @Inject(at = @At(value = "INVOKE", target = "net.minecraft.entity.player.HungerManager.addExhaustion(F)V",
            ordinal = 1), method = "update")
    private void silk$invokeSecondUpdateAddExhaustion(PlayerEntity player, CallbackInfo ci) {
        ((IPlayerEntityMixin) player).addExhaustion(((IPlayerEntityBridge) player.getWorld()).silk$getSpigotConfig().regenExhaustion, EntityExhaustionEvent.ExhaustionReason.REGEN);
    }

    // Todo: qyl27: add regen rate manipulation.
}
