package org.bukkit.craftbukkit.entity;

import net.minecraft.entity.projectile.LlamaSpitEntity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.LlamaSpit;
import org.bukkit.projectiles.ProjectileSource;

public class CraftLlamaSpit extends AbstractProjectile implements LlamaSpit {

    public CraftLlamaSpit(CraftServer server, LlamaSpitEntity entity) {
        super(server, entity);
    }

    @Override
    public LlamaSpitEntity getHandle() {
        return (LlamaSpitEntity) super.getHandle();
    }

    @Override
    public String toString() {
        return "CraftLlamaSpit";
    }

    @Override
    public ProjectileSource getShooter() {
        return (getHandle().v() != null) ? (ProjectileSource) getHandle().v().getBukkitEntity() : null;
    }

    @Override
    public void setShooter(ProjectileSource source) {
        getHandle().setOwner((source != null) ? ((CraftLivingEntity) source).getHandle() : null);
    }
}
