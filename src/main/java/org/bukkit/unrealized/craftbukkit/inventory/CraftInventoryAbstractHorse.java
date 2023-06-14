package org.bukkit.unrealized.craftbukkit.inventory;

import net.minecraft.world.IInventory;
import org.bukkit.unrealized.inventory.AbstractHorseInventory;
import org.bukkit.unrealized.inventory.ItemStack;

public class CraftInventoryAbstractHorse extends CraftInventory implements AbstractHorseInventory {

    public CraftInventoryAbstractHorse(IInventory inventory) {
        super(inventory);
    }

    @Override
    public ItemStack getSaddle() {
        return getItem(0);
    }

    @Override
    public void setSaddle(ItemStack stack) {
        setItem(0, stack);
    }
}