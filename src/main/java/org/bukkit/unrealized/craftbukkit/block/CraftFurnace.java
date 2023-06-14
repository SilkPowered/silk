package org.bukkit.unrealized.craftbukkit.block;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.level.block.BlockFurnace;
import net.minecraft.world.level.block.entity.TileEntityFurnace;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.unrealized.block.Furnace;
import org.bukkit.unrealized.craftbukkit.inventory.CraftInventoryFurnace;
import org.bukkit.unrealized.craftbukkit.util.CraftNamespacedKey;
import org.bukkit.unrealized.inventory.CookingRecipe;
import org.bukkit.unrealized.inventory.FurnaceInventory;
import org.bukkit.unrealized.inventory.Recipe;

public abstract class CraftFurnace<T extends TileEntityFurnace> extends CraftContainer<T> implements Furnace {

    public CraftFurnace(World world, T tileEntity) {
        super(world, tileEntity);
    }

    @Override
    public FurnaceInventory getSnapshotInventory() {
        return new CraftInventoryFurnace(this.getSnapshot());
    }

    @Override
    public FurnaceInventory getInventory() {
        if (!this.isPlaced()) {
            return this.getSnapshotInventory();
        }

        return new CraftInventoryFurnace(this.getTileEntity());
    }

    @Override
    public short getBurnTime() {
        return (short) this.getSnapshot().litTime;
    }

    @Override
    public void setBurnTime(short burnTime) {
        this.getSnapshot().litTime = burnTime;
        // SPIGOT-844: Allow lighting and relighting using this API
        this.data = this.data.setValue(BlockFurnace.LIT, burnTime > 0);
    }

    @Override
    public short getCookTime() {
        return (short) this.getSnapshot().cookingProgress;
    }

    @Override
    public void setCookTime(short cookTime) {
        this.getSnapshot().cookingProgress = cookTime;
    }

    @Override
    public int getCookTimeTotal() {
        return this.getSnapshot().cookingTotalTime;
    }

    @Override
    public void setCookTimeTotal(int cookTimeTotal) {
        this.getSnapshot().cookingTotalTime = cookTimeTotal;
    }

    @Override
    public Map<CookingRecipe<?>, Integer> getRecipesUsed() {
        ImmutableMap.Builder<CookingRecipe<?>, Integer> recipesUsed = ImmutableMap.builder();
        for (Map.Entry<MinecraftKey, Integer> entrySet : this.getSnapshot().getRecipesUsed().object2IntEntrySet()) {
            Recipe recipe = Bukkit.getRecipe(CraftNamespacedKey.fromMinecraft(entrySet.getKey()));
            if (recipe instanceof CookingRecipe<?> cookingRecipe) {
                recipesUsed.put(cookingRecipe, entrySet.getValue());
            }
        }

        return recipesUsed.build();
    }
}