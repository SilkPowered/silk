package cx.rain.silk.mixins_legacy.interfaces.entity.player;

import cx.rain.silk.mixins_legacy.interfaces.entity.IEntityMixin;
import org.bukkit.craftbukkit.entity.CraftPlayer;

public interface IServerPlayerEntityMixin extends IEntityMixin {

    @Override
    CraftPlayer getBukkitEntity();
}
