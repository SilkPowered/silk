package org.bukkit.unrealized.craftbukkit.block;

import net.minecraft.world.level.block.entity.TileEntitySmoker;
import org.bukkit.World;
import org.bukkit.unrealized.block.Smoker;

public class CraftSmoker extends CraftFurnace<TileEntitySmoker> implements Smoker {

    public CraftSmoker(World world, TileEntitySmoker tileEntity) {
        super(world, tileEntity);
    }
}
