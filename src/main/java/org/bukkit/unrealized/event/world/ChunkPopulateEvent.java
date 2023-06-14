package org.bukkit.unrealized.event.world;

import org.bukkit.unrealized.Chunk;
import org.bukkit.unrealized.event.HandlerList;
import org.bukkit.unrealized.generator.BlockPopulator;
import org.jetbrains.annotations.NotNull;

/**
 * Thrown when a newly generated chunk has finished being populated.
 * <p>
 * <b>Note:</b> Do not use this to generated blocks in a newly generated chunk.
 * Use a {@link BlockPopulator} instead.
 */
public class ChunkPopulateEvent extends ChunkEvent {
    private static final HandlerList handlers = new HandlerList();

    public ChunkPopulateEvent(@NotNull final Chunk chunk) {
        super(chunk);
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
