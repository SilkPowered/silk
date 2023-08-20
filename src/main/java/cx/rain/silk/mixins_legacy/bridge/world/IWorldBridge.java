package cx.rain.silk.mixins_legacy.bridge.world;

import net.minecraft.util.math.BlockPos;
import org.bukkit.craftbukkit.block.CapturedBlockState;

import java.util.Map;

public interface IWorldBridge {
    boolean silk$getCaptureTreeGeneration();
    void silk$setCaptureTreeGeneration(boolean value);

    boolean silk$getPreventPoiUpdated();
    void silk$setPreventPoiUpdated(boolean value);

    boolean silk$getCaptureBlockStates();
    void silk$setCaptureBlockStates(boolean value);

    Map<BlockPos, CapturedBlockState> silk$getCapturedBlockStates();
}
