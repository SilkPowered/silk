package org.bukkit.craftbukkit.entity;

import com.google.common.base.Preconditions;
import net.minecraft.entity.vehicle.TntMinecartEntity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.minecart.ExplosiveMinecart;

public final class CraftMinecartTNT extends CraftMinecart implements ExplosiveMinecart {
    CraftMinecartTNT(CraftServer server, TntMinecartEntity entity) {
        super(server, entity);
    }

    @Override
    public void setFuseTicks(int ticks) {
        getHandle().fuse = ticks;
    }

    @Override
    public int getFuseTicks() {
        return getHandle().getFuseTicks();
    }

    @Override
    public void ignite() {
        getHandle().prime();
    }

    @Override
    public boolean isIgnited() {
        return getHandle().isPrimed();
    }

    @Override
    public void explode() {
        getHandle().explode(getHandle().getVelocity().horizontalLengthSquared());
    }

    @Override
    public void explode(double power) {
        Preconditions.checkArgument(0 <= power && power <= 5, "Power must be in range [0, 5] (got %s)", power);

        getHandle().explode(power);
    }

    @Override
    public TntMinecartEntity getHandle() {
        return (TntMinecartEntity) super.getHandle();
    }

    @Override
    public String toString() {
        return "CraftMinecartTNT";
    }
}
