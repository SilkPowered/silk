package org.bukkit.unrealized.craftbukkit.entity;

import com.google.common.base.Preconditions;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.entity.EntityType;
import org.bukkit.unrealized.entity.GlowSquid;

public class CraftGlowSquid extends CraftSquid implements GlowSquid {

    public CraftGlowSquid(CraftServer server, net.minecraft.world.entity.GlowSquid entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.world.entity.GlowSquid getHandle() {
        return (net.minecraft.world.entity.GlowSquid) super.getHandle();
    }

    @Override
    public EntityType getType() {
        return EntityType.GLOW_SQUID;
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