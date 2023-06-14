package org.bukkit.unrealized.craftbukkit.inventory;

import net.minecraft.world.IInventory;
import org.bukkit.unrealized.block.Jukebox;
import org.bukkit.unrealized.inventory.ItemStack;
import org.bukkit.unrealized.inventory.JukeboxInventory;

public class CraftInventoryJukebox extends CraftInventory implements JukeboxInventory {

    public CraftInventoryJukebox(IInventory inventory) {
        super(inventory);
    }

    @Override
    public void setRecord(ItemStack item) {
        if (item == null) {
            inventory.removeItem(0, 0); // Second parameter is unused in TileEntityJukebox
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
