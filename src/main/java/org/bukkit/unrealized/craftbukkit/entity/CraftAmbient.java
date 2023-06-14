package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.ambient.EntityAmbient;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.entity.Ambient;
import org.bukkit.unrealized.entity.EntityType;

public class CraftAmbient extends CraftMob implements Ambient {
    public CraftAmbient(CraftServer server, EntityAmbient entity) {
        super(server, entity);
    }

    @Override
    public EntityAmbient getHandle() {
        return (EntityAmbient) entity;
    }

    @Override
    public String toString() {
        return "CraftAmbient";
    }

    @Override
    public EntityType getType() {
        return EntityType.UNKNOWN;
    }
}
