package org.bukkit.unrealized.material;

import org.bukkit.unrealized.Material;
import org.bukkit.unrealized.block.data.BlockData;

/**
 * Represents a pressure plate
 *
 * @deprecated all usage of MaterialData is deprecated and subject to removal.
 * Use {@link BlockData}.
 */
@Deprecated
public class PressurePlate extends MaterialData implements PressureSensor {
    public PressurePlate() {
        super(Material.LEGACY_WOOD_PLATE);
    }

    public PressurePlate(Material type) {
        super(type);
    }

    /**
     * @param type the type
     * @param data the raw data value
     * @deprecated Magic value
     */
    @Deprecated
    public PressurePlate(Material type, byte data) {
        super(type, data);
    }

    @Override
    public boolean isPressed() {
        return getData() == 0x1;
    }

    @Override
    public String toString() {
        return super.toString() + (isPressed() ? " PRESSED" : "");
    }

    @Override
    public PressurePlate clone() {
        return (PressurePlate) super.clone();
    }
}
