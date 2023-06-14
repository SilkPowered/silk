package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.animal.horse.EntityHorseMule;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.entity.EntityType;
import org.bukkit.unrealized.entity.Horse.Variant;
import org.bukkit.unrealized.entity.Mule;

public class CraftMule extends CraftChestedHorse implements Mule {

    public CraftMule(CraftServer server, EntityHorseMule entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "CraftMule";
    }

    @Override
    public EntityType getType() {
        return EntityType.MULE;
    }

    @Override
    public Variant getVariant() {
        return Variant.MULE;
    }
}
