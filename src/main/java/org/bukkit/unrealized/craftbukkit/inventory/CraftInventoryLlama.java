package org.bukkit.unrealized.craftbukkit.inventory;

import net.minecraft.world.IInventory;
import org.bukkit.unrealized.inventory.ItemStack;
import org.bukkit.unrealized.inventory.LlamaInventory;

public class CraftInventoryLlama extends CraftInventoryAbstractHorse implements LlamaInventory {

    public CraftInventoryLlama(IInventory inventory) {
        super(inventory);
    }

    @Override
    public ItemStack getDecor() {
        return getItem(1);
    }

    @Override
    public void setDecor(ItemStack stack) {
        setItem(1, stack);
    }
}
