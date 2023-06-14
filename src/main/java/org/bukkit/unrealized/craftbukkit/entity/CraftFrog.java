package org.bukkit.unrealized.craftbukkit.entity;

import com.google.common.base.Preconditions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.animal.frog.Frog;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.unrealized.Registry;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.craftbukkit.util.CraftNamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.unrealized.entity.EntityType;
import org.bukkit.unrealized.entity.Frog;

public class CraftFrog extends CraftAnimals implements Frog {

    public CraftFrog(CraftServer server, Frog entity) {
        super(server, entity);
    }

    @Override
    public Frog getHandle() {
        return (Frog) entity;
    }

    @Override
    public String toString() {
        return "CraftFrog";
    }

    @Override
    public EntityType getType() {
        return EntityType.FROG;
    }

    @Override
    public Entity getTongueTarget() {
        return getHandle().getTongueTarget().map(net.minecraft.world.entity.Entity::getBukkitEntity).orElse(null);
    }

    @Override
    public void setTongueTarget(Entity target) {
        if (target == null) {
            getHandle().eraseTongueTarget();
        } else {
            getHandle().setTongueTarget(((CraftEntity) target).getHandle());
        }
    }

    @Override
    public Variant getVariant() {
        return Registry.FROG_VARIANT.get(CraftNamespacedKey.fromMinecraft(BuiltInRegistries.FROG_VARIANT.getKey(getHandle().getVariant())));
    }

    @Override
    public void setVariant(Variant variant) {
        Preconditions.checkArgument(variant != null, "variant");

        getHandle().setVariant(BuiltInRegistries.FROG_VARIANT.get(CraftNamespacedKey.toMinecraft(variant.getKey())));
    }
}
