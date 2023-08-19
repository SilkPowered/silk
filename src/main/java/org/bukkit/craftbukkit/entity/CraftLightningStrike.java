package org.bukkit.craftbukkit.entity;

import net.minecraft.entity.LightningEntity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.LightningStrike;

public class CraftLightningStrike extends CraftEntity implements LightningStrike {
    public CraftLightningStrike(final CraftServer server, final LightningEntity entity) {
        super(server, entity);
    }

    @Override
    public boolean isEffect() {
        return getHandle().visualOnly;
    }

    @Override
    public LightningEntity getHandle() {
        return (LightningEntity) entity;
    }

    @Override
    public String toString() {
        return "CraftLightningStrike";
    }

    // Spigot start
    private final LightningStrike.Spigot spigot = new LightningStrike.Spigot() {

        @Override
        public boolean isSilent()
        {
            return getHandle().isSilent;
        }
    };

    @Override
    public LightningStrike.Spigot spigot() {
        return spigot;
    }
    // Spigot end
}
