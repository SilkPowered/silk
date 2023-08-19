package org.bukkit.craftbukkit.block;

import net.minecraft.block.entity.EnchantingTableBlockEntity;
import org.bukkit.World;
import org.bukkit.block.EnchantingTable;
import org.bukkit.craftbukkit.util.CraftChatMessage;

public class CraftEnchantingTable extends CraftBlockEntityState<EnchantingTableBlockEntity> implements EnchantingTable {

    public CraftEnchantingTable(World world, EnchantingTableBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    @Override
    public String getCustomName() {
        EnchantingTableBlockEntity enchant = this.getSnapshot();
        return enchant.hasCustomName() ? CraftChatMessage.fromComponent(enchant.ab()) : null;
    }

    @Override
    public void setCustomName(String name) {
        this.getSnapshot().setCustomName(CraftChatMessage.fromStringOrNull(name));
    }

    @Override
    public void applyTo(EnchantingTableBlockEntity enchantingTable) {
        super.applyTo(enchantingTable);

        if (!this.getSnapshot().hasCustomName()) {
            enchantingTable.setCustomName(null);
        }
    }
}
