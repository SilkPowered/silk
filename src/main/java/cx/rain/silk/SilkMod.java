package cx.rain.silk;

import net.fabricmc.api.DedicatedServerModInitializer;

public class SilkMod implements DedicatedServerModInitializer {
    private final Silk silk = Silk.get();

    @Override
    public void onInitializeServer() {
        silk.getLogger().info("Silk loading!");


    }
}
