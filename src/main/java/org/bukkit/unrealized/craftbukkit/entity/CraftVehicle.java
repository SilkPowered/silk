package org.bukkit.unrealized.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.unrealized.entity.Vehicle;

public abstract class CraftVehicle extends CraftEntity implements Vehicle {
    public CraftVehicle(CraftServer server, net.minecraft.world.entity.Entity entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "CraftVehicle{passenger=" + getPassenger() + '}';
    }
}
