package org.bukkit.unrealized.craftbukkit.entity;

import java.util.List;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.entity.npc.EntityVillagerAbstract;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.craftbukkit.inventory.CraftInventory;
import org.bukkit.unrealized.craftbukkit.inventory.CraftMerchant;
import org.bukkit.unrealized.entity.AbstractVillager;
import org.bukkit.unrealized.entity.HumanEntity;
import org.bukkit.unrealized.inventory.Inventory;
import org.bukkit.unrealized.inventory.InventoryHolder;
import org.bukkit.unrealized.inventory.MerchantRecipe;

public class CraftAbstractVillager extends CraftAgeable implements AbstractVillager, InventoryHolder {

    public CraftAbstractVillager(CraftServer server, EntityVillagerAbstract entity) {
        super(server, entity);
    }

    @Override
    public EntityVillagerAbstract getHandle() {
        return (EntityVillager) entity;
    }

    @Override
    public String toString() {
        return "CraftAbstractVillager";
    }

    @Override
    public Inventory getInventory() {
        return new CraftInventory(getHandle().getInventory());
    }

    private CraftMerchant getMerchant() {
        return getHandle().getCraftMerchant();
    }

    @Override
    public List<MerchantRecipe> getRecipes() {
        return getMerchant().getRecipes();
    }

    @Override
    public void setRecipes(List<MerchantRecipe> recipes) {
        this.getMerchant().setRecipes(recipes);
    }

    @Override
    public MerchantRecipe getRecipe(int i) {
        return getMerchant().getRecipe(i);
    }

    @Override
    public void setRecipe(int i, MerchantRecipe merchantRecipe) {
        getMerchant().setRecipe(i, merchantRecipe);
    }

    @Override
    public int getRecipeCount() {
        return getMerchant().getRecipeCount();
    }

    @Override
    public boolean isTrading() {
        return getTrader() != null;
    }

    @Override
    public HumanEntity getTrader() {
        return getMerchant().getTrader();
    }
}