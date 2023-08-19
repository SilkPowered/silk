package org.bukkit.craftbukkit.entity;

import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.util.Identifier;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.craftbukkit.util.CraftNamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.loot.LootTable;

public class CraftChestBoat extends CraftBoat implements org.bukkit.entity.ChestBoat {

    private final Inventory inventory;

    public CraftChestBoat(CraftServer server, ChestBoatEntity entity) {
        super(server, entity);
        inventory = new CraftInventory(entity);
    }

    @Override
    public ChestBoatEntity getHandle() {
        return (ChestBoatEntity) entity;
    }

    @Override
    public String toString() {
        return "CraftChestBoat";
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void setLootTable(LootTable table) {
        setLootTable(table, getSeed());
    }

    @Override
    public LootTable getLootTable() {
        Identifier nmsTable = getHandle().A();
        if (nmsTable == null) {
            return null; // return empty loot table?
        }

        NamespacedKey key = CraftNamespacedKey.fromMinecraft(nmsTable);
        return Bukkit.getLootTable(key);
    }

    @Override
    public void setSeed(long seed) {
        setLootTable(getLootTable(), seed);
    }

    @Override
    public long getSeed() {
        return getHandle().C();
    }

    private void setLootTable(LootTable table, long seed) {
        Identifier newKey = (table == null) ? null : CraftNamespacedKey.toMinecraft(table.getKey());
        getHandle().a(newKey);
        getHandle().a(seed);
    }
}
