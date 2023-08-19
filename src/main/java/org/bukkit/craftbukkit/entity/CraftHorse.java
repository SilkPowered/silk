package org.bukkit.craftbukkit.entity;

import com.google.common.base.Preconditions;
import net.minecraft.entity.passive.HorseColor;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.passive.HorseMarking;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.inventory.CraftInventoryHorse;
import org.bukkit.entity.Horse;
import org.bukkit.inventory.HorseInventory;

public class CraftHorse extends CraftAbstractHorse implements Horse {

    public CraftHorse(CraftServer server, HorseEntity entity) {
        super(server, entity);
    }

    @Override
    public HorseEntity getHandle() {
        return (HorseEntity) super.getHandle();
    }

    @Override
    public Variant getVariant() {
        return Variant.HORSE;
    }

    @Override
    public Color getColor() {
        return Color.values()[getHandle().getVariant().getId()];
    }

    @Override
    public void setColor(Color color) {
        Preconditions.checkArgument(color != null, "Color cannot be null");
        getHandle().setHorseVariant(HorseColor.byId(color.ordinal()), getHandle().getMarking());
    }

    @Override
    public Style getStyle() {
        return Style.values()[getHandle().getMarking().getId()];
    }

    @Override
    public void setStyle(Style style) {
        Preconditions.checkArgument(style != null, "Style cannot be null");
        getHandle().setHorseVariant(getHandle().getVariant(), HorseMarking.byIndex(style.ordinal()));
    }

    @Override
    public boolean isCarryingChest() {
        return false;
    }

    @Override
    public void setCarryingChest(boolean chest) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public HorseInventory getInventory() {
        return new CraftInventoryHorse(getHandle().inventory);
    }

    @Override
    public String toString() {
        return "CraftHorse{variant=" + getVariant() + ", owner=" + getOwner() + '}';
    }
}
