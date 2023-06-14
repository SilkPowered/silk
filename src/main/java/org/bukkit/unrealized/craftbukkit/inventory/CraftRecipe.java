package org.bukkit.unrealized.craftbukkit.inventory;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.item.crafting.RecipeItemStack;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.unrealized.Material;
import org.bukkit.unrealized.craftbukkit.util.CraftMagicNumbers;
import org.bukkit.unrealized.inventory.ItemStack;
import org.bukkit.unrealized.inventory.Recipe;
import org.bukkit.unrealized.inventory.RecipeChoice;
import org.bukkit.unrealized.inventory.recipe.CookingBookCategory;
import org.bukkit.unrealized.inventory.recipe.CraftingBookCategory;

public interface CraftRecipe extends Recipe {

    void addToCraftingManager();

    default RecipeItemStack toNMS(RecipeChoice bukkit, boolean requireNotEmpty) {
        RecipeItemStack stack;

        if (bukkit == null) {
            stack = RecipeItemStack.EMPTY;
        } else if (bukkit instanceof RecipeChoice.MaterialChoice) {
            stack = new RecipeItemStack(((RecipeChoice.MaterialChoice) bukkit).getChoices().stream().map((mat) -> new net.minecraft.world.item.crafting.RecipeItemStack.StackProvider(CraftItemStack.asNMSCopy(new ItemStack(mat)))));
        } else if (bukkit instanceof RecipeChoice.ExactChoice) {
            stack = new RecipeItemStack(((RecipeChoice.ExactChoice) bukkit).getChoices().stream().map((mat) -> new net.minecraft.world.item.crafting.RecipeItemStack.StackProvider(CraftItemStack.asNMSCopy(mat))));
            stack.exact = true;
        } else {
            throw new IllegalArgumentException("Unknown recipe stack instance " + bukkit);
        }

        stack.getItems();
        if (requireNotEmpty) {
            Preconditions.checkArgument(stack.itemStacks.length != 0, "Recipe requires at least one non-air choice");
        }

        return stack;
    }

    public static RecipeChoice toBukkit(RecipeItemStack list) {
        list.getItems();

        if (list.itemStacks.length == 0) {
            return null;
        }

        if (list.exact) {
            List<ItemStack> choices = new ArrayList<>(list.itemStacks.length);
            for (net.minecraft.world.item.ItemStack i : list.itemStacks) {
                choices.add(CraftItemStack.asBukkitCopy(i));
            }

            return new RecipeChoice.ExactChoice(choices);
        } else {

            List<Material> choices = new ArrayList<>(list.itemStacks.length);
            for (net.minecraft.world.item.ItemStack i : list.itemStacks) {
                choices.add(CraftMagicNumbers.getMaterial(i.getItem()));
            }

            return new RecipeChoice.MaterialChoice(choices);
        }
    }

    public static net.minecraft.world.item.crafting.CraftingBookCategory getCategory(CraftingBookCategory bukkit) {
        return net.minecraft.world.item.crafting.CraftingBookCategory.valueOf(bukkit.name());
    }

    public static CraftingBookCategory getCategory(net.minecraft.world.item.crafting.CraftingBookCategory nms) {
        return CraftingBookCategory.valueOf(nms.name());
    }

    public static net.minecraft.world.item.crafting.CookingBookCategory getCategory(CookingBookCategory bukkit) {
        return net.minecraft.world.item.crafting.CookingBookCategory.valueOf(bukkit.name());
    }

    public static CookingBookCategory getCategory(net.minecraft.world.item.crafting.CookingBookCategory nms) {
        return CookingBookCategory.valueOf(nms.name());
    }
}
