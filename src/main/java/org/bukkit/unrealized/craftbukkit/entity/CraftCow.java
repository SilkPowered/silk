package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.animal.EntityCow;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.entity.Cow;
import org.bukkit.unrealized.entity.EntityType;

public class CraftCow extends CraftAnimals implements Cow {

    public CraftCow(CraftServer server, EntityCow entity) {
        super(server, entity);
    }

    @Override
    public EntityCow getHandle() {
        return (EntityCow) entity;
    }

    @Override
    public String toString() {
        return "CraftCow";
    }

    @Override
    public EntityType getType() {
        return EntityType.COW;
    }
}
