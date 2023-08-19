package org.bukkit.craftbukkit.entity;

import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.registry.entry.RegistryEntry;
import org.bukkit.Art;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.CraftArt;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Painting;

public class CraftPainting extends CraftHanging implements Painting {

    public CraftPainting(CraftServer server, PaintingEntity entity) {
        super(server, entity);
    }

    @Override
    public Art getArt() {
        RegistryEntry<PaintingVariant> art = getHandle().getVariant();
        return CraftArt.NotchToBukkit(art);
    }

    @Override
    public boolean setArt(Art art) {
        return setArt(art, false);
    }

    @Override
    public boolean setArt(Art art, boolean force) {
        PaintingEntity painting = this.getHandle();
        RegistryEntry<PaintingVariant> oldArt = painting.getVariant();
        painting.setVariant(CraftArt.BukkitToNotch(art));
        painting.setFacing(painting.cB());
        if (!force && !getHandle().generation && !painting.canStayAttached()) {
            // Revert painting since it doesn't fit
            painting.setVariant(oldArt);
            painting.setFacing(painting.cB());
            return false;
        }
        this.update();
        return true;
    }

    @Override
    public boolean setFacingDirection(BlockFace face, boolean force) {
        if (super.setFacingDirection(face, force)) {
            update();
            return true;
        }

        return false;
    }

    @Override
    public PaintingEntity getHandle() {
        return (PaintingEntity) entity;
    }

    @Override
    public String toString() {
        return "CraftPainting{art=" + getArt() + "}";
    }
}
