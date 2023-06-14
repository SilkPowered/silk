package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.projectile.EntityWitherSkull;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.entity.EntityType;
import org.bukkit.unrealized.entity.WitherSkull;

public class CraftWitherSkull extends CraftFireball implements WitherSkull {
    public CraftWitherSkull(CraftServer server, EntityWitherSkull entity) {
        super(server, entity);
    }

    @Override
    public void setCharged(boolean charged) {
        getHandle().setDangerous(charged);
    }

    @Override
    public boolean isCharged() {
        return getHandle().isDangerous();
    }

    @Override
    public EntityWitherSkull getHandle() {
        return (EntityWitherSkull) entity;
    }

    @Override
    public String toString() {
        return "CraftWitherSkull";
    }

    @Override
    public EntityType getType() {
        return EntityType.WITHER_SKULL;
    }
}
