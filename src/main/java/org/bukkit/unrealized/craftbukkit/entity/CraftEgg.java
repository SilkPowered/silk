package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.projectile.EntityEgg;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.entity.Egg;
import org.bukkit.unrealized.entity.EntityType;

public class CraftEgg extends CraftThrowableProjectile implements Egg {
    public CraftEgg(CraftServer server, EntityEgg entity) {
        super(server, entity);
    }

    @Override
    public EntityEgg getHandle() {
        return (EntityEgg) entity;
    }

    @Override
    public String toString() {
        return "CraftEgg";
    }

    @Override
    public EntityType getType() {
        return EntityType.EGG;
    }
}
