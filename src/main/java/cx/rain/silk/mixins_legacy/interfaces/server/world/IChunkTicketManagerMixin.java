package cx.rain.silk.mixins_legacy.interfaces.server.world;

import net.minecraft.server.world.ChunkTicketType;

public interface IChunkTicketManagerMixin {
    // Todo: qyl27: change methods return value.
//    <T> boolean addTicket(ChunkTicketType<T> type, ChunkPos pos, int radius, T argument);
//
//    <T> boolean removeTicket(ChunkTicketType<T> type, ChunkPos pos, int radius, T argument);

    <T> void removeAllTicketsFor(ChunkTicketType<T> type, int ticketLevel, T argument);
}
