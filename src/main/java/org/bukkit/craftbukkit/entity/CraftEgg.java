package org.bukkit.craftbukkit.entity;

import net.minecraft.entity.projectile.thrown.EggEntity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Egg;

public class CraftEgg extends CraftThrowableProjectile implements Egg {
    public CraftEgg(CraftServer server, EggEntity entity) {
        super(server, entity);
    }

    @Override
    public EggEntity getHandle() {
        return (EggEntity) entity;
    }

    @Override
    public String toString() {
        return "CraftEgg";
    }
}
