package org.bukkit.craftbukkit.entity;

import com.google.common.base.Preconditions;
import net.minecraft.entity.mob.SkeletonEntity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Skeleton;

public class CraftSkeleton extends CraftAbstractSkeleton implements Skeleton {

    public CraftSkeleton(CraftServer server, SkeletonEntity entity) {
        super(server, entity);
    }

    @Override
    public boolean isConverting() {
        return this.getHandle().isConverting();
    }

    @Override
    public int getConversionTime() {
        Preconditions.checkState(this.isConverting(), "Entity is not converting");
        return this.getHandle().conversionTime;
    }

    @Override
    public void setConversionTime(int time) {
        if (time < 0) {
            this.getHandle().conversionTime = -1;
            this.getHandle().getDataTracker().set(SkeletonEntity.DATA_STRAY_CONVERSION_ID, false);
        } else {
            this.getHandle().setConversionTime(time);
        }
    }

    @Override
    public SkeletonEntity getHandle() {
        return (SkeletonEntity) entity;
    }

    @Override
    public String toString() {
        return "CraftSkeleton";
    }

    @Override
    public SkeletonType getSkeletonType() {
       return SkeletonType.NORMAL;
    }
}
