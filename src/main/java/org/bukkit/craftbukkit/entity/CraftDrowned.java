package org.bukkit.craftbukkit.entity;

import net.minecraft.entity.mob.DrownedEntity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Drowned;

public class CraftDrowned extends CraftZombie implements Drowned {

    public CraftDrowned(CraftServer server, DrownedEntity entity) {
        super(server, entity);
    }

    @Override
    public DrownedEntity getHandle() {
        return (DrownedEntity) entity;
    }

    @Override
    public String toString() {
        return "CraftDrowned";
    }
}