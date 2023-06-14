package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.animal.EntityCod;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.entity.Cod;
import org.bukkit.unrealized.entity.EntityType;

public class CraftCod extends CraftFish implements Cod {

    public CraftCod(CraftServer server, EntityCod entity) {
        super(server, entity);
    }

    @Override
    public EntityCod getHandle() {
        return (EntityCod) super.getHandle();
    }

    @Override
    public String toString() {
        return "CraftCod";
    }

    @Override
    public EntityType getType() {
        return EntityType.COD;
    }
}
