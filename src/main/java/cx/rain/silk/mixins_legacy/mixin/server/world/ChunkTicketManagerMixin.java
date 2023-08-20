package cx.rain.silk.mixins_legacy.mixin.server.world;

import cx.rain.silk.mixins_legacy.interfaces.server.world.IChunkTicketManagerMixin;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.server.world.ChunkTicket;
import net.minecraft.server.world.ChunkTicketManager;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.util.collection.SortedArraySet;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ChunkTicketManager.class)
public abstract class ChunkTicketManagerMixin implements IChunkTicketManagerMixin {
    @Shadow @Final public Long2ObjectOpenHashMap<SortedArraySet<ChunkTicket<?>>> ticketsByPosition;

    @Shadow @Final private ChunkTicketManager.TicketDistanceLevelPropagator distanceFromTicketTracker;

    @Shadow
    protected static int getLevel(SortedArraySet<ChunkTicket<?>> tickets) {
        return 0;
    }

    @Override
    public <T> void removeAllTicketsFor(ChunkTicketType<T> type, int ticketLevel, T argument) {
        ChunkTicket<T> target = new ChunkTicket<>(type, ticketLevel, argument);

        for (var iterator = this.ticketsByPosition.long2ObjectEntrySet().fastIterator(); iterator.hasNext();) {
            var entry = iterator.next();
            var tickets = entry.getValue();
            if (tickets.remove(target)) {
                // copied from removeTicket
                this.distanceFromTicketTracker.updateLevel(entry.getLongKey(), getLevel(tickets), false);

                // can't use entry after it's removed
                if (tickets.isEmpty()) {
                    iterator.remove();
                }
            }
        }
    }
}
