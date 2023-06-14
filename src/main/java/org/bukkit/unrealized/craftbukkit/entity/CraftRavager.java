package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.monster.EntityRavager;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.entity.EntityType;
import org.bukkit.unrealized.entity.Ravager;

public class CraftRavager extends CraftRaider implements Ravager {

    public CraftRavager(CraftServer server, EntityRavager entity) {
        super(server, entity);
    }

    @Override
    public EntityRavager getHandle() {
        return (EntityRavager) super.getHandle();
    }

    @Override
    public EntityType getType() {
        return EntityType.RAVAGER;
    }

    @Override
    public String toString() {
        return "CraftRavager";
    }
}
