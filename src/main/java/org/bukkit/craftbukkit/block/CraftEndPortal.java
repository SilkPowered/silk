package org.bukkit.craftbukkit.block;

import net.minecraft.block.entity.EndPortalBlockEntity;
import org.bukkit.World;

public class CraftEndPortal extends CraftBlockEntityState<EndPortalBlockEntity> {

    public CraftEndPortal(World world, EndPortalBlockEntity tileEntity) {
        super(world, tileEntity);
    }
}
