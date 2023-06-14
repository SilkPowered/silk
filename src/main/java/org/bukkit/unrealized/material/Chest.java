package org.bukkit.unrealized.material;

import org.bukkit.unrealized.Material;
import org.bukkit.unrealized.block.BlockFace;
import org.bukkit.unrealized.block.data.BlockData;

/**
 * Represents a chest
 *
 * @deprecated all usage of MaterialData is deprecated and subject to removal.
 * Use {@link BlockData}.
 */
@Deprecated
public class Chest extends DirectionalContainer {

    public Chest() {
        super(Material.LEGACY_CHEST);
    }

    /**
     * Instantiate a chest facing in a particular direction.
     *
     * @param direction the direction the chest's lit opens towards
     */
    public Chest(BlockFace direction) {
        this();
        setFacingDirection(direction);
    }

    public Chest(final Material type) {
        super(type);
    }

    /**
     * @param type the type
     * @param data the raw data value
     * @deprecated Magic value
     */
    @Deprecated
    public Chest(final Material type, final byte data) {
        super(type, data);
    }

    @Override
    public Chest clone() {
        return (Chest) super.clone();
    }
}
