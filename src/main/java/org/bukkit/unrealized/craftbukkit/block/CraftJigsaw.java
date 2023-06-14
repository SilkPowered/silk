package org.bukkit.unrealized.craftbukkit.block;

import net.minecraft.world.level.block.entity.TileEntityJigsaw;
import org.bukkit.World;
import org.bukkit.unrealized.block.Jigsaw;

public class CraftJigsaw extends CraftBlockEntityState<TileEntityJigsaw> implements Jigsaw {

    public CraftJigsaw(World world, TileEntityJigsaw tileEntity) {
        super(world, tileEntity);
    }
}
