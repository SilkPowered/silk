package cx.rain.silk.loader.bukkit;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.impl.game.LibClassifier;

public enum BukkitLibraries implements LibClassifier.LibraryType {
    BUKKIT_SERVER(EnvType.SERVER, "org/bukkit/craftbukkit/Main.class"),
    BUKKIT_BUNDLER(EnvType.SERVER, "org.bukkit.craftbukkit.bootstrap.Main.class")
    ;

    private final EnvType envType;
    private final String[] classPaths;

    BukkitLibraries(String path) {
        this(null, new String[]{ path });
    }

    BukkitLibraries(String... paths) {
        this(null, paths);
    }

    BukkitLibraries(EnvType env, String... paths) {
        classPaths = paths;
        envType = env;
    }

    @Override
    public boolean isApplicable(EnvType env) {
        return envType == null || envType == env;
    }

    @Override
    public String[] getPaths() {
        return classPaths;
    }
}
