package org.bukkit.unrealized.craftbukkit.inventory;

import net.minecraft.server.MinecraftServer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.unrealized.NamespacedKey;
import org.bukkit.unrealized.craftbukkit.util.CraftNamespacedKey;
import org.bukkit.unrealized.inventory.ItemStack;
import org.bukkit.unrealized.inventory.RecipeChoice;
import org.bukkit.unrealized.inventory.SmokingRecipe;

public class CraftSmokingRecipe extends SmokingRecipe implements CraftRecipe {
    public CraftSmokingRecipe(NamespacedKey key, ItemStack result, RecipeChoice source, float experience, int cookingTime) {
        super(key, result, source, experience, cookingTime);
    }

    public static CraftSmokingRecipe fromBukkitRecipe(SmokingRecipe recipe) {
        if (recipe instanceof CraftSmokingRecipe) {
            return (CraftSmokingRecipe) recipe;
        }
        CraftSmokingRecipe ret = new CraftSmokingRecipe(recipe.getKey(), recipe.getResult(), recipe.getInputChoice(), recipe.getExperience(), recipe.getCookingTime());
        ret.setGroup(recipe.getGroup());
        ret.setCategory(recipe.getCategory());
        return ret;
    }

    @Override
    public void addToCraftingManager() {
        ItemStack result = this.getResult();

        MinecraftServer.getServer().getRecipeManager().addRecipe(new net.minecraft.world.item.crafting.RecipeSmoking(CraftNamespacedKey.toMinecraft(this.getKey()), this.getGroup(), CraftRecipe.getCategory(this.getCategory()), toNMS(this.getInputChoice(), true), CraftItemStack.asNMSCopy(result), getExperience(), getCookingTime()));
    }
}
