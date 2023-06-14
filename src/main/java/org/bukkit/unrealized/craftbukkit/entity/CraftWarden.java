package org.bukkit.unrealized.craftbukkit.entity;

import com.google.common.base.Preconditions;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.monster.warden.WardenAi;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.unrealized.Location;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Entity;
import org.bukkit.unrealized.entity.EntityType;
import org.bukkit.unrealized.entity.LivingEntity;
import org.bukkit.unrealized.entity.Warden;

public class CraftWarden extends CraftMonster implements Warden {

    public CraftWarden(CraftServer server, Warden entity) {
        super(server, entity);
    }

    @Override
    public Warden getHandle() {
        return (Warden) entity;
    }

    @Override
    public String toString() {
        return "CraftWarden";
    }

    @Override
    public EntityType getType() {
        return EntityType.WARDEN;
    }

    @Override
    public int getAnger() {
        return getHandle().getAngerManagement().getActiveAnger(getHandle().getTarget());
    }

    @Override
    public int getAnger(Entity entity) {
        Preconditions.checkArgument(entity != null, "Entity cannot be null");

        return getHandle().getAngerManagement().getActiveAnger(((CraftEntity) entity).getHandle());
    }

    @Override
    public void increaseAnger(Entity entity, int increase) {
        Preconditions.checkArgument(entity != null, "Entity cannot be null");

        getHandle().getAngerManagement().increaseAnger(((CraftEntity) entity).getHandle(), increase);
    }

    @Override
    public void setAnger(Entity entity, int anger) {
        Preconditions.checkArgument(entity != null, "Entity cannot be null");

        getHandle().clearAnger(((CraftEntity) entity).getHandle());
        getHandle().getAngerManagement().increaseAnger(((CraftEntity) entity).getHandle(), anger);
    }

    @Override
    public void clearAnger(Entity entity) {
        Preconditions.checkArgument(entity != null, "Entity cannot be null");

        getHandle().clearAnger(((CraftEntity) entity).getHandle());
    }

    @Override
    public LivingEntity getEntityAngryAt() {
        return (LivingEntity) getHandle().getEntityAngryAt().map(net.minecraft.world.entity.Entity::getBukkitEntity).orElse(null);
    }

    @Override
    public void setDisturbanceLocation(Location location) {
        Preconditions.checkArgument(location != null, "Location cannot be null");

        WardenAi.setDisturbanceLocation(getHandle(), BlockPosition.containing(location.getX(), location.getY(), location.getZ()));
    }

    @Override
    public AngerLevel getAngerLevel() {
        return switch (getHandle().getAngerLevel()) {
            case CALM -> AngerLevel.CALM;
            case AGITATED -> AngerLevel.AGITATED;
            case ANGRY -> AngerLevel.ANGRY;
        };
    }
}