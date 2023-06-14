package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.projectile.EntityDragonFireball;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.entity.DragonFireball;
import org.bukkit.unrealized.entity.EntityType;

public class CraftDragonFireball extends CraftFireball implements DragonFireball {
    public CraftDragonFireball(CraftServer server, EntityDragonFireball entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "CraftDragonFireball";
    }

    @Override
    public EntityType getType() {
        return EntityType.DRAGON_FIREBALL;
    }
}
