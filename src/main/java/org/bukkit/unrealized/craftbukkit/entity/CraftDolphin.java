package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.animal.EntityDolphin;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.entity.Dolphin;
import org.bukkit.unrealized.entity.EntityType;

public class CraftDolphin extends CraftWaterMob implements Dolphin {

    public CraftDolphin(CraftServer server, EntityDolphin entity) {
        super(server, entity);
    }

    @Override
    public EntityDolphin getHandle() {
        return (EntityDolphin) super.getHandle();
    }

    @Override
    public String toString() {
        return "CraftDolphin";
    }

    @Override
    public EntityType getType() {
        return EntityType.DOLPHIN;
    }
}
