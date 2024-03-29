package org.bukkit.craftbukkit.util;

import com.google.common.base.Preconditions;
import java.util.HashSet;
import java.util.List;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import org.bukkit.entity.Player;

public class LazyPlayerSet extends LazyHashSet<Player> {

    private final MinecraftServer server;

    public LazyPlayerSet(MinecraftServer server) {
        this.server = server;
    }

    @Override
    HashSet<Player> makeReference() {
        Preconditions.checkState(reference == null, "Reference already created!");
        List<ServerPlayerEntity> players = server.getPlayerManager().players;
        HashSet<Player> reference = new HashSet<Player>(players.size());
        for (ServerPlayerEntity player : players) {
            reference.add(player.getBukkitEntity());
        }
        return reference;
    }
}
