package cx.rain.silk.mixins.bridge.entity.player;

import net.minecraft.entity.player.PlayerEntity;

public interface IHungerManagerBridge {
    PlayerEntity silk$getPlayer();

    void silk$setPlayer(PlayerEntity player);

    int silk$getSaturatedRegenRate();
    int silk$getUnsaturatedRegenRate();
    int silk$getStarvationRate();
}
