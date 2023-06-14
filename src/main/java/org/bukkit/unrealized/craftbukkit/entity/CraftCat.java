package org.bukkit.unrealized.craftbukkit.entity;

import com.google.common.base.Preconditions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.animal.EntityCat;
import net.minecraft.world.item.EnumColor;
import org.bukkit.unrealized.DyeColor;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.entity.Cat;
import org.bukkit.unrealized.entity.EntityType;

public class CraftCat extends CraftTameableAnimal implements Cat {

    public CraftCat(CraftServer server, EntityCat entity) {
        super(server, entity);
    }

    @Override
    public EntityCat getHandle() {
        return (EntityCat) super.getHandle();
    }

    @Override
    public EntityType getType() {
        return EntityType.CAT;
    }

    @Override
    public String toString() {
        return "CraftCat";
    }

    @Override
    public Type getCatType() {
        return Type.values()[BuiltInRegistries.CAT_VARIANT.getId(getHandle().getVariant())];
    }

    @Override
    public void setCatType(Type type) {
        Preconditions.checkArgument(type != null, "Cannot have null Type");

        getHandle().setVariant(BuiltInRegistries.CAT_VARIANT.byId(type.ordinal()));
    }

    @Override
    public DyeColor getCollarColor() {
        return DyeColor.getByWoolData((byte) getHandle().getCollarColor().getId());
    }

    @Override
    public void setCollarColor(DyeColor color) {
        getHandle().setCollarColor(EnumColor.byId(color.getWoolData()));
    }
}
