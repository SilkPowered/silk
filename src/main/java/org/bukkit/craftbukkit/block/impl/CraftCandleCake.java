/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.craftbukkit.block.impl;

public final class CraftCandleCake extends org.bukkit.craftbukkit.block.data.CraftBlockData implements org.bukkit.block.data.Lightable {

    public CraftCandleCake() {
        super();
    }

    public CraftCandleCake(net.minecraft.block.BlockState state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.CraftLightable

    private static final net.minecraft.state.property.BooleanProperty LIT = getBoolean(net.minecraft.block.CandleCakeBlock.class, "lit");

    @Override
    public boolean isLit() {
        return get(LIT);
    }

    @Override
    public void setLit(boolean lit) {
        set(LIT, lit);
    }
}
