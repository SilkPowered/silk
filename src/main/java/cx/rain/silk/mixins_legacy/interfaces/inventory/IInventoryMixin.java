package cx.rain.silk.mixins_legacy.interfaces.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;

import java.util.List;

public interface IInventoryMixin {
    List<ItemStack> getContents();

    void onOpen(CraftHumanEntity who);

    void onClose(CraftHumanEntity who);

    List<HumanEntity> getViewers();

    InventoryHolder getOwner();

    int getMaxStackSize();

    void setMaxStackSize(int size);

    org.bukkit.Location getLocation();

    default Recipe getCurrentRecipe() {
        return null;
    }

    default void setCurrentRecipe(Recipe recipe) {
    }

    int MAX_STACK = 64;
}
