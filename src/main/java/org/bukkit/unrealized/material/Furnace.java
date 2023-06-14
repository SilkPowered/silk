package org.bukkit.unrealized.material;

import org.bukkit.unrealized.Material;
import org.bukkit.unrealized.block.BlockFace;
import org.bukkit.unrealized.block.data.BlockData;

/**
 * Represents a furnace.
 *
 * @deprecated all usage of MaterialData is deprecated and subject to removal.
 * Use {@link BlockData}.
 */
@Deprecated
public class Furnace extends FurnaceAndDispenser {

    public Furnace() {
        super(Material.LEGACY_FURNACE);
    }

    /**
     * Instantiate a furnace facing in a particular direction.
     *
     * @param direction the direction the furnace's "opening" is facing
     */
    public Furnace(BlockFace direction) {
        this();
        setFacingDirection(direction);
    }

    public Furnace(final Material type) {
        super(type);
    }

    /**
     * @param type the type
     * @param data the raw data value
     * @deprecated Magic value
     */
    @Deprecated
    public Furnace(final Material type, final byte data) {
        super(type, data);
    }

    @Override
    public Furnace clone() {
        return (Furnace) super.clone();
    }
}
