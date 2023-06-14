package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.monster.EntityPillager;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.craftbukkit.inventory.CraftInventory;
import org.bukkit.unrealized.entity.EntityType;
import org.bukkit.unrealized.entity.Pillager;
import org.bukkit.unrealized.inventory.Inventory;

public class CraftPillager extends CraftIllager implements Pillager {

    public CraftPillager(CraftServer server, EntityPillager entity) {
        super(server, entity);
    }

    @Override
    public EntityPillager getHandle() {
        return (EntityPillager) super.getHandle();
    }

    @Override
    public EntityType getType() {
        return EntityType.PILLAGER;
    }

    @Override
    public String toString() {
        return "CraftPillager";
    }

    @Override
    public Inventory getInventory() {
        return new CraftInventory(getHandle().inventory);
    }
}
