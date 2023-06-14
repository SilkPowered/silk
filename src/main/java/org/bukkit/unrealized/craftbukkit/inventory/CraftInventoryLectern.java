package org.bukkit.unrealized.craftbukkit.inventory;

import net.minecraft.world.IInventory;
import net.minecraft.world.ITileInventory;
import net.minecraft.world.level.block.entity.TileEntityLectern;
import org.bukkit.unrealized.block.Lectern;
import org.bukkit.unrealized.inventory.LecternInventory;

public class CraftInventoryLectern extends CraftInventory implements LecternInventory {

    public ITileInventory tile;

    public CraftInventoryLectern(IInventory inventory) {
        super(inventory);
        if (inventory instanceof TileEntityLectern.LecternInventory) {
            this.tile = ((TileEntityLectern.LecternInventory) inventory).getLectern();
        }
    }

    @Override
    public Lectern getHolder() {
        return (Lectern) inventory.getOwner();
    }
}
