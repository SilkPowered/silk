package org.bukkit.unrealized.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.entity.EntityType;
import org.bukkit.unrealized.entity.GlowItemFrame;

public class CraftGlowItemFrame extends CraftItemFrame implements GlowItemFrame {

    public CraftGlowItemFrame(CraftServer server, net.minecraft.world.entity.decoration.GlowItemFrame entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.world.entity.decoration.GlowItemFrame getHandle() {
        return (net.minecraft.world.entity.decoration.GlowItemFrame) super.getHandle();
    }

    @Override
    public EntityType getType() {
        return EntityType.GLOW_ITEM_FRAME;
    }

    @Override
    public String toString() {
        return "CraftGlowItemFrame{item=" + getItem() + ", rotation=" + getRotation() + "}";
    }
}