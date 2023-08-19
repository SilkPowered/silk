package org.bukkit.craftbukkit.entity;

import net.minecraft.entity.passive.RabbitEntity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Rabbit.Type;

public class CraftRabbit extends CraftAnimals implements Rabbit {

    public CraftRabbit(CraftServer server, RabbitEntity entity) {
        super(server, entity);
    }

    @Override
    public RabbitEntity getHandle() {
        return (RabbitEntity) entity;
    }

    @Override
    public String toString() {
        return "CraftRabbit{RabbitType=" + getRabbitType() + "}";
    }

    @Override
    public Type getRabbitType() {
        return Type.values()[getHandle().getVariant().ordinal()];
    }

    @Override
    public void setRabbitType(Type type) {
        getHandle().setVariant(RabbitEntity.RabbitType.values()[type.ordinal()]);
    }
}
