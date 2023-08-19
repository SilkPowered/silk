package org.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Goat;

public class CraftGoat extends CraftAnimals implements Goat {

    public CraftGoat(CraftServer server, net.minecraft.entity.passive.GoatEntity entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.entity.passive.GoatEntity getHandle() {
        return (net.minecraft.entity.passive.GoatEntity) super.getHandle();
    }

    @Override
    public String toString() {
        return "CraftGoat";
    }

    @Override
    public boolean hasLeftHorn() {
        return getHandle().hasLeftHorn();
    }

    @Override
    public void setLeftHorn(boolean hasHorn) {
        getHandle().getDataTracker().set(net.minecraft.entity.passive.GoatEntity.DATA_HAS_LEFT_HORN, hasHorn);
    }

    @Override
    public boolean hasRightHorn() {
        return getHandle().hasRightHorn();
    }

    @Override
    public void setRightHorn(boolean hasHorn) {
        getHandle().getDataTracker().set(net.minecraft.entity.passive.GoatEntity.DATA_HAS_RIGHT_HORN, hasHorn);
    }

    @Override
    public boolean isScreaming() {
        return getHandle().isScreaming();
    }

    @Override
    public void setScreaming(boolean screaming) {
        getHandle().setScreaming(screaming);
    }
}
