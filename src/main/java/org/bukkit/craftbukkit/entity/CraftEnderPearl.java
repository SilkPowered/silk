package org.bukkit.craftbukkit.entity;

import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EnderPearl;

public class CraftEnderPearl extends CraftThrowableProjectile implements EnderPearl {
    public CraftEnderPearl(CraftServer server, EnderPearlEntity entity) {
        super(server, entity);
    }

    @Override
    public EnderPearlEntity getHandle() {
        return (EnderPearlEntity) entity;
    }

    @Override
    public String toString() {
        return "CraftEnderPearl";
    }
}
