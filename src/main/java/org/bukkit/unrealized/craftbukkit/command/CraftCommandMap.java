package org.bukkit.unrealized.craftbukkit.command;

import java.util.Map;
import org.bukkit.unrealized.Server;
import org.bukkit.unrealized.command.Command;
import org.bukkit.unrealized.command.SimpleCommandMap;

public class CraftCommandMap extends SimpleCommandMap {

    public CraftCommandMap(Server server) {
        super(server);
    }

    public Map<String, Command> getKnownCommands() {
        return knownCommands;
    }
}
