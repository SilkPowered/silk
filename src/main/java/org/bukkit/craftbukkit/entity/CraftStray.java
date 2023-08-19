package org.bukkit.craftbukkit.entity;

import net.minecraft.entity.mob.StrayEntity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.entity.Stray;

public class CraftStray extends CraftAbstractSkeleton implements Stray {

    public CraftStray(CraftServer server, StrayEntity entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "CraftStray";
    }

    @Override
    public SkeletonType getSkeletonType() {
        return SkeletonType.STRAY;
    }
}
