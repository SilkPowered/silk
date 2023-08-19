package org.bukkit.craftbukkit.entity;

import net.minecraft.entity.mob.SlimeEntity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Slime;

public class CraftSlime extends CraftMob implements Slime, CraftEnemy {

    public CraftSlime(CraftServer server, SlimeEntity entity) {
        super(server, entity);
    }

    @Override
    public int getSize() {
        return getHandle().getSize();
    }

    @Override
    public void setSize(int size) {
        getHandle().setSize(size, true);
    }

    @Override
    public SlimeEntity getHandle() {
        return (SlimeEntity) entity;
    }

    @Override
    public String toString() {
        return "CraftSlime";
    }
}
