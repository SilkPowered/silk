package org.bukkit.unrealized.craftbukkit.block;

import net.minecraft.world.level.block.entity.TileEntityComparator;
import org.bukkit.World;
import org.bukkit.unrealized.block.Comparator;

public class CraftComparator extends CraftBlockEntityState<TileEntityComparator> implements Comparator {

    public CraftComparator(World world, TileEntityComparator tileEntity) {
        super(world, tileEntity);
    }
}
