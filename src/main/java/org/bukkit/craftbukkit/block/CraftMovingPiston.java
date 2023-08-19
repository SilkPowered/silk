package org.bukkit.craftbukkit.block;

import net.minecraft.block.entity.PistonBlockEntity;
import org.bukkit.World;

public class CraftMovingPiston extends CraftBlockEntityState<PistonBlockEntity> {

    public CraftMovingPiston(World world, PistonBlockEntity tileEntity) {
        super(world, tileEntity);
    }
}
