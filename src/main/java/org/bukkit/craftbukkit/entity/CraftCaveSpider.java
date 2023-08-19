package org.bukkit.craftbukkit.entity;

import net.minecraft.entity.mob.CaveSpiderEntity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.CaveSpider;

public class CraftCaveSpider extends CraftSpider implements CaveSpider {
    public CraftCaveSpider(CraftServer server, CaveSpiderEntity entity) {
        super(server, entity);
    }

    @Override
    public CaveSpiderEntity getHandle() {
        return (CaveSpiderEntity) entity;
    }

    @Override
    public String toString() {
        return "CraftCaveSpider";
    }
}
