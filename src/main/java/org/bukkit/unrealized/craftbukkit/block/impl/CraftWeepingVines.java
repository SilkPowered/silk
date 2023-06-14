/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.data.Ageable;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftWeepingVines extends CraftBlockData implements Ageable {

    public CraftWeepingVines() {
        super();
    }

    public CraftWeepingVines(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.CraftAgeable

    private static final net.minecraft.world.level.block.state.properties.BlockStateInteger AGE = getInteger(net.minecraft.world.level.block.BlockWeepingVines.class, "age");

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
}