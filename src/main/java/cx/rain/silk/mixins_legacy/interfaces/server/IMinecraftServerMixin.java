package cx.rain.silk.mixins_legacy.interfaces.server;

import net.minecraft.server.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.CraftServer;

public interface IMinecraftServerMixin  {
    boolean hasStopped();

    boolean isDebugging();

    private static double calcTps(double avg, double exp, double tps)
    {
        return ( avg * exp ) + ( tps * ( 1 - exp ) );
    }

    static MinecraftServer getServer() {
        return (Bukkit.getServer() instanceof CraftServer) ? ((CraftServer) Bukkit.getServer()).getServer() : null;
    }

}
