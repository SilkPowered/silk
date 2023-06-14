package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.animal.horse.EntityHorseZombie;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.entity.EntityType;
import org.bukkit.unrealized.entity.Horse.Variant;
import org.bukkit.unrealized.entity.ZombieHorse;

public class CraftZombieHorse extends CraftAbstractHorse implements ZombieHorse {

    public CraftZombieHorse(CraftServer server, EntityHorseZombie entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "CraftZombieHorse";
    }

    @Override
    public EntityType getType() {
        return EntityType.ZOMBIE_HORSE;
    }

    @Override
    public Variant getVariant() {
        return Variant.UNDEAD_HORSE;
    }
}
