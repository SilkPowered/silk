package org.bukkit.unrealized.craftbukkit.inventory;

import net.minecraft.world.inventory.InventoryMerchant;
import net.minecraft.world.item.trading.IMerchant;
import org.bukkit.unrealized.inventory.Merchant;
import org.bukkit.unrealized.inventory.MerchantInventory;
import org.bukkit.unrealized.inventory.MerchantRecipe;

public class CraftInventoryMerchant extends CraftInventory implements MerchantInventory {

    private final IMerchant merchant;

    public CraftInventoryMerchant(IMerchant merchant, InventoryMerchant inventory) {
        super(inventory);
        this.merchant = merchant;
    }

    @Override
    public int getSelectedRecipeIndex() {
        return getInventory().selectionHint;
    }

    @Override
    public MerchantRecipe getSelectedRecipe() {
        net.minecraft.world.item.trading.MerchantRecipe nmsRecipe = getInventory().getActiveOffer();
        return (nmsRecipe == null) ? null : nmsRecipe.asBukkit();
    }

    @Override
    public InventoryMerchant getInventory() {
        return (InventoryMerchant) inventory;
    }

    @Override
    public Merchant getMerchant() {
        return merchant.getCraftMerchant();
    }
}
