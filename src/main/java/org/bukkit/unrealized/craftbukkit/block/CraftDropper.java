package org.bukkit.unrealized.craftbukkit.block;

import net.minecraft.world.level.block.BlockDropper;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TileEntityDropper;
import org.bukkit.unrealized.Material;
import org.bukkit.World;
import org.bukkit.unrealized.block.Block;
import org.bukkit.unrealized.block.Dropper;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.unrealized.craftbukkit.inventory.CraftInventory;
import org.bukkit.unrealized.inventory.Inventory;

public class CraftDropper extends CraftLootable<TileEntityDropper> implements Dropper {

    public CraftDropper(World world, TileEntityDropper tileEntity) {
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
            BlockDropper drop = (BlockDropper) Blocks.DROPPER;

            drop.dispenseFrom(world.getHandle(), this.getPosition());
        }
    }
}
