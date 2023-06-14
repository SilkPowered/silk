package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.EntityExperienceOrb;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.unrealized.entity.EntityType;
import org.bukkit.unrealized.entity.ExperienceOrb;

public class CraftExperienceOrb extends CraftEntity implements ExperienceOrb {
    public CraftExperienceOrb(CraftServer server, EntityExperienceOrb entity) {
        super(server, entity);
    }

    @Override
    public int getExperience() {
        return getHandle().value;
    }

    @Override
    public void setExperience(int value) {
        getHandle().value = value;
    }

    @Override
    public EntityExperienceOrb getHandle() {
        return (EntityExperienceOrb) entity;
    }

    @Override
    public String toString() {
        return "CraftExperienceOrb";
    }

    @Override
    public EntityType getType() {
        return EntityType.EXPERIENCE_ORB;
    }
}
