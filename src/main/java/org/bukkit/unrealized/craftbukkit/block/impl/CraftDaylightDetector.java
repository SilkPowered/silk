/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.block.data.AnaloguePowerable;
import org.bukkit.unrealized.block.data.type.DaylightDetector;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftDaylightDetector extends CraftBlockData implements DaylightDetector, AnaloguePowerable {

    public CraftDaylightDetector() {
        super();
    }

    public CraftDaylightDetector(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.type.CraftDaylightDetector

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean INVERTED = getBoolean(net.minecraft.world.level.block.BlockDaylightDetector.class, "inverted");

    @Override
    public boolean isInverted() {
        return get(INVERTED);
    }

    @Override
    public void setInverted(boolean inverted) {
        set(INVERTED, inverted);
    }

    // org.bukkit.craftbukkit.block.data.CraftAnaloguePowerable

    private static final net.minecraft.world.level.block.state.properties.BlockStateInteger POWER = getInteger(net.minecraft.world.level.block.BlockDaylightDetector.class, "power");

    @Override
    public int getPower() {
        return get(POWER);
    }

    @Override
    public void setPower(int power) {
        set(POWER, power);
    }

    @Override
    public int getMaximumPower() {
        return getMax(POWER);
    }
}
