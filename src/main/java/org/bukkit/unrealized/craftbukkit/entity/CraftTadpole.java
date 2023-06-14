package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.animal.frog.Tadpole;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.entity.EntityType;
import org.bukkit.unrealized.entity.Tadpole;

public class CraftTadpole extends CraftFish implements Tadpole {

    public CraftTadpole(CraftServer server, Tadpole entity) {
        super(server, entity);
    }

    @Override
    public Tadpole getHandle() {
        return (Tadpole) entity;
    }

    @Override
    public String toString() {
        return "CraftTadpole";
    }

    @Override
    public EntityType getType() {
        return EntityType.TADPOLE;
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
