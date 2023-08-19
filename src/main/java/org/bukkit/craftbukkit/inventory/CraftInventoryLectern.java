package org.bukkit.craftbukkit.inventory;

import net.minecraft.block.entity.LecternBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import org.bukkit.block.Lectern;
import org.bukkit.inventory.LecternInventory;

public class CraftInventoryLectern extends CraftInventory implements LecternInventory {

    public NamedScreenHandlerFactory tile;

    public CraftInventoryLectern(Inventory inventory) {
        super(inventory);
        if (inventory instanceof LecternBlockEntity.LecternInventory) {
            this.tile = ((LecternBlockEntity.LecternInventory) inventory).getLectern();
        }
    }

    @Override
    public Lectern getHolder() {
        return (Lectern) inventory.getOwner();
    }
}
