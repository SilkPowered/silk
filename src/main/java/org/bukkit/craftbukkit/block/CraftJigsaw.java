package org.bukkit.craftbukkit.block;

import net.minecraft.block.entity.JigsawBlockEntity;
import org.bukkit.World;
import org.bukkit.block.Jigsaw;

public class CraftJigsaw extends CraftBlockEntityState<JigsawBlockEntity> implements Jigsaw {

    public CraftJigsaw(World world, JigsawBlockEntity tileEntity) {
        super(world, tileEntity);
    }
}
