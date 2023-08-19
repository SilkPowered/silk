package org.bukkit.craftbukkit.entity;

import net.minecraft.entity.mob.AbstractSkeletonEntity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.AbstractSkeleton;
import org.bukkit.entity.Skeleton;

public abstract class CraftAbstractSkeleton extends CraftMonster implements AbstractSkeleton {

    public CraftAbstractSkeleton(CraftServer server, AbstractSkeletonEntity entity) {
        super(server, entity);
    }

    @Override
    public void setSkeletonType(Skeleton.SkeletonType type) {
        throw new UnsupportedOperationException("Not supported.");
    }
}
