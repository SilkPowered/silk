package org.bukkit.craftbukkit.inventory;

import com.google.common.base.Preconditions;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.AnvilScreenHandler;
import org.bukkit.Location;
import org.bukkit.inventory.AnvilInventory;

public class CraftInventoryAnvil extends CraftResultInventory implements AnvilInventory {

    private final Location location;
    private final AnvilScreenHandler container;

    public CraftInventoryAnvil(Location location, Inventory inventory, Inventory resultInventory, AnvilScreenHandler container) {
        super(inventory, resultInventory);
        this.location = location;
        this.container = container;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public String getRenameText() {
        return container.itemName;
    }

    @Override
    public int getRepairCostAmount() {
        return container.repairItemCountCost;
    }

    @Override
    public void setRepairCostAmount(int amount) {
        container.repairItemCountCost = amount;
    }

    @Override
    public int getRepairCost() {
        return container.cost.get();
    }

    @Override
    public void setRepairCost(int i) {
        container.cost.set(i);
    }

    @Override
    public int getMaximumRepairCost() {
        return container.maximumRepairCost;
    }

    @Override
    public void setMaximumRepairCost(int levels) {
        Preconditions.checkArgument(levels >= 0, "Maximum repair cost must be positive (or 0)");
        container.maximumRepairCost = levels;
    }
}
