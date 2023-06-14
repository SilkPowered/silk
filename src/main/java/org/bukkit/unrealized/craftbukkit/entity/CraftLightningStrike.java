package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.EntityLightning;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.unrealized.entity.EntityType;
import org.bukkit.unrealized.entity.LightningStrike;

public class CraftLightningStrike extends CraftEntity implements LightningStrike {
    public CraftLightningStrike(final CraftServer server, final EntityLightning entity) {
        super(server, entity);
    }

    @Override
    public boolean isEffect() {
        return getHandle().visualOnly;
    }

    @Override
    public EntityLightning getHandle() {
        return (EntityLightning) entity;
    }

    @Override
    public String toString() {
        return "CraftLightningStrike";
    }

    @Override
    public EntityType getType() {
        return EntityType.LIGHTNING;
    }
}
