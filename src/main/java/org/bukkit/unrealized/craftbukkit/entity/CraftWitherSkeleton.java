package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.monster.EntitySkeletonWither;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.entity.EntityType;
import org.bukkit.unrealized.entity.Skeleton.SkeletonType;
import org.bukkit.unrealized.entity.WitherSkeleton;

public class CraftWitherSkeleton extends CraftAbstractSkeleton implements WitherSkeleton {

    public CraftWitherSkeleton(CraftServer server, EntitySkeletonWither entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "CraftWitherSkeleton";
    }

    @Override
    public EntityType getType() {
        return EntityType.WITHER_SKELETON;
    }

    @Override
    public SkeletonType getSkeletonType() {
        return SkeletonType.WITHER;
    }
}
