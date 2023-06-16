package cx.rain.silk.mixins.bridge.server;

import jline.console.ConsoleReader;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.craftbukkit.CraftServer;

import java.util.Queue;

@Deprecated
public interface IMinecraftServerBridge {
    CraftServer silk$getCraftServer();
    void silk$setCraftServer(CraftServer server);

    ConsoleCommandSender silk$getConsole();
    void silk$setConsole(ConsoleCommandSender sender);

    ConsoleReader silk$getReader();
    void silk$setReader(ConsoleReader sender);

    Queue<Runnable> silk$getProcessQueue();
}
