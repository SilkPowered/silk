package org.bukkit.craftbukkit.entity;

import net.minecraft.entity.mob.SkeletonHorseEntity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.entity.SkeletonHorse;

public class CraftSkeletonHorse extends CraftAbstractHorse implements SkeletonHorse {

    public CraftSkeletonHorse(CraftServer server, SkeletonHorseEntity entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "CraftSkeletonHorse";
    }

    @Override
    public Variant getVariant() {
        return Variant.SKELETON_HORSE;
    }

    @Override
    public SkeletonHorseEntity getHandle() {
        return (SkeletonHorseEntity) entity;
    }

    @Override
    public boolean isTrapped() {
        return getHandle().isTrap();
    }

    @Override
    public void setTrapped(boolean trapped) {
        getHandle().setTrap(trapped);
    }

    @Override
    public int getTrapTime() {
        return getHandle().trapTime;
    }

    @Override
    public void setTrapTime(int trapTime) {
        getHandle().trapTime = trapTime;
    }
}
