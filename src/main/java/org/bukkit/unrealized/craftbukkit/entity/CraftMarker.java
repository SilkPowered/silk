package org.bukkit.unrealized.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.unrealized.entity.EntityType;
import org.bukkit.unrealized.entity.Marker;

public class CraftMarker extends CraftEntity implements Marker {

    public CraftMarker(CraftServer server, net.minecraft.world.entity.Marker entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.world.entity.Marker getHandle() {
        return (net.minecraft.world.entity.Marker) super.getHandle();
    }

    @Override
    public EntityType getType() {
        return EntityType.MARKER;
    }

    @Override
    public String toString() {
        return "CraftMarker";
    }
}
