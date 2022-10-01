package cx.rain.silk.loader.bukkit;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.impl.game.LibClassifier;

public enum BukkitLibraries implements LibClassifier.LibraryType {
    BUKKIT_SERVER(EnvType.SERVER, "org/bukkit/craftbukkit/Main.class"),
    BUKKIT_BUNDLER(EnvType.SERVER, "org.bukkit.craftbukkit.bootstrap.Main.class"),
    MC_CLIENT(EnvType.CLIENT, "net/minecraft/client/main/Main.class", "net/minecraft/client/MinecraftApplet.class", "com/mojang/minecraft/MinecraftApplet.class"),
    MC_SERVER(EnvType.SERVER, "net/minecraft/server/Main.class", "net/minecraft/server/MinecraftServer.class", "com/mojang/minecraft/server/MinecraftServer.class"),
    MC_COMMON("net/minecraft/server/MinecraftServer.class"),
    MC_BUNDLER(EnvType.SERVER, "net/minecraft/bundler/Main.class"),
    REALMS(EnvType.CLIENT, "realmsVersion", "com/mojang/realmsclient/RealmsVersion.class"),
    MODLOADER("ModLoader"),
    LOG4J_API("org/apache/logging/log4j/LogManager.class"),
    LOG4J_CORE("META-INF/services/org.apache.logging.log4j.spi.Provider", "META-INF/log4j-provider.properties"),
    LOG4J_CONFIG("log4j2.xml"),
    LOG4J_PLUGIN("com/mojang/util/UUIDTypeAdapter.class"), // in authlib
    LOG4J_PLUGIN_2("com/mojang/patchy/LegacyXMLLayout.class"), // in patchy
    LOG4J_PLUGIN_3("net/minecrell/terminalconsole/util/LoggerNamePatternSelector.class"), // in terminalconsoleappender, used by loom's log4j config
    GSON("com/google/gson/TypeAdapter.class"), // used by log4j plugins
    SLF4J_API("org/slf4j/Logger.class"),
    SLF4J_CORE("META-INF/services/org.slf4j.spi.SLF4JServiceProvider")
    ;

    public static final BukkitLibraries[] GAME = { MC_CLIENT, MC_SERVER, MC_BUNDLER };
    public static final BukkitLibraries[] LOGGING = { LOG4J_API, LOG4J_CORE, LOG4J_CONFIG, LOG4J_PLUGIN, LOG4J_PLUGIN_2, LOG4J_PLUGIN_3, GSON, SLF4J_API, SLF4J_CORE };

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
