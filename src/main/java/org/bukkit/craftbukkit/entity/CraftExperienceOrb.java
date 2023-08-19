package org.bukkit.craftbukkit.entity;

import net.minecraft.entity.ExperienceOrbEntity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.ExperienceOrb;

public class CraftExperienceOrb extends CraftEntity implements ExperienceOrb {
    public CraftExperienceOrb(CraftServer server, ExperienceOrbEntity entity) {
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
    public ExperienceOrbEntity getHandle() {
        return (ExperienceOrbEntity) entity;
    }

    @Override
    public String toString() {
        return "CraftExperienceOrb";
    }
}
