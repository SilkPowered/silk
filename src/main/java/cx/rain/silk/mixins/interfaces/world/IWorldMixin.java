package cx.rain.silk.mixins.interfaces.world;

import org.bukkit.World;
import org.bukkit.craftbukkit.CraftServer;

public interface IWorldMixin {
    World getWorld();

    CraftServer getCraftServer();
}
