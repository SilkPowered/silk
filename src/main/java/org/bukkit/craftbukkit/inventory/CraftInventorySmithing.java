package org.bukkit.craftbukkit.inventory;

import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.SmithingInventory;

public class CraftInventorySmithing extends CraftResultInventory implements SmithingInventory {

    private final Location location;

    public CraftInventorySmithing(Location location, Inventory inventory, CraftingResultInventory resultInventory) {
        super(inventory, resultInventory);
        this.location = location;
    }

    @Override
    public CraftingResultInventory getResultInventory() {
        return (CraftingResultInventory) super.getResultInventory();
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public ItemStack getResult() {
        return getItem(3);
    }

    @Override
    public void setResult(ItemStack item) {
        setItem(3, item);
    }

    @Override
    public Recipe getRecipe() {
        net.minecraft.recipe.Recipe recipe = getResultInventory().d();
        return (recipe == null) ? null : recipe.toBukkitRecipe();
    }
}
