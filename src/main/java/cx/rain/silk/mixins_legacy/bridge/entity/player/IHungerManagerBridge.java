package cx.rain.silk.mixins_legacy.bridge.entity.player;

import net.minecraft.entity.player.PlayerEntity;

public interface IHungerManagerBridge {
    PlayerEntity silk$getPlayer();

    void silk$setPlayer(PlayerEntity player);

    int silk$getSaturatedRegenRate();
    int silk$getUnsaturatedRegenRate();
    int silk$getStarvationRate();
}
