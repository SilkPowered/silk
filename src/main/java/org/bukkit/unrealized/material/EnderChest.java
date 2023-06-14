package org.bukkit.unrealized.material;

import org.bukkit.unrealized.Material;
import org.bukkit.unrealized.block.BlockFace;
import org.bukkit.unrealized.block.data.BlockData;

/**
 * Represents an ender chest
 *
 * @deprecated all usage of MaterialData is deprecated and subject to removal.
 * Use {@link BlockData}.
 */
@Deprecated
public class EnderChest extends DirectionalContainer {

    public EnderChest() {
        super(Material.LEGACY_ENDER_CHEST);
    }

    /**
     * Instantiate an ender chest facing in a particular direction.
     *
     * @param direction the direction the ender chest's lid opens towards
     */
    public EnderChest(BlockFace direction) {
        this();
        setFacingDirection(direction);
    }

    public EnderChest(final Material type) {
        super(type);
    }

    /**
     * @param type the type
     * @param data the raw data value
     * @deprecated Magic value
     */
    @Deprecated
    public EnderChest(final Material type, final byte data) {
        super(type, data);
    }

    @Override
    public EnderChest clone() {
        return (EnderChest) super.clone();
    }
}
