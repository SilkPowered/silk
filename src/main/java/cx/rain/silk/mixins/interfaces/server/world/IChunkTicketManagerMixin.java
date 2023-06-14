package cx.rain.silk.mixins.interfaces.server.world;

import cx.rain.silk.mixins.constants.server.world.ChunkTicketTypeConstants;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.util.math.ChunkPos;

public interface IChunkTicketManagerMixin {
    // Todo: qyl27: change methods return value.
//    <T> boolean addTicket(ChunkTicketType<T> type, ChunkPos pos, int radius, T argument);
//
//    <T> boolean removeTicket(ChunkTicketType<T> type, ChunkPos pos, int radius, T argument);

    <T> void removeAllTicketsFor(ChunkTicketType<T> type, int ticketLevel, T argument);
}
