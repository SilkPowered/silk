package org.bukkit.craftbukkit.entity;

import net.minecraft.entity.mob.ZoglinEntity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Zoglin;

public class CraftZoglin extends CraftMonster implements Zoglin {

    public CraftZoglin(CraftServer server, ZoglinEntity entity) {
        super(server, entity);
    }

    @Override
    public boolean isBaby() {
        return getHandle().h_();
    }

    @Override
    public void setBaby(boolean flag) {
        getHandle().a(flag);
    }

    @Override
    public ZoglinEntity getHandle() {
        return (ZoglinEntity) entity;
    }

    @Override
    public String toString() {
        return "CraftZoglin";
    }

    @Override
    public int getAge() {
        return getHandle().h_() ? -1 : 0;
    }

    @Override
    public void setAge(int i) {
        getHandle().a(i < 0);
    }

    @Override
    public void setAgeLock(boolean b) {
    }

    @Override
    public boolean getAgeLock() {
        return false;
    }

    @Override
    public void setBaby() {
        getHandle().a(true);
    }

    @Override
    public void setAdult() {
        getHandle().a(false);
    }

    @Override
    public boolean isAdult() {
        return !getHandle().h_();
    }

    @Override
    public boolean canBreed() {
        return false;
    }

    @Override
    public void setBreed(boolean b) {
    }
}
