package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.animal.horse.EntityHorseDonkey;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.entity.Donkey;
import org.bukkit.unrealized.entity.EntityType;
import org.bukkit.unrealized.entity.Horse.Variant;

public class CraftDonkey extends CraftChestedHorse implements Donkey {

    public CraftDonkey(CraftServer server, EntityHorseDonkey entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "CraftDonkey";
    }

    @Override
    public EntityType getType() {
        return EntityType.DONKEY;
    }

    @Override
    public Variant getVariant() {
        return Variant.DONKEY;
    }
}
