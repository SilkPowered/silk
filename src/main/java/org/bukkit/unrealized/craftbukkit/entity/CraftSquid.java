package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.animal.EntitySquid;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.entity.EntityType;
import org.bukkit.unrealized.entity.Squid;

public class CraftSquid extends CraftWaterMob implements Squid {

    public CraftSquid(CraftServer server, EntitySquid entity) {
        super(server, entity);
    }

    @Override
    public EntitySquid getHandle() {
        return (EntitySquid) entity;
    }

    @Override
    public String toString() {
        return "CraftSquid";
    }

    @Override
    public EntityType getType() {
        return EntityType.SQUID;
    }
}
