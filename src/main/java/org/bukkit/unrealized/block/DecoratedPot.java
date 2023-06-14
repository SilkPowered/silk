package org.bukkit.unrealized.block;

import java.util.List;
import org.bukkit.unrealized.Material;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a captured state of a decorated pot.
 */
@ApiStatus.Experimental
public interface DecoratedPot extends TileState {

    /**
     * Gets the shards which will be dropped when this pot is broken.
     *
     * @return shards
     */
    @NotNull
    public List<Material> getShards();
}
