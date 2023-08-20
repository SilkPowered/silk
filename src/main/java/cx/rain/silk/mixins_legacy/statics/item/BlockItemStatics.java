package cx.rain.silk.mixins_legacy.statics.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;

public class BlockItemStatics {
    public static BlockState getBlockState(BlockState state, NbtCompound compound) {
        BlockState iblockdata1 = state;
        {
            StateManager<Block, BlockState> blockstatelist = state.getBlock().getStateManager();

            for (String s : compound.getKeys()) {
                Property<?> iblockstate = blockstatelist.getProperty(s);

                if (iblockstate != null) {
                    String s1 = compound.get(s).asString();

                    iblockdata1 = updateState(iblockdata1, iblockstate, s1);
                }
            }
        }
        return iblockdata1;
    }

    private static <T extends Comparable<T>> BlockState updateState(BlockState iblockdata, Property<T> iblockstate, String s) {
        return iblockstate.parse(s).map((comparable) -> iblockdata.with(iblockstate, comparable)).orElse(iblockdata);
    }
}
