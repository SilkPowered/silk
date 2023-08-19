package org.bukkit.craftbukkit.entity;

import net.minecraft.entity.mob.RavagerEntity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Ravager;

public class CraftRavager extends CraftRaider implements Ravager {

    public CraftRavager(CraftServer server, RavagerEntity entity) {
        super(server, entity);
    }

    @Override
    public RavagerEntity getHandle() {
        return (RavagerEntity) super.getHandle();
    }

    @Override
    public String toString() {
        return "CraftRavager";
    }
}
