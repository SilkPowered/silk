package org.bukkit.craftbukkit.entity;

import net.minecraft.entity.mob.GiantEntity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Giant;

public class CraftGiant extends CraftMonster implements Giant {

    public CraftGiant(CraftServer server, GiantEntity entity) {
        super(server, entity);
    }

    @Override
    public GiantEntity getHandle() {
        return (GiantEntity) entity;
    }

    @Override
    public String toString() {
        return "CraftGiant";
    }
}
