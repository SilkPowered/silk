package cx.rain.silk.loader.bukkit;

import net.fabricmc.loader.impl.game.minecraft.McVersion;

// Todo: qyl27: Bukkit version record.
public record BukkitVersion(McVersion mcVersion, String commit, int build) {
}
