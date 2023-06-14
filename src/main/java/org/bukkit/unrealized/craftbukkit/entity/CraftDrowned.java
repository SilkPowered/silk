package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.monster.EntityDrowned;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.entity.Drowned;
import org.bukkit.unrealized.entity.EntityType;

public class CraftDrowned extends CraftZombie implements Drowned {

    public CraftDrowned(CraftServer server, EntityDrowned entity) {
        super(server, entity);
    }

    @Override
    public EntityDrowned getHandle() {
        return (EntityDrowned) entity;
    }

    @Override
    public String toString() {
        return "CraftDrowned";
    }

    @Override
    public EntityType getType() {
        return EntityType.DROWNED;
    }
}
