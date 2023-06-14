package org.bukkit.unrealized.material;

import org.bukkit.unrealized.DyeColor;
import org.bukkit.unrealized.Material;
import org.bukkit.unrealized.block.data.BlockData;

/**
 * Represents dye
 *
 * @deprecated all usage of MaterialData is deprecated and subject to removal.
 * Use {@link BlockData}.
 */
@Deprecated
public class Dye extends MaterialData implements Colorable {
    public Dye() {
        super(Material.LEGACY_INK_SACK);
    }

    public Dye(final Material type) {
        super(type);
    }

    /**
     * @param type the type
     * @param data the raw data value
     * @deprecated Magic value
     */
    @Deprecated
    public Dye(final Material type, final byte data) {
        super(type, data);
    }

    /**
     * @param color color of the dye
     */
    public Dye(final DyeColor color) {
        super(Material.LEGACY_INK_SACK, color.getDyeData());
    }

    /**
     * Gets the current color of this dye
     *
     * @return DyeColor of this dye
     */
    @Override
    public DyeColor getColor() {
        return DyeColor.getByDyeData(getData());
    }

    /**
     * Sets the color of this dye
     *
     * @param color New color of this dye
     */
    @Override
    public void setColor(DyeColor color) {
        setData(color.getDyeData());
    }

    @Override
    public String toString() {
        return getColor() + " DYE(" + getData() + ")";
    }

    @Override
    public Dye clone() {
        return (Dye) super.clone();
    }
}
