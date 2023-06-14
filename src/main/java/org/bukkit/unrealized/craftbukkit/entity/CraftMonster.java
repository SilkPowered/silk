package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.monster.EntityMonster;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.entity.Monster;

public class CraftMonster extends CraftCreature implements Monster, CraftEnemy {

    public CraftMonster(CraftServer server, EntityMonster entity) {
        super(server, entity);
    }

    @Override
    public EntityMonster getHandle() {
        return (EntityMonster) entity;
    }

    @Override
    public String toString() {
        return "CraftMonster";
    }
}
