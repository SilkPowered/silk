package org.bukkit.craftbukkit.entity;

import com.google.common.base.Preconditions;
import net.minecraft.entity.passive.FrogEntity;
import net.minecraft.registry.Registries;
import org.bukkit.Registry;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.util.CraftNamespacedKey;
import org.bukkit.entity.Entity;

public class CraftFrog extends CraftAnimals implements org.bukkit.entity.Frog {

    public CraftFrog(CraftServer server, FrogEntity entity) {
        super(server, entity);
    }

    @Override
    public FrogEntity getHandle() {
        return (FrogEntity) entity;
    }

    @Override
    public String toString() {
        return "CraftFrog";
    }

    @Override
    public Entity getTongueTarget() {
        return getHandle().getFrogTarget().map(net.minecraft.entity.Entity::getBukkitEntity).orElse(null);
    }

    @Override
    public void setTongueTarget(Entity target) {
        if (target == null) {
            getHandle().clearFrogTarget();
        } else {
            getHandle().setFrogTarget(((CraftEntity) target).getHandle());
        }
    }

    @Override
    public Variant getVariant() {
        return Registry.FROG_VARIANT.get(CraftNamespacedKey.fromMinecraft(Registries.FROG_VARIANT.getId(getHandle().getVariant())));
    }

    @Override
    public void setVariant(Variant variant) {
        Preconditions.checkArgument(variant != null, "variant");

        getHandle().setVariant(Registries.FROG_VARIANT.get(CraftNamespacedKey.toMinecraft(variant.getKey())));
    }
}
