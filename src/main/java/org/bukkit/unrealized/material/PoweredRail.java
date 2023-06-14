package org.bukkit.unrealized.material;

import org.bukkit.unrealized.Material;
import org.bukkit.unrealized.block.data.BlockData;

/**
 * Represents a powered rail
 *
 * @deprecated all usage of MaterialData is deprecated and subject to removal.
 * Use {@link BlockData}.
 */
@Deprecated
public class PoweredRail extends ExtendedRails implements Redstone {
    public PoweredRail() {
        super(Material.LEGACY_POWERED_RAIL);
    }

    public PoweredRail(final Material type) {
        super(type);
    }

    /**
     * @param type the type
     * @param data the raw data value
     * @deprecated Magic value
     */
    @Deprecated
    public PoweredRail(final Material type, final byte data) {
        super(type, data);
    }

    @Override
    public boolean isPowered() {
        return (getData() & 0x8) == 0x8;
    }

    /**
     * Set whether this PoweredRail should be powered or not.
     *
     * @param isPowered whether or not the rail is powered
     */
    public void setPowered(boolean isPowered) {
        setData((byte) (isPowered ? (getData() | 0x8) : (getData() & ~0x8)));
    }

    @Override
    public PoweredRail clone() {
        return (PoweredRail) super.clone();
    }
}
