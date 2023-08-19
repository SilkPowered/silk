package org.bukkit.craftbukkit.entity;

import net.minecraft.entity.mob.GhastEntity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Ghast;

public class CraftGhast extends CraftFlying implements Ghast, CraftEnemy {

    public CraftGhast(CraftServer server, GhastEntity entity) {
        super(server, entity);
    }

    @Override
    public GhastEntity getHandle() {
        return (GhastEntity) entity;
    }

    @Override
    public String toString() {
        return "CraftGhast";
    }

    @Override
    public boolean isCharging() {
        return getHandle().isShooting();
    }

    @Override
    public void setCharging(boolean flag) {
        getHandle().setShooting(flag);
    }
}
