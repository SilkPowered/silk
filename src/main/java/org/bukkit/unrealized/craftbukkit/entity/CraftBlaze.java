package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.monster.EntityBlaze;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.entity.Blaze;
import org.bukkit.unrealized.entity.EntityType;

public class CraftBlaze extends CraftMonster implements Blaze {
    public CraftBlaze(CraftServer server, EntityBlaze entity) {
        super(server, entity);
    }

    @Override
    public EntityBlaze getHandle() {
        return (EntityBlaze) entity;
    }

    @Override
    public String toString() {
        return "CraftBlaze";
    }

    @Override
    public EntityType getType() {
        return EntityType.BLAZE;
    }
}
