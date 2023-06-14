package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.monster.EntitySilverfish;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.entity.EntityType;
import org.bukkit.unrealized.entity.Silverfish;

public class CraftSilverfish extends CraftMonster implements Silverfish {
    public CraftSilverfish(CraftServer server, EntitySilverfish entity) {
        super(server, entity);
    }

    @Override
    public EntitySilverfish getHandle() {
        return (EntitySilverfish) entity;
    }

    @Override
    public String toString() {
        return "CraftSilverfish";
    }

    @Override
    public EntityType getType() {
        return EntityType.SILVERFISH;
    }
}
