package org.bukkit.craftbukkit.block;

import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.sound.SoundEvents;
import org.bukkit.World;
import org.bukkit.block.Barrel;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.inventory.Inventory;

public class CraftBarrel extends CraftLootable<BarrelBlockEntity> implements Barrel {

    public CraftBarrel(World world, BarrelBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    @Override
    public Inventory getSnapshotInventory() {
        return new CraftInventory(this.getSnapshot());
    }

    @Override
    public Inventory getInventory() {
        if (!this.isPlaced()) {
            return this.getSnapshotInventory();
        }

        return new CraftInventory(this.getTileEntity());
    }

    @Override
    public void open() {
        requirePlaced();
        if (!getTileEntity().openersCounter.opened) {
            BlockState blockData = getTileEntity().getBlockState();
            boolean open = blockData.getValue(BarrelBlock.OPEN);

            if (!open) {
                getTileEntity().updateBlockState(blockData, true);
                if (getWorldHandle() instanceof net.minecraft.world.World) {
                    getTileEntity().playSound(blockData, SoundEvents.BARREL_OPEN);
                }
            }
        }
        getTileEntity().openersCounter.opened = true;
    }

    @Override
    public void close() {
        requirePlaced();
        if (getTileEntity().openersCounter.opened) {
            BlockState blockData = getTileEntity().getBlockState();
            getTileEntity().updateBlockState(blockData, false);
            if (getWorldHandle() instanceof net.minecraft.world.World) {
                getTileEntity().playSound(blockData, SoundEvents.BARREL_CLOSE);
            }
        }
        getTileEntity().openersCounter.opened = false;
    }
}
