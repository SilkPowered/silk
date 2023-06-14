package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.projectile.EntitySmallFireball;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.entity.EntityType;
import org.bukkit.unrealized.entity.SmallFireball;

public class CraftSmallFireball extends CraftSizedFireball implements SmallFireball {
    public CraftSmallFireball(CraftServer server, EntitySmallFireball entity) {
        super(server, entity);
    }

    @Override
    public EntitySmallFireball getHandle() {
        return (EntitySmallFireball) entity;
    }

    @Override
    public String toString() {
        return "CraftSmallFireball";
    }

    @Override
    public EntityType getType() {
        return EntityType.SMALL_FIREBALL;
    }
}
