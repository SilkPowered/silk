package org.bukkit.craftbukkit.inventory;

import com.google.common.base.Preconditions;
import net.minecraft.block.ChestBlock;
import net.minecraft.inventory.DoubleInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import org.bukkit.Location;
import org.bukkit.block.DoubleChest;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CraftInventoryDoubleChest extends CraftInventory implements DoubleChestInventory {
    public NamedScreenHandlerFactory tile;
    private final CraftInventory left;
    private final CraftInventory right;

    public CraftInventoryDoubleChest(ChestBlock.DoubleInventory block) {
        super(block.inventorylargechest);
        this.tile = block;
        this.left = new CraftInventory(block.inventorylargechest.container1);
        this.right = new CraftInventory(block.inventorylargechest.container2);
    }

    public CraftInventoryDoubleChest(DoubleInventory largeChest) {
        super(largeChest);
        if (largeChest.container1 instanceof DoubleInventory) {
            left = new CraftInventoryDoubleChest((DoubleInventory) largeChest.container1);
        } else {
            left = new CraftInventory(largeChest.container1);
        }
        if (largeChest.container2 instanceof DoubleInventory) {
            right = new CraftInventoryDoubleChest((DoubleInventory) largeChest.container2);
        } else {
            right = new CraftInventory(largeChest.container2);
        }
    }

    @Override
    public Inventory getLeftSide() {
        return left;
    }

    @Override
    public Inventory getRightSide() {
        return right;
    }

    @Override
    public void setContents(ItemStack[] items) {
        Preconditions.checkArgument(items.length <= getInventory().size(), "Invalid inventory size (%s); expected %s or less", items.length, getInventory().size());
        ItemStack[] leftItems = new ItemStack[left.getSize()], rightItems = new ItemStack[right.getSize()];
        System.arraycopy(items, 0, leftItems, 0, Math.min(left.getSize(), items.length));
        left.setContents(leftItems);
        if (items.length >= left.getSize()) {
            System.arraycopy(items, left.getSize(), rightItems, 0, Math.min(right.getSize(), items.length - left.getSize()));
            right.setContents(rightItems);
        }
    }

    @Override
    public DoubleChest getHolder() {
        return new DoubleChest(this);
    }

    @Override
    public Location getLocation() {
        return getLeftSide().getLocation().add(getRightSide().getLocation()).multiply(0.5);
    }
}
