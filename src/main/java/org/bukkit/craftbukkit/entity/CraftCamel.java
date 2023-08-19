package org.bukkit.craftbukkit.entity;

import net.minecraft.entity.EntityPose;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Camel;
import org.bukkit.entity.Horse;

public class CraftCamel extends CraftAbstractHorse implements Camel {

    public CraftCamel(CraftServer server, net.minecraft.entity.passive.CamelEntity entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.entity.passive.CamelEntity getHandle() {
        return (net.minecraft.entity.passive.CamelEntity) super.getHandle();
    }

    @Override
    public String toString() {
        return "CraftCamel";
    }

    @Override
    public Horse.Variant getVariant() {
        return Horse.Variant.CAMEL;
    }

    @Override
    public boolean isDashing() {
        return getHandle().isDashing();
    }

    @Override
    public void setDashing(boolean dashing) {
        getHandle().setDashing(dashing);
    }

    @Override
    public boolean isSitting() {
        return getHandle().getPose() == EntityPose.SITTING;
    }

    @Override
    public void setSitting(boolean sitting) {
        if (sitting) {
            getHandle().startSitting();
        } else {
            getHandle().startStanding();
        }
    }
}
