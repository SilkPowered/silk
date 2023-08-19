package org.bukkit.craftbukkit.block;

import net.minecraft.block.entity.ComparatorBlockEntity;
import org.bukkit.World;
import org.bukkit.block.Comparator;

public class CraftComparator extends CraftBlockEntityState<ComparatorBlockEntity> implements Comparator {

    public CraftComparator(World world, ComparatorBlockEntity tileEntity) {
        super(world, tileEntity);
    }
}
