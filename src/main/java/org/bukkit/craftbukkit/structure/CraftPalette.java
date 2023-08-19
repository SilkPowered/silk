package org.bukkit.craftbukkit.structure;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.structure.StructureTemplate;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.block.CraftBlockStates;
import org.bukkit.structure.Palette;

public class CraftPalette implements Palette {

    private final StructureTemplate.a palette;

    public CraftPalette(StructureTemplate.a palette) {
        this.palette = palette;
    }

    @Override
    public List<BlockState> getBlocks() {
        List<BlockState> blocks = new ArrayList<>();
        for (StructureTemplate.StructureBlockInfo blockInfo : palette.blocks()) {
            blocks.add(CraftBlockStates.getBlockState(blockInfo.pos(), blockInfo.state(), blockInfo.nbt()));
        }
        return blocks;
    }

    @Override
    public int getBlockCount() {
        return palette.blocks().size();
    }
}
