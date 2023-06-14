package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.monster.EntityZombieHusk;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.entity.EntityType;
import org.bukkit.unrealized.entity.Husk;

public class CraftHusk extends CraftZombie implements Husk {

    public CraftHusk(CraftServer server, EntityZombieHusk entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "CraftHusk";
    }

    @Override
    public EntityType getType() {
        return EntityType.HUSK;
    }
}
