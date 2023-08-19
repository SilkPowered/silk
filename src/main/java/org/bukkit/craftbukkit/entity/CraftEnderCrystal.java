package org.bukkit.craftbukkit.entity;

import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.util.math.BlockPos;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.util.CraftLocation;
import org.bukkit.entity.EnderCrystal;

public class CraftEnderCrystal extends CraftEntity implements EnderCrystal {
    public CraftEnderCrystal(CraftServer server, EndCrystalEntity entity) {
        super(server, entity);
    }

    @Override
    public boolean isShowingBottom() {
        return getHandle().shouldShowBottom();
    }

    @Override
    public void setShowingBottom(boolean showing) {
        getHandle().setShowBottom(showing);
    }

    @Override
    public Location getBeamTarget() {
        BlockPos pos = getHandle().getBeamTarget();
        return pos == null ? null : CraftLocation.toBukkit(pos, getWorld());
    }

    @Override
    public void setBeamTarget(Location location) {
        if (location == null) {
            getHandle().setBeamTarget((BlockPos) null);
        } else if (location.getWorld() != getWorld()) {
            throw new IllegalArgumentException("Cannot set beam target location to different world");
        } else {
            getHandle().setBeamTarget(CraftLocation.toBlockPosition(location));
        }
    }

    @Override
    public EndCrystalEntity getHandle() {
        return (EndCrystalEntity) entity;
    }

    @Override
    public String toString() {
        return "CraftEnderCrystal";
    }
}
