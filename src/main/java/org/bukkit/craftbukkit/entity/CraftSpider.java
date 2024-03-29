package org.bukkit.craftbukkit.entity;

import net.minecraft.entity.mob.SpiderEntity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Spider;

public class CraftSpider extends CraftMonster implements Spider {

    public CraftSpider(CraftServer server, SpiderEntity entity) {
        super(server, entity);
    }

    @Override
    public SpiderEntity getHandle() {
        return (SpiderEntity) entity;
    }

    @Override
    public String toString() {
        return "CraftSpider";
    }
}
