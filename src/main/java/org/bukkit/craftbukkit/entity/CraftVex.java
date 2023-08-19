package org.bukkit.craftbukkit.entity;

import com.google.common.base.Preconditions;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.util.math.BlockPos;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.util.CraftLocation;
import org.bukkit.entity.Vex;

public class CraftVex extends CraftMonster implements Vex {

    public CraftVex(CraftServer server, VexEntity entity) {
        super(server, entity);
    }

    @Override
    public VexEntity getHandle() {
        return (VexEntity) super.getHandle();
    }

    @Override
    public String toString() {
        return "CraftVex";
    }

    @Override
    public boolean isCharging() {
        return getHandle().isCharging();
    }

    @Override
    public void setCharging(boolean charging) {
        getHandle().setCharging(charging);
    }

    @Override
    public Location getBound() {
        BlockPos blockPosition = getHandle().getBounds();
        return (blockPosition == null) ? null : CraftLocation.toBukkit(blockPosition, getWorld());
    }

    @Override
    public void setBound(Location location) {
        if (location == null) {
            getHandle().setBounds(null);
        } else {
            Preconditions.checkArgument(getWorld().equals(location.getWorld()), "The bound world cannot be different to the entity's world.");
            getHandle().setBounds(CraftLocation.toBlockPosition(location));
        }
    }

    @Override
    public int getLifeTicks() {
        return getHandle().limitedLifeTicks;
    }

    @Override
    public void setLifeTicks(int lifeTicks) {
        getHandle().setLifeTicks(lifeTicks);
        if (lifeTicks < 0) {
            getHandle().hasLimitedLife = false;
        }
    }

    @Override
    public boolean hasLimitedLife() {
        return getHandle().hasLimitedLife;
    }
}