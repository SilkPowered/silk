package org.bukkit.craftbukkit.entity;

import com.google.common.base.Preconditions;
import net.minecraft.entity.mob.ShulkerEntity;
import org.bukkit.DyeColor;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.block.CraftBlock;
import org.bukkit.entity.Shulker;

public class CraftShulker extends CraftGolem implements Shulker, CraftEnemy {

    public CraftShulker(CraftServer server, ShulkerEntity entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "CraftShulker";
    }

    @Override
    public ShulkerEntity getHandle() {
        return (ShulkerEntity) entity;
    }

    @Override
    public DyeColor getColor() {
        return DyeColor.getByWoolData(getHandle().getDataTracker().get(ShulkerEntity.DATA_COLOR_ID));
    }

    @Override
    public void setColor(DyeColor color) {
        getHandle().getDataTracker().set(ShulkerEntity.DATA_COLOR_ID, (color == null) ? 16 : color.getWoolData());
    }

    @Override
    public float getPeek() {
        return (float) getHandle().getPeekAmount() / 100;
    }

    @Override
    public void setPeek(float value) {
        Preconditions.checkArgument(value >= 0 && value <= 1, "value needs to be in between or equal to 0 and 1");
        getHandle().setPeekAmount((int) (value * 100));
    }

    @Override
    public BlockFace getAttachedFace() {
        return CraftBlock.notchToBlockFace(getHandle().getAttachedFace());
    }

    @Override
    public void setAttachedFace(BlockFace face) {
        Preconditions.checkNotNull(face, "face cannot be null");
        Preconditions.checkArgument(face.isCartesian(), "%s is not a valid block face to attach a shulker to, a cartesian block face is expected", face);
        getHandle().setAttachedFace(CraftBlock.blockFaceToNotch(face));
    }
}
