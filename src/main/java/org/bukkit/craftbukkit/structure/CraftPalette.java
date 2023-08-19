package org.bukkit.craftbukkit.structure;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.structure.StructureTemplate;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.block.CraftBlockStates;
import org.bukkit.structure.Palette;

public class CraftPalette implements Palette {

    private final StructureTemplate.PalettedBlockInfoList palette;

    public CraftPalette(StructureTemplate.PalettedBlockInfoList palette) {
        this.palette = palette;
    }

    @Override
    public List<BlockState> getBlocks() {
        List<BlockState> blocks = new ArrayList<>();
        for (StructureTemplate.StructureBlockInfo blockInfo : palette.blocks()) {
            blocks.add(CraftBlockStates.getBlockState(blockInfo.comp_1341(), blockInfo.comp_1342(), blockInfo.comp_1343()));
        }
        return blocks;
    }

    @Override
    public int getBlockCount() {
        return palette.blocks().size();
    }
}
