package org.bukkit.unrealized.craftbukkit.inventory;

import net.minecraft.server.MinecraftServer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.unrealized.NamespacedKey;
import org.bukkit.unrealized.craftbukkit.util.CraftNamespacedKey;
import org.bukkit.unrealized.inventory.ItemStack;
import org.bukkit.unrealized.inventory.RecipeChoice;
import org.bukkit.unrealized.inventory.StonecuttingRecipe;

public class CraftStonecuttingRecipe extends StonecuttingRecipe implements CraftRecipe {
    public CraftStonecuttingRecipe(NamespacedKey key, ItemStack result, RecipeChoice source) {
        super(key, result, source);
    }

    public static CraftStonecuttingRecipe fromBukkitRecipe(StonecuttingRecipe recipe) {
        if (recipe instanceof CraftStonecuttingRecipe) {
            return (CraftStonecuttingRecipe) recipe;
        }
        CraftStonecuttingRecipe ret = new CraftStonecuttingRecipe(recipe.getKey(), recipe.getResult(), recipe.getInputChoice());
        ret.setGroup(recipe.getGroup());
        return ret;
    }

    @Override
    public void addToCraftingManager() {
        ItemStack result = this.getResult();

        MinecraftServer.getServer().getRecipeManager().addRecipe(new net.minecraft.world.item.crafting.RecipeStonecutting(CraftNamespacedKey.toMinecraft(this.getKey()), this.getGroup(), toNMS(this.getInputChoice(), true), CraftItemStack.asNMSCopy(result)));
    }
}
