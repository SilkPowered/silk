package org.bukkit.craftbukkit.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.dimension.DimensionType;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.block.CraftBlock;
import org.bukkit.craftbukkit.block.CraftBlockEntityState;
import org.bukkit.craftbukkit.block.CraftBlockState;

public class BlockStateListPopulator extends DummyGeneratorAccess {
    private final WorldAccess world;
    private final Map<BlockPos, net.minecraft.block.BlockState> dataMap = new HashMap<>();
    private final Map<BlockPos, BlockEntity> entityMap = new HashMap<>();
    private final LinkedHashMap<BlockPos, CraftBlockState> list;

    public BlockStateListPopulator(WorldAccess world) {
        this(world, new LinkedHashMap<>());
    }

    private BlockStateListPopulator(WorldAccess world, LinkedHashMap<BlockPos, CraftBlockState> list) {
        this.world = world;
        this.list = list;
    }

    @Override
    public net.minecraft.block.BlockState getBlockState(BlockPos bp) {
        net.minecraft.block.BlockState blockData = dataMap.get(bp);
        return (blockData != null) ? blockData : world.getBlockState(bp);
    }

    @Override
    public FluidState getFluidState(BlockPos bp) {
        net.minecraft.block.BlockState blockData = dataMap.get(bp);
        return (blockData != null) ? blockData.getFluidState() : world.getFluidState(bp);
    }

    @Override
    public BlockEntity getBlockEntity(BlockPos blockposition) {
        // The contains is important to check for null values
        if (entityMap.containsKey(blockposition)) {
            return entityMap.get(blockposition);
        }

        return world.getBlockEntity(blockposition);
    }

    @Override
    public boolean setBlockState(BlockPos position, net.minecraft.block.BlockState data, int flag) {
        position = position.toImmutable();
        // remove first to keep insertion order
        list.remove(position);

        dataMap.put(position, data);
        if (data.hasBlockEntity()) {
            entityMap.put(position, ((BlockEntityProvider) data.getBlock()).createBlockEntity(position, data));
        } else {
            entityMap.put(position, null);
        }

        // use 'this' to ensure that the block state is the correct TileState
        CraftBlockState state = (CraftBlockState) CraftBlock.at(this, position).getState();
        state.setFlag(flag);
        // set world handle to ensure that updated calls are done to the world and not to this populator
        state.setWorldHandle(world);
        list.put(position, state);
        return true;
    }

    @Override
    public ServerWorld getMinecraftWorld() {
        return world.getMinecraftWorld();
    }

    public void refreshTiles() {
        for (CraftBlockState state : list.values()) {
            if (state instanceof CraftBlockEntityState) {
                ((CraftBlockEntityState<?>) state).refreshSnapshot();
            }
        }
    }

    public void updateList() {
        for (BlockState state : list.values()) {
            state.update(true);
        }
    }

    public Set<BlockPos> getBlocks() {
        return list.keySet();
    }

    public List<CraftBlockState> getList() {
        return new ArrayList<>(list.values());
    }

    public WorldAccess getWorld() {
        return world;
    }

    // For tree generation
    @Override
    public int getBottomY() {
        return getWorld().C_();
    }

    @Override
    public int getHeight() {
        return getWorld().D_();
    }

    @Override
    public boolean testBlockState(BlockPos blockposition, Predicate<net.minecraft.block.BlockState> predicate) {
        return predicate.test(getBlockState(blockposition));
    }

    @Override
    public boolean testFluidState(BlockPos bp, Predicate<FluidState> prdct) {
        return world.testFluidState(bp, prdct);
    }

    @Override
    public DimensionType getDimension() {
        return world.getDimension();
    }

    @Override
    public DynamicRegistryManager getRegistryManager() {
        return world.getRegistryManager();
    }
}
