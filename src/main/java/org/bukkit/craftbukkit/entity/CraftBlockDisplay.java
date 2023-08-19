package org.bukkit.craftbukkit.entity;

import com.google.common.base.Preconditions;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.bukkit.entity.BlockDisplay;

public class CraftBlockDisplay extends CraftDisplay implements BlockDisplay {

    public CraftBlockDisplay(CraftServer server, net.minecraft.entity.decoration.DisplayEntity.BlockDisplayEntity entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.entity.decoration.DisplayEntity.BlockDisplayEntity getHandle() {
        return (net.minecraft.entity.decoration.DisplayEntity.BlockDisplayEntity) super.getHandle();
    }

    @Override
    public String toString() {
        return "CraftBlockDisplay";
    }

    @Override
    public BlockData getBlock() {
        return CraftBlockData.fromData(getHandle().getBlockState());
    }

    @Override
    public void setBlock(BlockData block) {
        Preconditions.checkArgument(block != null, "Block cannot be null");

        getHandle().setBlockState(((CraftBlockData) block).getState());
    }
}
