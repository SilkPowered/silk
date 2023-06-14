/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.data.Ageable;
import org.bukkit.unrealized.block.data.type.Bamboo;
import org.bukkit.unrealized.block.data.type.Sapling;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftBamboo extends CraftBlockData implements Bamboo, Ageable, Sapling {

    public CraftBamboo() {
        super();
    }

    public CraftBamboo(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.type.CraftBamboo

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> LEAVES = getEnum(net.minecraft.world.level.block.BlockBamboo.class, "leaves");

    @Override
    public Leaves getLeaves() {
        return get(LEAVES, Leaves.class);
    }

    @Override
    public void setLeaves(Leaves leaves) {
        set(LEAVES, leaves);
    }

    // org.bukkit.craftbukkit.block.data.CraftAgeable

    private static final net.minecraft.world.level.block.state.properties.BlockStateInteger AGE = getInteger(net.minecraft.world.level.block.BlockBamboo.class, "age");

    @Override
    public int getAge() {
        return get(AGE);
    }

    @Override
    public void setAge(int age) {
        set(AGE, age);
    }

    @Override
    public int getMaximumAge() {
        return getMax(AGE);
    }

    // org.bukkit.craftbukkit.block.data.type.CraftSapling

    private static final net.minecraft.world.level.block.state.properties.BlockStateInteger STAGE = getInteger(net.minecraft.world.level.block.BlockBamboo.class, "stage");

    @Override
    public int getStage() {
        return get(STAGE);
    }

    @Override
    public void setStage(int stage) {
        set(STAGE, stage);
    }

    @Override
    public int getMaximumStage() {
        return getMax(STAGE);
    }
}