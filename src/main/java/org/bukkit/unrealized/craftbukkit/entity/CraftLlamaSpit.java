package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.projectile.EntityLlamaSpit;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.unrealized.entity.EntityType;
import org.bukkit.unrealized.entity.LlamaSpit;
import org.bukkit.unrealized.projectiles.ProjectileSource;

public class CraftLlamaSpit extends AbstractProjectile implements LlamaSpit {

    public CraftLlamaSpit(CraftServer server, EntityLlamaSpit entity) {
        super(server, entity);
    }

    @Override
    public EntityLlamaSpit getHandle() {
        return (EntityLlamaSpit) super.getHandle();
    }

    @Override
    public String toString() {
        return "CraftLlamaSpit";
    }

    @Override
    public EntityType getType() {
        return EntityType.LLAMA_SPIT;
    }

    @Override
    public ProjectileSource getShooter() {
        return (getHandle().getOwner() != null) ? (ProjectileSource) getHandle().getOwner().getBukkitEntity() : null;
    }

    @Override
    public void setShooter(ProjectileSource source) {
        getHandle().setOwner((source != null) ? ((CraftLivingEntity) source).getHandle() : null);
    }
}
