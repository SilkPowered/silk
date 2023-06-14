package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.monster.EntityMagmaCube;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.entity.EntityType;
import org.bukkit.unrealized.entity.MagmaCube;

public class CraftMagmaCube extends CraftSlime implements MagmaCube {

    public CraftMagmaCube(CraftServer server, EntityMagmaCube entity) {
        super(server, entity);
    }

    @Override
    public EntityMagmaCube getHandle() {
        return (EntityMagmaCube) entity;
    }

    @Override
    public String toString() {
        return "CraftMagmaCube";
    }

    @Override
    public EntityType getType() {
        return EntityType.MAGMA_CUBE;
    }
}
