package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.projectile.EntitySnowball;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.entity.EntityType;
import org.bukkit.unrealized.entity.Snowball;

public class CraftSnowball extends CraftThrowableProjectile implements Snowball {
    public CraftSnowball(CraftServer server, EntitySnowball entity) {
        super(server, entity);
    }

    @Override
    public EntitySnowball getHandle() {
        return (EntitySnowball) entity;
    }

    @Override
    public String toString() {
        return "CraftSnowball";
    }

    @Override
    public EntityType getType() {
        return EntityType.SNOWBALL;
    }
}