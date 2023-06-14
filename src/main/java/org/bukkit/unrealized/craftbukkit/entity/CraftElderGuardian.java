package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.monster.EntityGuardianElder;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.entity.ElderGuardian;
import org.bukkit.unrealized.entity.EntityType;

public class CraftElderGuardian extends CraftGuardian implements ElderGuardian {

    public CraftElderGuardian(CraftServer server, EntityGuardianElder entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "CraftElderGuardian";
    }

    @Override
    public EntityType getType() {
        return EntityType.ELDER_GUARDIAN;
    }

    @Override
    public boolean isElder() {
        return true;
    }
}
