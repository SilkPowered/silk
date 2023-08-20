package cx.rain.silk.mixins_legacy.mixin.server;

import cx.rain.silk.mixins_legacy.bridge.server.IMinecraftServerBridge;
import cx.rain.silk.mixins_legacy.interfaces.server.IMinecraftServerMixin;
import jline.console.ConsoleReader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerTask;
import net.minecraft.util.thread.ReentrantThreadExecutor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.craftbukkit.CraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin extends ReentrantThreadExecutor<ServerTask> implements IMinecraftServerMixin, IMinecraftServerBridge {
    @Shadow public abstract boolean isStopped();

    public MinecraftServerMixin(String string) {
        super(string);
    }

    @Override
    public boolean isOnThread() {
        return super.isOnThread() || isStopped();
    }

    private boolean silk$hasStopped = false;
    private final Object silk$stopLock = new Object();

    @Override
    public boolean hasStopped() {
        synchronized (silk$stopLock) {
            return silk$hasStopped;
        }
    }

    @Override
    public boolean isDebugging() {
        return false;
    }

    private CraftServer silk$server;

    @Override
    public CraftServer silk$getCraftServer() {
        return silk$server;
    }

    @Override
    public void silk$setCraftServer(CraftServer server) {
        silk$server = server;
    }

    private ConsoleCommandSender silk$console;

    @Override
    public ConsoleCommandSender silk$getConsole() {
        return silk$console;
    }

    @Override
    public void silk$setConsole(ConsoleCommandSender sender) {
        silk$console = sender;
    }

    private ConsoleReader silk$reader;

    @Override
    public ConsoleReader silk$getReader() {
        return silk$reader;
    }

    @Override
    public void silk$setReader(ConsoleReader reader) {
        silk$reader = reader;
    }

    private Queue<Runnable> silk$processQueue = new ConcurrentLinkedQueue<Runnable>();

    @Override
    public Queue<Runnable> silk$getProcessQueue() {
        return silk$processQueue;
    }
}
