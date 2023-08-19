package org.bukkit.craftbukkit.inventory;

import net.minecraft.block.entity.ChiseledBookshelfBlockEntity;
import org.bukkit.block.ChiseledBookshelf;
import org.bukkit.inventory.ChiseledBookshelfInventory;
import org.bukkit.inventory.ItemStack;

public class CraftInventoryChiseledBookshelf extends CraftInventory implements ChiseledBookshelfInventory {

    public CraftInventoryChiseledBookshelf(ChiseledBookshelfBlockEntity inventory) {
        super(inventory);

    }

    @Override
    public void setItem(int index, ItemStack item) {
        net.minecraft.item.ItemStack nms = CraftItemStack.asNMSCopy(item);

        if (nms.isEmpty()) {
            this.getInventory().removeItemNoUpdate(index);
        } else {
            this.getInventory().setItem(index, nms);
        }
    }

    @Override
    public ChiseledBookshelf getHolder() {
        return (ChiseledBookshelf) inventory.getOwner();
    }
}
