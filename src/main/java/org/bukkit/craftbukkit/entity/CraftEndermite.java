package org.bukkit.craftbukkit.entity;

import net.minecraft.entity.mob.EndermiteEntity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Endermite;

public class CraftEndermite extends CraftMonster implements Endermite {

    public CraftEndermite(CraftServer server, EndermiteEntity entity) {
        super(server, entity);
    }

    @Override
    public EndermiteEntity getHandle() {
        return (EndermiteEntity) super.getHandle();
    }

    @Override
    public String toString() {
        return "CraftEndermite";
    }

    @Override
    public boolean isPlayerSpawned() {
        return false;
    }

    @Override
    public void setPlayerSpawned(boolean playerSpawned) {
        // Nop
    }
}
