package org.bukkit.craftbukkit.entity;

import net.minecraft.entity.mob.VindicatorEntity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Vindicator;

public class CraftVindicator extends CraftIllager implements Vindicator {

    public CraftVindicator(CraftServer server, VindicatorEntity entity) {
        super(server, entity);
    }

    @Override
    public VindicatorEntity getHandle() {
        return (VindicatorEntity) super.getHandle();
    }

    @Override
    public String toString() {
        return "CraftVindicator";
    }

    @Override
    public boolean isJohnny() {
        return getHandle().isJohnny;
    }

    @Override
    public void setJohnny(boolean johnny) {
        getHandle().isJohnny = johnny;
    }
}
