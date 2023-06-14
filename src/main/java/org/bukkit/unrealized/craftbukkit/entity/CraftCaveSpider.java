package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.monster.EntityCaveSpider;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.entity.CaveSpider;
import org.bukkit.unrealized.entity.EntityType;

public class CraftCaveSpider extends CraftSpider implements CaveSpider {
    public CraftCaveSpider(CraftServer server, EntityCaveSpider entity) {
        super(server, entity);
    }

    @Override
    public EntityCaveSpider getHandle() {
        return (EntityCaveSpider) entity;
    }

    @Override
    public String toString() {
        return "CraftCaveSpider";
    }

    @Override
    public EntityType getType() {
        return EntityType.CAVE_SPIDER;
    }
}
