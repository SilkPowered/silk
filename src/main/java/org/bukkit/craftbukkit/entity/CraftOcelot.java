package org.bukkit.craftbukkit.entity;

import net.minecraft.entity.passive.OcelotEntity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Ocelot.Type;

public class CraftOcelot extends CraftAnimals implements Ocelot {
    public CraftOcelot(CraftServer server, OcelotEntity ocelot) {
        super(server, ocelot);
    }

    @Override
    public OcelotEntity getHandle() {
        return (OcelotEntity) entity;
    }

    @Override
    public boolean isTrusting() {
        return getHandle().isTrusting();
    }

    @Override
    public void setTrusting(boolean trust) {
        getHandle().setTrusting(trust);
    }

    @Override
    public Type getCatType() {
        return Type.WILD_OCELOT;
    }

    @Override
    public void setCatType(Type type) {
        throw new UnsupportedOperationException("Cats are now a different entity!");
    }

    @Override
    public String toString() {
        return "CraftOcelot";
    }
}
