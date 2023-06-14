package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.EntityCreature;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.entity.Creature;

public class CraftCreature extends CraftMob implements Creature {
    public CraftCreature(CraftServer server, EntityCreature entity) {
        super(server, entity);
    }

    @Override
    public EntityCreature getHandle() {
        return (EntityCreature) entity;
    }

    @Override
    public String toString() {
        return "CraftCreature";
    }
}
