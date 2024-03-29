package org.bukkit.craftbukkit.entity;

import net.minecraft.entity.passive.WolfEntity;
import org.bukkit.DyeColor;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Wolf;

public class CraftWolf extends CraftTameableAnimal implements Wolf {
    public CraftWolf(CraftServer server, WolfEntity wolf) {
        super(server, wolf);
    }

    @Override
    public boolean isAngry() {
        return getHandle().hasAngerTime();
    }

    @Override
    public void setAngry(boolean angry) {
        if (angry) {
            getHandle().c();
        } else {
            getHandle().stopAnger();
        }
    }

    @Override
    public WolfEntity getHandle() {
        return (WolfEntity) entity;
    }

    @Override
    public DyeColor getCollarColor() {
        return DyeColor.getByWoolData((byte) getHandle().getCollarColor().getId());
    }

    @Override
    public void setCollarColor(DyeColor color) {
        getHandle().setCollarColor(net.minecraft.util.DyeColor.byId(color.getWoolData()));
    }

    @Override
    public boolean isWet() {
        return getHandle().isFurWet();
    }

    @Override
    public float getTailAngle() {
        return getHandle().getTailAngle();
    }

    @Override
    public boolean isInterested() {
        return getHandle().isBegging();
    }

    @Override
    public void setInterested(boolean flag) {
        getHandle().setBegging(flag);
    }
}
