package org.bukkit.craftbukkit.entity;

import net.minecraft.entity.passive.ChickenEntity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Chicken;

public class CraftChicken extends CraftAnimals implements Chicken {

    public CraftChicken(CraftServer server, ChickenEntity entity) {
        super(server, entity);
    }

    @Override
    public ChickenEntity getHandle() {
        return (ChickenEntity) entity;
    }

    @Override
    public String toString() {
        return "CraftChicken";
    }
}
