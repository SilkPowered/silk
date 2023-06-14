package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.vehicle.EntityMinecartAbstract;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.entity.EntityType;
import org.bukkit.unrealized.entity.minecart.RideableMinecart;

public class CraftMinecartRideable extends CraftMinecart implements RideableMinecart {
    public CraftMinecartRideable(CraftServer server, EntityMinecartAbstract entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "CraftMinecartRideable";
    }

    @Override
    public EntityType getType() {
        return EntityType.MINECART;
    }
}
