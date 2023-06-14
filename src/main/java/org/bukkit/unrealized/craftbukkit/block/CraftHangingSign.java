package org.bukkit.unrealized.craftbukkit.block;

import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import org.bukkit.World;
import org.bukkit.unrealized.block.HangingSign;

public class CraftHangingSign extends CraftSign<HangingSignBlockEntity> implements HangingSign {

    public CraftHangingSign(World world, HangingSignBlockEntity tileEntity) {
        super(world, tileEntity);
    }
}
