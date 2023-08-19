package org.bukkit.craftbukkit.entity;

import net.minecraft.entity.passive.BatEntity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Bat;

public class CraftBat extends CraftAmbient implements Bat {
    public CraftBat(CraftServer server, BatEntity entity) {
        super(server, entity);
    }

    @Override
    public BatEntity getHandle() {
        return (BatEntity) entity;
    }

    @Override
    public String toString() {
        return "CraftBat";
    }

    @Override
    public boolean isAwake() {
        return !getHandle().isRoosting();
    }

    @Override
    public void setAwake(boolean state) {
        getHandle().setRoosting(!state);
    }
}
