package org.bukkit.craftbukkit.entity;

import net.minecraft.entity.passive.TadpoleEntity;
import org.bukkit.craftbukkit.CraftServer;

public class CraftTadpole extends CraftFish implements org.bukkit.entity.Tadpole {

    public CraftTadpole(CraftServer server, TadpoleEntity entity) {
        super(server, entity);
    }

    @Override
    public TadpoleEntity getHandle() {
        return (TadpoleEntity) entity;
    }

    @Override
    public String toString() {
        return "CraftTadpole";
    }

    @Override
    public int getAge() {
        return getHandle().age;
    }

    @Override
    public void setAge(int age) {
        getHandle().age = age;
    }
}
