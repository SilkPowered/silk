package cx.rain.silk;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public class SilkLoader implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        Silk.get();
    }
}
