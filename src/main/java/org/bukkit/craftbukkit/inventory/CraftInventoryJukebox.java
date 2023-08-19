package org.bukkit.craftbukkit.inventory;

import net.minecraft.inventory.Inventory;
import org.bukkit.block.Jukebox;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.JukeboxInventory;

public class CraftInventoryJukebox extends CraftInventory implements JukeboxInventory {

    public CraftInventoryJukebox(Inventory inventory) {
        super(inventory);
    }

    @Override
    public void setRecord(ItemStack item) {
        if (item == null) {
            inventory.removeStack(0, 0); // Second parameter is unused in TileEntityJukebox
        } else {
            setItem(0, item);
        }
    }

    @Override
    public ItemStack getRecord() {
        return getItem(0);
    }

    @Override
    public Jukebox getHolder() {
        return (Jukebox) inventory.getOwner();
    }
}
