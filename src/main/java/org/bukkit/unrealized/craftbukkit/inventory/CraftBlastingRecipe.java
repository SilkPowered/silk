package org.bukkit.unrealized.craftbukkit.inventory;

import net.minecraft.server.MinecraftServer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.unrealized.NamespacedKey;
import org.bukkit.unrealized.craftbukkit.util.CraftNamespacedKey;
import org.bukkit.unrealized.inventory.BlastingRecipe;
import org.bukkit.unrealized.inventory.ItemStack;
import org.bukkit.unrealized.inventory.RecipeChoice;

public class CraftBlastingRecipe extends BlastingRecipe implements CraftRecipe {
    public CraftBlastingRecipe(NamespacedKey key, ItemStack result, RecipeChoice source, float experience, int cookingTime) {
        super(key, result, source, experience, cookingTime);
    }

    public static CraftBlastingRecipe fromBukkitRecipe(BlastingRecipe recipe) {
        if (recipe instanceof CraftBlastingRecipe) {
            return (CraftBlastingRecipe) recipe;
        }
        CraftBlastingRecipe ret = new CraftBlastingRecipe(recipe.getKey(), recipe.getResult(), recipe.getInputChoice(), recipe.getExperience(), recipe.getCookingTime());
        ret.setGroup(recipe.getGroup());
        ret.setCategory(recipe.getCategory());
        return ret;
    }

    @Override
    public void addToCraftingManager() {
        ItemStack result = this.getResult();

        MinecraftServer.getServer().getRecipeManager().addRecipe(new net.minecraft.world.item.crafting.RecipeBlasting(CraftNamespacedKey.toMinecraft(this.getKey()), this.getGroup(), CraftRecipe.getCategory(this.getCategory()), toNMS(this.getInputChoice(), true), CraftItemStack.asNMSCopy(result), getExperience(), getCookingTime()));
    }
}
