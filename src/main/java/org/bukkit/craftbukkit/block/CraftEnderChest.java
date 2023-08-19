package org.bukkit.craftbukkit.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.EnderChestBlockEntity;
import org.bukkit.World;
import org.bukkit.block.EnderChest;

public class CraftEnderChest extends CraftBlockEntityState<EnderChestBlockEntity> implements EnderChest {

    public CraftEnderChest(World world, EnderChestBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    @Override
    public void open() {
        requirePlaced();
        if (!getTileEntity().openersCounter.opened && getWorldHandle() instanceof net.minecraft.world.World) {
            BlockState block = getTileEntity().getCachedState();
            int openCount = getTileEntity().openersCounter.getViewerCount();

            getTileEntity().openersCounter.onAPIOpen((net.minecraft.world.World) getWorldHandle(), getPosition(), block);
            getTileEntity().openersCounter.openerAPICountChanged((net.minecraft.world.World) getWorldHandle(), getPosition(), block, openCount, openCount + 1);
        }
        getTileEntity().openersCounter.opened = true;
    }

    @Override
    public void close() {
        requirePlaced();
        if (getTileEntity().openersCounter.opened && getWorldHandle() instanceof net.minecraft.world.World) {
            BlockState block = getTileEntity().getCachedState();
            int openCount = getTileEntity().openersCounter.getViewerCount();

            getTileEntity().openersCounter.onAPIClose((net.minecraft.world.World) getWorldHandle(), getPosition(), block);
            getTileEntity().openersCounter.openerAPICountChanged((net.minecraft.world.World) getWorldHandle(), getPosition(), block, openCount, 0);
        }
        getTileEntity().openersCounter.opened = false;
    }
}
