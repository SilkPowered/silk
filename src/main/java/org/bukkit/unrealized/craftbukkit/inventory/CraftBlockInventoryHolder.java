package org.bukkit.unrealized.craftbukkit.inventory;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.IInventory;
import net.minecraft.world.level.GeneratorAccess;
import org.bukkit.unrealized.block.Block;
import org.bukkit.unrealized.craftbukkit.block.CraftBlock;
import org.bukkit.unrealized.inventory.BlockInventoryHolder;
import org.bukkit.unrealized.inventory.Inventory;

public class CraftBlockInventoryHolder implements BlockInventoryHolder {

    private final Block block;
    private final Inventory inventory;

    public CraftBlockInventoryHolder(GeneratorAccess world, BlockPosition pos, IInventory inv) {
        this.block = CraftBlock.at(world, pos);
        this.inventory = new CraftInventory(inv);
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
