package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.monster.EntityEnderman;
import net.minecraft.world.level.block.state.IBlockData;
import org.bukkit.unrealized.Material;
import org.bukkit.unrealized.block.data.BlockData;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;
import org.bukkit.unrealized.craftbukkit.util.CraftMagicNumbers;
import org.bukkit.unrealized.entity.Enderman;
import org.bukkit.unrealized.entity.EntityType;
import org.bukkit.unrealized.material.MaterialData;

public class CraftEnderman extends CraftMonster implements Enderman {
    public CraftEnderman(CraftServer server, EntityEnderman entity) {
        super(server, entity);
    }

    @Override
    public MaterialData getCarriedMaterial() {
        IBlockData blockData = getHandle().getCarriedBlock();
        return (blockData == null) ? Material.AIR.getNewData((byte) 0) : CraftMagicNumbers.getMaterial(blockData);
    }

    @Override
    public BlockData getCarriedBlock() {
        IBlockData blockData = getHandle().getCarriedBlock();
        return (blockData == null) ? null : CraftBlockData.fromData(blockData);
    }

    @Override
    public void setCarriedMaterial(MaterialData data) {
        getHandle().setCarriedBlock(CraftMagicNumbers.getBlock(data));
    }

    @Override
    public void setCarriedBlock(BlockData blockData) {
        getHandle().setCarriedBlock(blockData == null ? null : ((CraftBlockData) blockData).getState());
    }

    @Override
    public EntityEnderman getHandle() {
        return (EntityEnderman) entity;
    }

    @Override
    public String toString() {
        return "CraftEnderman";
    }

    @Override
    public EntityType getType() {
        return EntityType.ENDERMAN;
    }
}
