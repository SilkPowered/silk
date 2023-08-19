package org.bukkit.craftbukkit.entity;

import com.google.common.base.Preconditions;
import java.util.UUID;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AbstractHorseEntity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.inventory.CraftInventoryAbstractHorse;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Horse;
import org.bukkit.inventory.AbstractHorseInventory;

public abstract class CraftAbstractHorse extends CraftAnimals implements AbstractHorse {

    public CraftAbstractHorse(CraftServer server, AbstractHorseEntity entity) {
        super(server, entity);
    }

    @Override
    public AbstractHorseEntity getHandle() {
        return (AbstractHorseEntity) entity;
    }

    @Override
    public void setVariant(Horse.Variant variant) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public int getDomestication() {
        return getHandle().getTemper();
    }

    @Override
    public void setDomestication(int value) {
        Preconditions.checkArgument(value >= 0 && value <= this.getMaxDomestication(), "Domestication level (%s) need to be between %s and %s (max domestication)", value, 0, this.getMaxDomestication());
        getHandle().setTemper(value);
    }

    @Override
    public int getMaxDomestication() {
        return getHandle().getMaxTemper();
    }

    @Override
    public void setMaxDomestication(int value) {
        Preconditions.checkArgument(value > 0, "Max domestication (%s) cannot be zero or less", value);
        getHandle().maxDomestication = value;
    }

    @Override
    public double getJumpStrength() {
        return getHandle().getJumpStrength();
    }

    @Override
    public void setJumpStrength(double strength) {
        Preconditions.checkArgument(strength >= 0, "Jump strength (%s) cannot be less than zero", strength);
        getHandle().getAttributeInstance(EntityAttributes.JUMP_STRENGTH).setBaseValue(strength);
    }

    @Override
    public boolean isTamed() {
        return getHandle().isTame();
    }

    @Override
    public void setTamed(boolean tamed) {
        getHandle().setTame(tamed);
    }

    @Override
    public AnimalTamer getOwner() {
        if (getOwnerUUID() == null) return null;
        return getServer().getOfflinePlayer(getOwnerUUID());
    }

    @Override
    public void setOwner(AnimalTamer owner) {
        if (owner != null) {
            setTamed(true);
            getHandle().setTarget(null, null, false);
            setOwnerUUID(owner.getUniqueId());
        } else {
            setTamed(false);
            setOwnerUUID(null);
        }
    }

    public UUID getOwnerUUID() {
        return getHandle().U_();
    }

    public void setOwnerUUID(UUID uuid) {
        getHandle().setOwnerUuid(uuid);
    }

    @Override
    public boolean isEatingHaystack() {
        return getHandle().isEatingGrass();
    }

    @Override
    public void setEatingHaystack(boolean eatingHaystack) {
        getHandle().setEatingGrass(eatingHaystack);
    }

    @Override
    public AbstractHorseInventory getInventory() {
        return new CraftInventoryAbstractHorse(getHandle().inventory);
    }
}
