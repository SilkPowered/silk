package org.bukkit.unrealized.craftbukkit.inventory;

import net.minecraft.server.MinecraftServer;
import org.bukkit.unrealized.NamespacedKey;
import org.bukkit.unrealized.craftbukkit.util.CraftNamespacedKey;
import org.bukkit.unrealized.inventory.RecipeChoice;
import org.bukkit.unrealized.inventory.SmithingTrimRecipe;

public class CraftSmithingTrimRecipe extends SmithingTrimRecipe implements CraftRecipe {

    public CraftSmithingTrimRecipe(NamespacedKey key, RecipeChoice template, RecipeChoice base, RecipeChoice addition) {
        super(key, template, base, addition);
    }

    public static CraftSmithingTrimRecipe fromBukkitRecipe(SmithingTrimRecipe recipe) {
        if (recipe instanceof CraftSmithingTrimRecipe) {
            return (CraftSmithingTrimRecipe) recipe;
        }
        CraftSmithingTrimRecipe ret = new CraftSmithingTrimRecipe(recipe.getKey(), recipe.getTemplate(), recipe.getBase(), recipe.getAddition());
        return ret;
    }

    @Override
    public void addToCraftingManager() {
        MinecraftServer.getServer().getRecipeManager().addRecipe(new net.minecraft.world.item.crafting.SmithingTrimRecipe(CraftNamespacedKey.toMinecraft(this.getKey()), toNMS(this.getTemplate(), true), toNMS(this.getBase(), true), toNMS(this.getAddition(), true)));
    }
}
