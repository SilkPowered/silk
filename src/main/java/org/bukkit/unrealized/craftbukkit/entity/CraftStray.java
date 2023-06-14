package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.monster.EntitySkeletonStray;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.entity.EntityType;
import org.bukkit.unrealized.entity.Skeleton.SkeletonType;
import org.bukkit.unrealized.entity.Stray;

public class CraftStray extends CraftAbstractSkeleton implements Stray {

    public CraftStray(CraftServer server, EntitySkeletonStray entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "CraftStray";
    }

    @Override
    public EntityType getType() {
        return EntityType.STRAY;
    }

    @Override
    public SkeletonType getSkeletonType() {
        return SkeletonType.STRAY;
    }
}
