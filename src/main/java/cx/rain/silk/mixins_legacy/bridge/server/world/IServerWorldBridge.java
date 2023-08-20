package cx.rain.silk.mixins_legacy.bridge.server.world;

import net.minecraft.registry.RegistryKey;
import net.minecraft.world.dimension.DimensionOptions;

@Deprecated
public interface IServerWorldBridge {
    RegistryKey<DimensionOptions> silk$getTypeKey();
    void silk$setTypeKey(RegistryKey<DimensionOptions> value);
}
