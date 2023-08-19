/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.craftbukkit.block.impl;

public final class CraftCaveVines extends org.bukkit.craftbukkit.block.data.CraftBlockData implements org.bukkit.block.data.type.CaveVines, org.bukkit.block.data.Ageable, org.bukkit.block.data.type.CaveVinesPlant {

    public CraftCaveVines() {
        super();
    }

    public CraftCaveVines(net.minecraft.block.BlockState state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.CraftAgeable

    private static final net.minecraft.state.property.IntProperty AGE = getInteger(net.minecraft.block.CaveVinesHeadBlock.class, "age");

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

    // org.bukkit.craftbukkit.block.data.type.CraftCaveVinesPlant

    private static final net.minecraft.state.property.BooleanProperty BERRIES = getBoolean(net.minecraft.block.CaveVinesHeadBlock.class, "berries");

    @Override
    public boolean isBerries() {
        return get(BERRIES);
    }

    @Override
    public void setBerries(boolean berries) {
        set(BERRIES, berries);
    }
}
