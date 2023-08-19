package org.bukkit.craftbukkit.entity;

import com.google.common.base.Preconditions;
import net.minecraft.entity.mob.WardenBrain;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.util.math.BlockPos;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class CraftWarden extends CraftMonster implements org.bukkit.entity.Warden {

    public CraftWarden(CraftServer server, WardenEntity entity) {
        super(server, entity);
    }

    @Override
    public WardenEntity getHandle() {
        return (WardenEntity) entity;
    }

    @Override
    public String toString() {
        return "CraftWarden";
    }

    @Override
    public int getAnger() {
        return getHandle().getAngerManager().getAngerFor(getHandle().j());
    }

    @Override
    public int getAnger(Entity entity) {
        Preconditions.checkArgument(entity != null, "Entity cannot be null");

        return getHandle().getAngerManager().getAngerFor(((CraftEntity) entity).getHandle());
    }

    @Override
    public void increaseAnger(Entity entity, int increase) {
        Preconditions.checkArgument(entity != null, "Entity cannot be null");

        getHandle().getAngerManager().increaseAngerAt(((CraftEntity) entity).getHandle(), increase);
    }

    @Override
    public void setAnger(Entity entity, int anger) {
        Preconditions.checkArgument(entity != null, "Entity cannot be null");

        getHandle().removeSuspect(((CraftEntity) entity).getHandle());
        getHandle().getAngerManager().increaseAngerAt(((CraftEntity) entity).getHandle(), anger);
    }

    @Override
    public void clearAnger(Entity entity) {
        Preconditions.checkArgument(entity != null, "Entity cannot be null");

        getHandle().removeSuspect(((CraftEntity) entity).getHandle());
    }

    @Override
    public LivingEntity getEntityAngryAt() {
        return (LivingEntity) getHandle().getPrimeSuspect().map(net.minecraft.entity.Entity::getBukkitEntity).orElse(null);
    }

    @Override
    public void setDisturbanceLocation(Location location) {
        Preconditions.checkArgument(location != null, "Location cannot be null");

        WardenBrain.lookAtDisturbance(getHandle(), BlockPos.ofFloored(location.getX(), location.getY(), location.getZ()));
    }

    @Override
    public AngerLevel getAngerLevel() {
        return switch (getHandle().getAngriness()) {
            case CALM -> AngerLevel.CALM;
            case AGITATED -> AngerLevel.AGITATED;
            case ANGRY -> AngerLevel.ANGRY;
        };
    }
}
