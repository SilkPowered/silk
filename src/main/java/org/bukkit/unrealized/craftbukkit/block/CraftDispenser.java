package org.bukkit.unrealized.craftbukkit.block;

import net.minecraft.world.level.block.BlockDispenser;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TileEntityDispenser;
import org.bukkit.unrealized.Material;
import org.bukkit.World;
import org.bukkit.unrealized.block.Block;
import org.bukkit.unrealized.block.Dispenser;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.unrealized.craftbukkit.inventory.CraftInventory;
import org.bukkit.unrealized.craftbukkit.projectiles.CraftBlockProjectileSource;
import org.bukkit.unrealized.inventory.Inventory;
import org.bukkit.unrealized.projectiles.BlockProjectileSource;

public class CraftDispenser extends CraftLootable<TileEntityDispenser> implements Dispenser {

    public CraftDispenser(World world, TileEntityDispenser tileEntity) {
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
    public BlockProjectileSource getBlockProjectileSource() {
        Block block = getBlock();

        if (block.getType() != Material.DISPENSER) {
            return null;
        }

        return new CraftBlockProjectileSource((TileEntityDispenser) this.getTileEntityFromWorld());
    }

    @Override
    public boolean dispense() {
        ensureNoWorldGeneration();
        Block block = getBlock();
        if (block.getType() == Material.DISPENSER) {
            CraftWorld world = (CraftWorld) this.getWorld();
            BlockDispenser dispense = (BlockDispenser) Blocks.DISPENSER;

            dispense.dispenseFrom(world.getHandle(), this.getPosition());
            return true;
        } else {
            return false;
        }
    }
}
