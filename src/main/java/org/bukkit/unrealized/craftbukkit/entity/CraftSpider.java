package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.monster.EntitySpider;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.entity.EntityType;
import org.bukkit.unrealized.entity.Spider;

public class CraftSpider extends CraftMonster implements Spider {

    public CraftSpider(CraftServer server, EntitySpider entity) {
        super(server, entity);
    }

    @Override
    public EntitySpider getHandle() {
        return (EntitySpider) entity;
    }

    @Override
    public String toString() {
        return "CraftSpider";
    }

    @Override
    public EntityType getType() {
        return EntityType.SPIDER;
    }
}