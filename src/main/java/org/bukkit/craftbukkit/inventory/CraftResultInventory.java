package org.bukkit.craftbukkit.inventory;

import net.minecraft.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CraftResultInventory extends CraftInventory {

    private final Inventory resultInventory;

    public CraftResultInventory(Inventory inventory, Inventory resultInventory) {
        super(inventory);
        this.resultInventory = resultInventory;
    }

    public Inventory getResultInventory() {
        return resultInventory;
    }

    public Inventory getIngredientsInventory() {
        return inventory;
    }

    @Override
    public ItemStack getItem(int slot) {
        if (slot < getIngredientsInventory().getContainerSize()) {
            net.minecraft.item.ItemStack item = getIngredientsInventory().getItem(slot);
            return item.isEmpty() ? null : CraftItemStack.asCraftMirror(item);
        } else {
            net.minecraft.item.ItemStack item = getResultInventory().getItem(slot - getIngredientsInventory().getContainerSize());
            return item.isEmpty() ? null : CraftItemStack.asCraftMirror(item);
        }
    }

    @Override
    public void setItem(int index, ItemStack item) {
        if (index < getIngredientsInventory().getContainerSize()) {
            getIngredientsInventory().setItem(index, CraftItemStack.asNMSCopy(item));
        } else {
            getResultInventory().setItem((index - getIngredientsInventory().getContainerSize()), CraftItemStack.asNMSCopy(item));
        }
    }

    @Override
    public int getSize() {
        return getResultInventory().getContainerSize() + getIngredientsInventory().getContainerSize();
    }
}
