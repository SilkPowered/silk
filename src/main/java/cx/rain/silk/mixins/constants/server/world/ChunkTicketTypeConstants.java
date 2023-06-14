package cx.rain.silk.mixins.constants.server.world;

import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.util.Unit;
import org.bukkit.unrealized.plugin.Plugin;

import java.util.Comparator;

public class ChunkTicketTypeConstants {
    public static final ChunkTicketType<Unit> PLUGIN = ChunkTicketType.create("plugin", (a, b) -> 0);
    public static final ChunkTicketType<Plugin> PLUGIN_TICKET = ChunkTicketType.create("plugin_ticket", Comparator.comparing(plugin -> plugin.getClass().getName()));
}