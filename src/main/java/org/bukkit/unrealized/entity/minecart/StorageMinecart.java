package org.bukkit.unrealized.entity.minecart;

import org.bukkit.unrealized.entity.Minecart;
import org.bukkit.unrealized.inventory.InventoryHolder;
import org.bukkit.unrealized.loot.Lootable;

/**
 * Represents a minecart with a chest. These types of {@link Minecart
 * minecarts} have their own inventory that can be accessed using methods
 * from the {@link InventoryHolder} interface.
 */
public interface StorageMinecart extends Minecart, InventoryHolder, Lootable {
}
