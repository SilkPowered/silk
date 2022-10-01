package cx.rain.silk;

import cx.rain.silk.early_loading.worker.Hello;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.fabricmc.loader.impl.FabricLoaderImpl;

public class SilkLoader implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        putObjectShare();

        Hello.printHello();
    }

    private void putObjectShare() {
        var objectShare = FabricLoaderImpl.INSTANCE.getObjectShare();


    }
}
