package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.monster.EntityEndermite;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.entity.Endermite;
import org.bukkit.unrealized.entity.EntityType;

public class CraftEndermite extends CraftMonster implements Endermite {

    public CraftEndermite(CraftServer server, EntityEndermite entity) {
        super(server, entity);
    }

    @Override
    public EntityEndermite getHandle() {
        return (EntityEndermite) super.getHandle();
    }

    @Override
    public String toString() {
        return "CraftEndermite";
    }

    @Override
    public EntityType getType() {
        return EntityType.ENDERMITE;
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
