package org.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.GlowItemFrame;

public class CraftGlowItemFrame extends CraftItemFrame implements GlowItemFrame {

    public CraftGlowItemFrame(CraftServer server, net.minecraft.entity.decoration.GlowItemFrameEntity entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.entity.decoration.GlowItemFrameEntity getHandle() {
        return (net.minecraft.entity.decoration.GlowItemFrameEntity) super.getHandle();
    }

    @Override
    public String toString() {
        return "CraftGlowItemFrame{item=" + getItem() + ", rotation=" + getRotation() + "}";
    }
}
