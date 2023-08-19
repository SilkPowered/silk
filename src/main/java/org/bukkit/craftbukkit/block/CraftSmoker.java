package org.bukkit.craftbukkit.block;

import net.minecraft.block.entity.SmokerBlockEntity;
import org.bukkit.World;
import org.bukkit.block.Smoker;

public class CraftSmoker extends CraftFurnace<SmokerBlockEntity> implements Smoker {

    public CraftSmoker(World world, SmokerBlockEntity tileEntity) {
        super(world, tileEntity);
    }
}
