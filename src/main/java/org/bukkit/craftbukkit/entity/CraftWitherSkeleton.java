package org.bukkit.craftbukkit.entity;

import net.minecraft.entity.mob.WitherSkeletonEntity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.entity.WitherSkeleton;

public class CraftWitherSkeleton extends CraftAbstractSkeleton implements WitherSkeleton {

    public CraftWitherSkeleton(CraftServer server, WitherSkeletonEntity entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "CraftWitherSkeleton";
    }

    @Override
    public SkeletonType getSkeletonType() {
        return SkeletonType.WITHER;
    }
}
