package org.bukkit.craftbukkit.inventory;

import com.google.common.base.Preconditions;
import java.util.Arrays;
import java.util.List;
import net.minecraft.inventory.Inventory;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class CraftInventoryCrafting extends CraftInventory implements CraftingInventory {
    private final Inventory resultInventory;

    public CraftInventoryCrafting(Inventory inventory, Inventory resultInventory) {
        super(inventory);
        this.resultInventory = resultInventory;
    }

    public Inventory getResultInventory() {
        return resultInventory;
    }

    public Inventory getMatrixInventory() {
        return inventory;
    }

    @Override
    public int getSize() {
        return getResultInventory().getContainerSize() + getMatrixInventory().getContainerSize();
    }

    @Override
    public void setContents(ItemStack[] items) {
        Preconditions.checkArgument(items.length <= getSize(), "Invalid inventory size (%s); expected %s or less", items.length, getSize());
        setContents(items[0], Arrays.copyOfRange(items, 1, items.length));
    }

    @Override
    public ItemStack[] getContents() {
        ItemStack[] items = new ItemStack[getSize()];
        List<net.minecraft.item.ItemStack> mcResultItems = getResultInventory().getContents();

        int i = 0;
        for (i = 0; i < mcResultItems.size(); i++) {
            items[i] = CraftItemStack.asCraftMirror(mcResultItems.get(i));
        }

        List<net.minecraft.item.ItemStack> mcItems = getMatrixInventory().getContents();

        for (int j = 0; j < mcItems.size(); j++) {
            items[i + j] = CraftItemStack.asCraftMirror(mcItems.get(j));
        }

        return items;
    }

    public void setContents(ItemStack result, ItemStack[] contents) {
        setResult(result);
        setMatrix(contents);
    }

    @Override
    public CraftItemStack getItem(int index) {
        if (index < getResultInventory().getContainerSize()) {
            net.minecraft.item.ItemStack item = getResultInventory().getItem(index);
            return item.isEmpty() ? null : CraftItemStack.asCraftMirror(item);
        } else {
            net.minecraft.item.ItemStack item = getMatrixInventory().getItem(index - getResultInventory().getContainerSize());
            return item.isEmpty() ? null : CraftItemStack.asCraftMirror(item);
        }
    }

    @Override
    public void setItem(int index, ItemStack item) {
        if (index < getResultInventory().getContainerSize()) {
            getResultInventory().setItem(index, CraftItemStack.asNMSCopy(item));
        } else {
            getMatrixInventory().setItem((index - getResultInventory().getContainerSize()), CraftItemStack.asNMSCopy(item));
        }
    }

    @Override
    public ItemStack[] getMatrix() {
        List<net.minecraft.item.ItemStack> matrix = getMatrixInventory().getContents();

        return asCraftMirror(matrix);
    }

    @Override
    public ItemStack getResult() {
        net.minecraft.item.ItemStack item = getResultInventory().getItem(0);
        if (!item.isEmpty()) return CraftItemStack.asCraftMirror(item);
        return null;
    }

    @Override
    public void setMatrix(ItemStack[] contents) {
        Preconditions.checkArgument(contents.length <= getMatrixInventory().getContainerSize(), "Invalid inventory size (%s); expected %s or less", contents.length, getMatrixInventory().getContainerSize());

        for (int i = 0; i < getMatrixInventory().getContainerSize(); i++) {
            if (i < contents.length) {
                getMatrixInventory().setItem(i, CraftItemStack.asNMSCopy(contents[i]));
            } else {
                getMatrixInventory().setItem(i, net.minecraft.item.ItemStack.EMPTY);
            }
        }
    }

    @Override
    public void setResult(ItemStack item) {
        List<net.minecraft.item.ItemStack> contents = getResultInventory().getContents();
        contents.set(0, CraftItemStack.asNMSCopy(item));
    }

    @Override
    public Recipe getRecipe() {
        net.minecraft.recipe.Recipe recipe = getInventory().getCurrentRecipe();
        return recipe == null ? null : recipe.toBukkitRecipe();
    }
}
