package org.bukkit.unrealized.craftbukkit.inventory;

import net.minecraft.server.MinecraftServer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.unrealized.NamespacedKey;
import org.bukkit.unrealized.craftbukkit.util.CraftNamespacedKey;
import org.bukkit.unrealized.inventory.ItemStack;
import org.bukkit.unrealized.inventory.RecipeChoice;
import org.bukkit.unrealized.inventory.SmithingTransformRecipe;

public class CraftSmithingTransformRecipe extends SmithingTransformRecipe implements CraftRecipe {
    public CraftSmithingTransformRecipe(NamespacedKey key, ItemStack result, RecipeChoice template, RecipeChoice base, RecipeChoice addition) {
        super(key, result, template, base, addition);
    }

    public static CraftSmithingTransformRecipe fromBukkitRecipe(SmithingTransformRecipe recipe) {
        if (recipe instanceof CraftSmithingTransformRecipe) {
            return (CraftSmithingTransformRecipe) recipe;
        }
        CraftSmithingTransformRecipe ret = new CraftSmithingTransformRecipe(recipe.getKey(), recipe.getResult(), recipe.getTemplate(), recipe.getBase(), recipe.getAddition());
        return ret;
    }

    @Override
    public void addToCraftingManager() {
        ItemStack result = this.getResult();

        MinecraftServer.getServer().getRecipeManager().addRecipe(new net.minecraft.world.item.crafting.SmithingTransformRecipe(CraftNamespacedKey.toMinecraft(this.getKey()), toNMS(this.getTemplate(), true), toNMS(this.getBase(), true), toNMS(this.getAddition(), true), CraftItemStack.asNMSCopy(result)));
    }
}
