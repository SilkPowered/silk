package org.bukkit.craftbukkit.entity;

import net.minecraft.entity.passive.DolphinEntity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Dolphin;

public class CraftDolphin extends CraftWaterMob implements Dolphin {

    public CraftDolphin(CraftServer server, DolphinEntity entity) {
        super(server, entity);
    }

    @Override
    public DolphinEntity getHandle() {
        return (DolphinEntity) super.getHandle();
    }

    @Override
    public String toString() {
        return "CraftDolphin";
    }
}
