package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.boss.EntityComplexPart;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderDragon;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.unrealized.entity.ComplexEntityPart;
import org.bukkit.unrealized.entity.ComplexLivingEntity;
import org.bukkit.unrealized.entity.EntityType;
import org.bukkit.unrealized.event.entity.EntityDamageEvent;

public class CraftComplexPart extends CraftEntity implements ComplexEntityPart {
    public CraftComplexPart(CraftServer server, EntityComplexPart entity) {
        super(server, entity);
    }

    @Override
    public ComplexLivingEntity getParent() {
        return (ComplexLivingEntity) ((EntityEnderDragon) getHandle().parentMob).getBukkitEntity();
    }

    @Override
    public void setLastDamageCause(EntityDamageEvent cause) {
        getParent().setLastDamageCause(cause);
    }

    @Override
    public EntityDamageEvent getLastDamageCause() {
        return getParent().getLastDamageCause();
    }

    @Override
    public boolean isValid() {
        return getParent().isValid();
    }

    @Override
    public EntityComplexPart getHandle() {
        return (EntityComplexPart) entity;
    }

    @Override
    public String toString() {
        return "CraftComplexPart";
    }

    @Override
    public EntityType getType() {
        return EntityType.UNKNOWN;
    }
}
