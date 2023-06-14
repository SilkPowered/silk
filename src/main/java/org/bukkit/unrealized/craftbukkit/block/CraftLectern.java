package org.bukkit.unrealized.craftbukkit.block;

import net.minecraft.world.level.block.BlockLectern;
import net.minecraft.world.level.block.entity.TileEntityLectern;
import org.bukkit.unrealized.Material;
import org.bukkit.World;
import org.bukkit.unrealized.block.Lectern;
import org.bukkit.unrealized.craftbukkit.inventory.CraftInventoryLectern;
import org.bukkit.unrealized.inventory.Inventory;

public class CraftLectern extends CraftBlockEntityState<TileEntityLectern> implements Lectern {

    public CraftLectern(World world, TileEntityLectern tileEntity) {
        super(world, tileEntity);
    }

    @Override
    public int getPage() {
        return getSnapshot().getPage();
    }

    @Override
    public void setPage(int page) {
        getSnapshot().setPage(page);
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

        if (result && this.getType() == Material.LECTERN && getWorldHandle() instanceof net.minecraft.world.level.World) {
            BlockLectern.signalPageChange(this.world.getHandle(), this.getPosition(), this.getHandle());
        }

        return result;
    }
}