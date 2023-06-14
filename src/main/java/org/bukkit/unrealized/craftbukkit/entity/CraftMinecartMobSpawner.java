package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.vehicle.EntityMinecartMobSpawner;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.entity.EntityType;
import org.bukkit.unrealized.entity.minecart.SpawnerMinecart;

final class CraftMinecartMobSpawner extends CraftMinecart implements SpawnerMinecart {
    CraftMinecartMobSpawner(CraftServer server, EntityMinecartMobSpawner entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "CraftMinecartMobSpawner";
    }

    @Override
    public EntityType getType() {
        return EntityType.MINECART_MOB_SPAWNER;
    }
}
