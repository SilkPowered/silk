package org.bukkit.unrealized.material;

import org.bukkit.unrealized.Material;
import org.bukkit.unrealized.block.BlockFace;
import org.bukkit.unrealized.block.data.BlockData;

/**
 * Represents a furnace or a dispenser.
 *
 * @deprecated all usage of MaterialData is deprecated and subject to removal.
 * Use {@link BlockData}.
 */
@Deprecated
public class DirectionalContainer extends MaterialData implements Directional {

    public DirectionalContainer(final Material type) {
        super(type);
    }

    /**
     * @param type the type
     * @param data the raw data value
     * @deprecated Magic value
     */
    @Deprecated
    public DirectionalContainer(final Material type, final byte data) {
        super(type, data);
    }

    @Override
    public void setFacingDirection(BlockFace face) {
        byte data;

        switch (face) {
        case NORTH:
            data = 0x2;
            break;

        case SOUTH:
            data = 0x3;
            break;

        case WEST:
            data = 0x4;
            break;

        case EAST:
        default:
            data = 0x5;
        }

        setData(data);
    }

    @Override
    public BlockFace getFacing() {
        byte data = getData();

        switch (data) {
        case 0x2:
            return BlockFace.NORTH;

        case 0x3:
            return BlockFace.SOUTH;

        case 0x4:
            return BlockFace.WEST;

        case 0x5:
        default:
            return BlockFace.EAST;
        }
    }

    @Override
    public String toString() {
        return super.toString() + " facing " + getFacing();
    }

    @Override
    public DirectionalContainer clone() {
        return (DirectionalContainer) super.clone();
    }
}
