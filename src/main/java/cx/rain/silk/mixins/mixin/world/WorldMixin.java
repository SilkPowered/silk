package cx.rain.silk.mixins.mixin.world;

import cx.rain.silk.mixins.interfaces.world.IWorldMixin;
import net.minecraft.world.World;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.Bukkit;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(World.class)
public abstract class WorldMixin implements IWorldMixin {
    private CraftWorld world;

    @Override
    public org.bukkit.World getWorld() {
        return world;
    }

    @Override
    public CraftServer getCraftServer() {
        return (CraftServer) Bukkit.getServer();
    }


}
