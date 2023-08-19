package org.bukkit.craftbukkit.entity;

import com.google.common.base.Preconditions;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.GlowSquid;

public class CraftGlowSquid extends CraftSquid implements GlowSquid {

    public CraftGlowSquid(CraftServer server, net.minecraft.entity.passive.GlowSquidEntity entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.entity.passive.GlowSquidEntity getHandle() {
        return (net.minecraft.entity.passive.GlowSquidEntity) super.getHandle();
    }

    @Override
    public String toString() {
        return "CraftGlowSquid";
    }

    @Override
    public int getDarkTicksRemaining() {
        return getHandle().getDarkTicksRemaining();
    }

    @Override
    public void setDarkTicksRemaining(int darkTicksRemaining) {
        Preconditions.checkArgument(darkTicksRemaining >= 0, "darkTicksRemaining must be >= 0");
        getHandle().setDarkTicks(darkTicksRemaining);
    }
}
