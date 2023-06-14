package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.monster.piglin.EntityPiglinBrute;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.entity.EntityType;
import org.bukkit.unrealized.entity.PiglinBrute;

public class CraftPiglinBrute extends CraftPiglinAbstract implements PiglinBrute {

    public CraftPiglinBrute(CraftServer server, EntityPiglinBrute entity) {
        super(server, entity);
    }

    @Override
    public EntityPiglinBrute getHandle() {
        return (EntityPiglinBrute) super.getHandle();
    }

    @Override
    public EntityType getType() {
        return EntityType.PIGLIN_BRUTE;
    }

    @Override
    public String toString() {
        return "CraftPiglinBrute";
    }
}
