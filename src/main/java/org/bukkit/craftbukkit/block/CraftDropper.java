package org.bukkit.craftbukkit.block;

import com.google.common.base.Preconditions;
import net.minecraft.block.Blocks;
import net.minecraft.block.DropperBlock;
import net.minecraft.block.entity.DropperBlockEntity;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Dropper;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.inventory.Inventory;

public class CraftDropper extends CraftLootable<DropperBlockEntity> implements Dropper {

    public CraftDropper(World world, DropperBlockEntity tileEntity) {
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
    public void drop() {
        ensureNoWorldGeneration();
        Block block = getBlock();
        if (block.getType() == Material.DROPPER) {
            CraftWorld world = (CraftWorld) this.getWorld();
            DropperBlock drop = (DropperBlock) Blocks.DROPPER;

            drop.dispenseFrom(world.getHandle(), this.getPosition());
        }
    }
}
