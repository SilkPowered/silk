package org.bukkit.craftbukkit.block;

import net.minecraft.block.ChiseledBookshelfBlock;
import net.minecraft.block.entity.ChiseledBookshelfBlockEntity;
import net.minecraft.util.math.Vec2f;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.block.ChiseledBookshelf;
import org.bukkit.block.data.Directional;
import org.bukkit.craftbukkit.inventory.CraftInventoryChiseledBookshelf;
import org.bukkit.inventory.ChiseledBookshelfInventory;
import org.bukkit.util.Vector;

public class CraftChiseledBookshelf extends CraftBlockEntityState<ChiseledBookshelfBlockEntity> implements ChiseledBookshelf {

    public CraftChiseledBookshelf(World world, ChiseledBookshelfBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    @Override
    public int getLastInteractedSlot() {
        return getSnapshot().getLastInteractedSlot();
    }

    @Override
    public void setLastInteractedSlot(int lastInteractedSlot) {
        getSnapshot().lastInteractedSlot = lastInteractedSlot;
    }

    @Override
    public ChiseledBookshelfInventory getSnapshotInventory() {
        return new CraftInventoryChiseledBookshelf(this.getSnapshot());
    }

    @Override
    public ChiseledBookshelfInventory getInventory() {
        if (!this.isPlaced()) {
            return this.getSnapshotInventory();
        }

        return new CraftInventoryChiseledBookshelf(this.getTileEntity());
    }

    @Override
    public int getSlot(Vector clickVector) {
        BlockFace facing = ((Directional) this.getBlockData()).getFacing();

        Vec2f faceVector;
        switch (facing) {
        case NORTH:
            faceVector = new Vec2f((float) (1.0f - clickVector.getX()), (float) clickVector.getY());
            break;
        case SOUTH:
            faceVector = new Vec2f((float) clickVector.getX(), (float) clickVector.getY());
            break;
        case WEST:
            faceVector = new Vec2f((float) clickVector.getZ(), (float) clickVector.getY());
            break;
        case EAST:
            faceVector = new Vec2f((float) (1f - clickVector.getZ()), (float) clickVector.getY());
            break;
        case DOWN:
        case UP:
        default:
            return -1;
        }

        return ChiseledBookshelfBlock.getHitSlot(faceVector);
    }
}
