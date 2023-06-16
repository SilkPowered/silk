package cx.rain.silk.mixins.interfaces.entity.player;

import cx.rain.silk.mixins.interfaces.entity.IEntityMixin;
import org.bukkit.craftbukkit.entity.CraftPlayer;

public interface IServerPlayerEntityMixin extends IEntityMixin {

    @Override
    CraftPlayer getBukkitEntity();
}
