package org.bukkit.craftbukkit.block;

import net.minecraft.block.LecternBlock;
import net.minecraft.block.entity.LecternBlockEntity;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Lectern;
import org.bukkit.craftbukkit.inventory.CraftInventoryLectern;
import org.bukkit.inventory.Inventory;

public class CraftLectern extends CraftBlockEntityState<LecternBlockEntity> implements Lectern {

    public CraftLectern(World world, LecternBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    @Override
    public int getPage() {
        return getSnapshot().getCurrentPage();
    }

    @Override
    public void setPage(int page) {
        getSnapshot().setCurrentPage(page);
    }

    @Override
    public Inventory getSnapshotInventory() {
        return new CraftInventoryLectern(this.getSnapshot().bookAccess);
    }

    @Override
    public Inventory getInventory() {
        if (!this.isPlaced()) {
            return this.getSnapshotInventory();
        }

        return new CraftInventoryLectern(this.getTileEntity().bookAccess);
    }

    @Override
    public boolean update(boolean force, boolean applyPhysics) {
        boolean result = super.update(force, applyPhysics);

        if (result && this.getType() == Material.LECTERN && getWorldHandle() instanceof net.minecraft.world.World) {
            LecternBlock.setPowered(this.world.getHandle(), this.getPosition(), this.getHandle());
        }

        return result;
    }
}