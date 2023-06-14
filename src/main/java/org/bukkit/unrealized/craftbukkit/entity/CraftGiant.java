package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.monster.EntityGiantZombie;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.entity.EntityType;
import org.bukkit.unrealized.entity.Giant;

public class CraftGiant extends CraftMonster implements Giant {

    public CraftGiant(CraftServer server, EntityGiantZombie entity) {
        super(server, entity);
    }

    @Override
    public EntityGiantZombie getHandle() {
        return (EntityGiantZombie) entity;
    }

    @Override
    public String toString() {
        return "CraftGiant";
    }

    @Override
    public EntityType getType() {
        return EntityType.GIANT;
    }
}
