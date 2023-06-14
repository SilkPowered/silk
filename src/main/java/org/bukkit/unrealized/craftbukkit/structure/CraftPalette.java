package org.bukkit.unrealized.craftbukkit.structure;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructure;
import org.bukkit.unrealized.block.BlockState;
import org.bukkit.unrealized.craftbukkit.block.CraftBlockStates;
import org.bukkit.unrealized.structure.Palette;

public class CraftPalette implements Palette {

    private final DefinedStructure.a palette;

    public CraftPalette(DefinedStructure.a palette) {
        this.palette = palette;
    }

    @Override
    public List<BlockState> getBlocks() {
        List<BlockState> blocks = new ArrayList<>();
        for (DefinedStructure.BlockInfo blockInfo : palette.blocks()) {
            blocks.add(CraftBlockStates.getBlockState(blockInfo.pos(), blockInfo.state(), blockInfo.nbt()));
        }
        return blocks;
    }

    @Override
    public int getBlockCount() {
        return palette.blocks().size();
    }
}
