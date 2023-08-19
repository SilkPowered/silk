package org.bukkit.craftbukkit.block;

import net.minecraft.block.entity.BlastFurnaceBlockEntity;
import org.bukkit.World;
import org.bukkit.block.BlastFurnace;

public class CraftBlastFurnace extends CraftFurnace<BlastFurnaceBlockEntity> implements BlastFurnace {

    public CraftBlastFurnace(World world, BlastFurnaceBlockEntity tileEntity) {
        super(world, tileEntity);
    }
}
