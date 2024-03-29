package org.bukkit.craftbukkit.entity;

import net.minecraft.entity.projectile.DragonFireballEntity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.DragonFireball;

public class CraftDragonFireball extends CraftFireball implements DragonFireball {
    public CraftDragonFireball(CraftServer server, DragonFireballEntity entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "CraftDragonFireball";
    }
}
