/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.data.Ageable;
import org.bukkit.unrealized.block.data.Bisected;
import org.bukkit.unrealized.block.data.type.PitcherCrop;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftPitcherCrop extends CraftBlockData implements PitcherCrop, Ageable, Bisected {

    public CraftPitcherCrop() {
        super();
    }

    public CraftPitcherCrop(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.CraftAgeable

    private static final net.minecraft.world.level.block.state.properties.BlockStateInteger AGE = getInteger(net.minecraft.world.level.block.PitcherCropBlock.class, "age");

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

    // org.bukkit.craftbukkit.block.data.CraftBisected

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> HALF = getEnum(net.minecraft.world.level.block.PitcherCropBlock.class, "half");

    @Override
    public Half getHalf() {
        return get(HALF, Half.class);
    }

    @Override
    public void setHalf(Half half) {
        set(HALF, half);
    }
}
