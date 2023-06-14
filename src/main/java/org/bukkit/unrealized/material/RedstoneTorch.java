package org.bukkit.unrealized.material;

import org.bukkit.unrealized.Material;
import org.bukkit.unrealized.block.data.BlockData;

/**
 * Represents a redstone torch
 *
 * @deprecated all usage of MaterialData is deprecated and subject to removal.
 * Use {@link BlockData}.
 */
@Deprecated
public class RedstoneTorch extends Torch implements Redstone {
    public RedstoneTorch() {
        super(Material.LEGACY_REDSTONE_TORCH_ON);
    }

    public RedstoneTorch(final Material type) {
        super(type);
    }

    /**
     * @param type the type
     * @param data the raw data value
     * @deprecated Magic value
     */
    @Deprecated
    public RedstoneTorch(final Material type, final byte data) {
        super(type, data);
    }

    /**
     * Gets the current state of this Material, indicating if it's powered or
     * unpowered
     *
     * @return true if powered, otherwise false
     */
    @Override
    public boolean isPowered() {
        return getItemType() == Material.LEGACY_REDSTONE_TORCH_ON;
    }

    @Override
    public String toString() {
        return super.toString() + " " + (isPowered() ? "" : "NOT ") + "POWERED";
    }

    @Override
    public RedstoneTorch clone() {
        return (RedstoneTorch) super.clone();
    }
}
