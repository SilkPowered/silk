package org.bukkit.craftbukkit.entity;

import net.minecraft.entity.mob.AmbientEntity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Ambient;

public class CraftAmbient extends CraftMob implements Ambient {
    public CraftAmbient(CraftServer server, AmbientEntity entity) {
        super(server, entity);
    }

    @Override
    public AmbientEntity getHandle() {
        return (AmbientEntity) entity;
    }

    @Override
    public String toString() {
        return "CraftAmbient";
    }
}
