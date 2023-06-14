package org.bukkit.unrealized.event.world;

import org.bukkit.unrealized.Chunk;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Chunk related event
 */
public abstract class ChunkEvent extends WorldEvent {
    protected Chunk chunk;

    protected ChunkEvent(@NotNull final Chunk chunk) {
        super(chunk.getWorld());
        this.chunk = chunk;
    }

    /**
     * Gets the chunk being loaded/unloaded
     *
     * @return Chunk that triggered this event
     */
    @NotNull
    public Chunk getChunk() {
        return chunk;
    }
}
