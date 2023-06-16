package org.spigotmc;

import cx.rain.silk.mixins.bridge.server.IMinecraftServerBridge;
import cx.rain.silk.mixins.interfaces.server.IMinecraftServerMixin;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.world.ServerWorld;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.io.File;

public class SpigotCommand extends Command {

    public SpigotCommand(String name) {
        super(name);
        this.description = "Spigot related commands";
        this.usageMessage = "/spigot reload";
        this.setPermission("bukkit.command.spigot");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!testPermission(sender)) return true;

        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: " + usageMessage);
            return false;
        }

        if (args[0].equals("reload")) {
            Command.broadcastCommandMessage(sender, ChatColor.RED + "Please note that this command is not supported and may cause issues.");
            Command.broadcastCommandMessage(sender, ChatColor.RED + "If you encounter any issues please use the /stop command to restart your server.");

            MinecraftServer console = IMinecraftServerMixin.getServer();
            SpigotConfig.init(new File(console.getRunDirectory(), "spigot.yml"));
            for (ServerWorld world : console.getWorlds()) {
                world.spigotConfig.init();
            }
            ((IMinecraftServerBridge) console).silk$getCraftServer().reloadCount++;

            Command.broadcastCommandMessage(sender, ChatColor.GREEN + "Reload complete.");
        }

        return true;
    }
}
