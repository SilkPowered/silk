package org.bukkit.unrealized.craftbukkit.block;

import net.minecraft.world.level.block.entity.CalibratedSculkSensorBlockEntity;
import org.bukkit.World;
import org.bukkit.unrealized.block.CalibratedSculkSensor;

public class CraftCalibratedSculkSensor extends CraftSculkSensor<CalibratedSculkSensorBlockEntity> implements CalibratedSculkSensor {

    public CraftCalibratedSculkSensor(World world, CalibratedSculkSensorBlockEntity tileEntity) {
        super(world, tileEntity);
    }
}
