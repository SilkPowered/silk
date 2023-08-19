package org.bukkit.craftbukkit.entity;

import com.google.common.base.Preconditions;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Axolotl;

public class CraftAxolotl extends CraftAnimals implements Axolotl {

    public CraftAxolotl(CraftServer server, net.minecraft.entity.passive.AxolotlEntity entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.entity.passive.AxolotlEntity getHandle() {
        return (net.minecraft.entity.passive.AxolotlEntity) super.getHandle();
    }

    @Override
    public String toString() {
        return "CraftAxolotl";
    }

    @Override
    public boolean isPlayingDead() {
        return getHandle().isPlayingDead();
    }

    @Override
    public void setPlayingDead(boolean playingDead) {
        getHandle().setPlayingDead(playingDead);
    }

    @Override
    public Variant getVariant() {
        return Variant.values()[getHandle().getVariant().ordinal()];
    }

    @Override
    public void setVariant(Variant variant) {
        Preconditions.checkArgument(variant != null, "variant");

        getHandle().setVariant(net.minecraft.entity.passive.AxolotlEntity.Variant.byId(variant.ordinal()));
    }
}
