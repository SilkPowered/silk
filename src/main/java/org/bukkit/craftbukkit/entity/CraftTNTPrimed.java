package org.bukkit.craftbukkit.entity;

import net.minecraft.entity.TntEntity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.TNTPrimed;

public class CraftTNTPrimed extends CraftEntity implements TNTPrimed {

    public CraftTNTPrimed(CraftServer server, TntEntity entity) {
        super(server, entity);
    }

    @Override
    public float getYield() {
        return getHandle().yield;
    }

    @Override
    public boolean isIncendiary() {
        return getHandle().isIncendiary;
    }

    @Override
    public void setIsIncendiary(boolean isIncendiary) {
        getHandle().isIncendiary = isIncendiary;
    }

    @Override
    public void setYield(float yield) {
        getHandle().yield = yield;
    }

    @Override
    public int getFuseTicks() {
        return getHandle().getFuse();
    }

    @Override
    public void setFuseTicks(int fuseTicks) {
        getHandle().setFuse(fuseTicks);
    }

    @Override
    public TntEntity getHandle() {
        return (TntEntity) entity;
    }

    @Override
    public String toString() {
        return "CraftTNTPrimed";
    }

    @Override
    public Entity getSource() {
        net.minecraft.entity.LivingEntity source = getHandle().getOwner();

        return (source != null) ? source.getBukkitEntity() : null;
    }

    @Override
    public void setSource(Entity source) {
        if (source instanceof LivingEntity) {
            getHandle().owner = ((CraftLivingEntity) source).getHandle();
        } else {
            getHandle().owner = null;
        }
    }
}
