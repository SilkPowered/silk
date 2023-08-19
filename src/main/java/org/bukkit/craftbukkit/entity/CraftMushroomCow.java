package org.bukkit.craftbukkit.entity;

import com.google.common.base.Preconditions;
import net.minecraft.entity.passive.MooshroomEntity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.MushroomCow.Variant;

public class CraftMushroomCow extends CraftCow implements MushroomCow {
    public CraftMushroomCow(CraftServer server, MooshroomEntity entity) {
        super(server, entity);
    }

    @Override
    public MooshroomEntity getHandle() {
        return (MooshroomEntity) entity;
    }

    @Override
    public Variant getVariant() {
        return Variant.values()[getHandle().getVariant().ordinal()];
    }

    @Override
    public void setVariant(Variant variant) {
        Preconditions.checkArgument(variant != null, "variant");

        getHandle().setVariant(MooshroomEntity.Type.values()[variant.ordinal()]);
    }

    @Override
    public String toString() {
        return "CraftMushroomCow";
    }
}
