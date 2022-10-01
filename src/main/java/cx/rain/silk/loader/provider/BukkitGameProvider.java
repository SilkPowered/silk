package cx.rain.silk.loader.provider;

import cx.rain.silk.Silk;
import cx.rain.silk.loader.bukkit.BukkitLibraries;
import cx.rain.silk.loader.bukkit.BukkitVersion;
import cx.rain.silk.utility.bundler.SilkBundlerProcessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.ObjectShare;
import net.fabricmc.loader.api.VersionParsingException;
import net.fabricmc.loader.api.metadata.ModDependency;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.fabricmc.loader.impl.FormattedException;
import net.fabricmc.loader.impl.game.GameProviderHelper;
import net.fabricmc.loader.impl.game.LibClassifier;
import net.fabricmc.loader.impl.game.minecraft.*;
import net.fabricmc.loader.impl.game.minecraft.patch.BrandingPatch;
import net.fabricmc.loader.impl.game.minecraft.patch.EntrypointPatch;
import net.fabricmc.loader.impl.game.patch.GameTransformer;
import net.fabricmc.loader.impl.launch.FabricLauncher;
import net.fabricmc.loader.impl.metadata.BuiltinModMetadata;
import net.fabricmc.loader.impl.metadata.ModDependencyImpl;
import net.fabricmc.loader.impl.util.Arguments;
import net.fabricmc.loader.impl.util.ExceptionUtil;
import net.fabricmc.loader.impl.util.LoaderUtil;
import net.fabricmc.loader.impl.util.SystemProperties;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.*;

public class BukkitGameProvider extends MinecraftGameProvider {
    protected static final String[] ALLOWED_EARLY_CLASS_PREFIXES = { "org.apache.logging.log4j.", "com.mojang.util." };

    protected static final Set<String> SENSITIVE_ARGS = new HashSet<>(Arrays.asList(
            "accesstoken",
            "clientid",
            "profileproperties",
            "proxypass",
            "proxyuser",
            "username",
            "userproperties",
            "uuid",
            "xuid"));

    protected static final GameTransformer TRANSFORMER = new GameTransformer(
            new EntrypointPatch(),
            new BrandingPatch());

    protected BukkitVersion bukkitVersion;
    protected McVersion versionData;

    protected EnvType envType;
    protected Arguments arguments;
    protected final List<Path> gameJars = new ArrayList<>();

    protected String entrypoint;
    protected Path realmsJar;
    protected boolean hasModLoader = false;
    protected boolean log4jAvailable;
    protected boolean slf4jAvailable;

    protected final Set<Path> logJars = new HashSet<>();
    protected final List<Path> miscGameLibraries = new ArrayList<>();
    protected Collection<Path> validParentClassPath;

    @Override
    public String getGameId() {
        return super.getGameId();
    }

    @Override
    public String getGameName() {
        return super.getGameName() + " Bukkit";
    }

    @Override
    public String getRawGameVersion() {
        // Todo: qyl27: Detect bukkit version.
        return versionData.getRaw();
    }

    @Override
    public String getNormalizedGameVersion() {
        // Todo: qyl27: Detect bukkit version.
        return versionData.getNormalized();
    }

    @Override
    public Collection<BuiltinMod> getBuiltinMods() {
        var minecraftBuilder = new BuiltinModMetadata.Builder(getGameId(), getNormalizedGameVersion())
                .setName(getGameName());

        if (versionData.getClassVersion().isPresent()) {
            int version = versionData.getClassVersion().getAsInt() - 44;

            try {
                minecraftBuilder.addDependency(new ModDependencyImpl(ModDependency.Kind.DEPENDS, "java", Collections.singletonList(String.format(Locale.ENGLISH, ">=%d", version))));
            } catch (VersionParsingException e) {
                throw new RuntimeException(e);
            }
        }

        return List.of(new BuiltinMod(gameJars, minecraftBuilder.build()));
    }

    @Override
    public Path getGameJar() {
        return gameJars.get(0);
    }

    @Override
    public String getEntrypoint() {
        return entrypoint;
    }

    @Override
    public boolean isEnabled() {
        return System.getProperty(LoaderProperties.SILK_SKIP_BUKKIT_PROVIDER) == null;
    }

    @Override
    public boolean locateGame(FabricLauncher launcher, String[] args) {
        envType = launcher.getEnvironmentType();

        if (envType == EnvType.CLIENT) {
            throw new RuntimeException("Server only!");
        }

        arguments = new Arguments();
        arguments.parse(args);

        try {
            var classifier = new LibClassifier<>(BukkitLibraries.class, envType, this);
            var envGameLib = BukkitLibraries.BUKKIT_SERVER;
            var commonGameJar = GameProviderHelper.getCommonGameJar();
            var envGameJar = GameProviderHelper.getEnvGameJar(envType);
            var commonGameJarDeclared = commonGameJar != null;

            if (commonGameJarDeclared) {
                if (envGameJar != null) {
                    classifier.process(envGameJar, BukkitLibraries.BUKKIT_SERVER);
                }

                classifier.process(commonGameJar);
            } else if (envGameJar != null) {
                classifier.process(envGameJar);
            }

            classifier.process(launcher.getClassPath());

            if (classifier.has(BukkitLibraries.BUKKIT_BUNDLER)) {
                SilkBundlerProcessor.process(classifier);
            }

            envGameJar = classifier.getOrigin(envGameLib);
            if (envGameJar == null) {
                return false;
            }

            commonGameJar = classifier.getOrigin(BukkitLibraries.BUKKIT_SERVER);

            if (commonGameJarDeclared && commonGameJar == null) {
                Silk.get().getLoaderLogger().warn("No game jar for bukkit loader.");
            }

            gameJars.add(envGameJar);

            if (commonGameJar != null && !commonGameJar.equals(envGameJar)) {
                gameJars.add(commonGameJar);
            }

            entrypoint = classifier.getClassName(envGameLib);
            realmsJar = classifier.getOrigin(BukkitLibraries.REALMS);
            hasModLoader = classifier.has(BukkitLibraries.MODLOADER);
            log4jAvailable = classifier.has(BukkitLibraries.LOG4J_API) && classifier.has(BukkitLibraries.LOG4J_CORE);
            slf4jAvailable = classifier.has(BukkitLibraries.SLF4J_API) && classifier.has(BukkitLibraries.SLF4J_CORE);
            boolean hasLogLib = log4jAvailable || slf4jAvailable;

            for (var lib : BukkitLibraries.LOGGING) {
                Path path = classifier.getOrigin(lib);

                if (path != null) {
                    if (hasLogLib) {
                        logJars.add(path);
                    } else if (!gameJars.contains(path)) {
                        miscGameLibraries.add(path);
                    }
                }
            }

            miscGameLibraries.addAll(classifier.getUnmatchedOrigins());

            File librariesDir = getLaunchDirectory().resolve("bundler").resolve("libraries").toFile();
            if (librariesDir.isDirectory()) {
                for (File f : librariesDir.listFiles()) {
                    miscGameLibraries.add(f.toPath());
                }
            }

            validParentClassPath = classifier.getSystemLibraries();
        } catch (IOException ex) {
            throw ExceptionUtil.wrap(ex);
        }

        // expose obfuscated jar locations for mods to more easily remap code from obfuscated to intermediary
        ObjectShare share = FabricLoaderImpl.INSTANCE.getObjectShare();
        share.put("fabric-loader:inputGameJar", gameJars.get(0)); // deprecated
        share.put("fabric-loader:inputGameJars", gameJars);
        if (realmsJar != null) share.put("fabric-loader:inputRealmsJar", realmsJar);

        String version = arguments.remove(Arguments.GAME_VERSION);
        if (version == null) version = System.getProperty(SystemProperties.GAME_VERSION);
        versionData = McVersionLookup.getVersion(gameJars, entrypoint, version);

        processArgumentMap(arguments, envType);

        return true;
    }

    protected static void processArgumentMap(Arguments argMap, EnvType envType) {
        switch (envType) {
            case CLIENT -> {
                throw new RuntimeException("Not supported in server.");
            }
            case SERVER -> {
                argMap.remove("version");
                argMap.remove("gameDir");
                argMap.remove("assetsDir");
            }
        }
    }

    @Override
    public void initialize(FabricLauncher launcher) {
        launcher.setValidParentClassPath(validParentClassPath);

        if (isObfuscated()) {
            Map<String, Path> obfJars = new HashMap<>(3);
            var names = new String[gameJars.size()];

            for (int i = 0; i < gameJars.size(); i++) {
                String name;

                if (i == 0) {
                    name = envType.name().toLowerCase(Locale.ENGLISH);
                } else if (i == 1) {
                    name = "common";
                } else {
                    name = String.format(Locale.ENGLISH, "extra-%d", i - 2);
                }

                obfJars.put(name, gameJars.get(i));
                names[i] = name;
            }

            if (realmsJar != null) {
                obfJars.put("realms", realmsJar);
            }

            obfJars = GameProviderHelper.deobfuscate(obfJars,
                    getGameId(), getNormalizedGameVersion(),
                    getLaunchDirectory(),
                    launcher);

            for (int i = 0; i < gameJars.size(); i++) {
                Path newJar = obfJars.get(names[i]);
                Path oldJar = gameJars.set(i, newJar);

                if (logJars.remove(oldJar)) logJars.add(newJar);
            }

            realmsJar = obfJars.get("realms");
        }

        if (!logJars.isEmpty()) {
            for (Path jar : logJars) {
                if (gameJars.contains(jar)) {
                    launcher.addToClassPath(jar, ALLOWED_EARLY_CLASS_PREFIXES);
                } else {
                    launcher.addToClassPath(jar);
                }
            }
        }

        setupLogHandler(launcher, true);

        TRANSFORMER.locateEntrypoints(launcher, gameJars);
    }

    private void setupLogHandler(FabricLauncher launcher, boolean useTargetCl) {
        System.setProperty("log4j2.formatMsgNoLookups", "true"); // lookups are not used by mc and cause issues with older log4j2 versions
        // Todo.
    }

    @Override
    public String[] getLaunchArguments(boolean sanitize) {
        {
            if (arguments == null) return new String[0];

            String[] ret = arguments.toArray();
            if (!sanitize) return ret;

            int writeIdx = 0;

            for (int i = 0; i < ret.length; i++) {
                String arg = ret[i];

                if (i + 1 < ret.length
                        && arg.startsWith("--")
                        && SENSITIVE_ARGS.contains(arg.substring(2).toLowerCase(Locale.ENGLISH))) {
                    i++; // skip value
                } else {
                    ret[writeIdx++] = arg;
                }
            }

            if (writeIdx < ret.length) ret = Arrays.copyOf(ret, writeIdx);

            return ret;
        }
    }

    @Override
    public Arguments getArguments() {
        return arguments;
    }

    @Override
    public GameTransformer getEntrypointTransformer() {
        return TRANSFORMER;
    }

    @Override
    public boolean hasAwtSupport() {
        return !LoaderUtil.hasMacOs();
    }

    @Override
    public boolean canOpenErrorGui() {
        if (arguments == null || envType == EnvType.CLIENT) {
            return true;
        }

        List<String> extras = arguments.getExtraArgs();
        return !extras.contains("nogui") && !extras.contains("--nogui");
    }

    @Override
    public void unlockClassPath(FabricLauncher launcher) {
        for (Path gameJar : gameJars) {
            if (logJars.contains(gameJar)) {
                launcher.setAllowedPrefixes(gameJar);
            } else {
                launcher.addToClassPath(gameJar);
            }
        }

        if (realmsJar != null) launcher.addToClassPath(realmsJar);

        for (Path lib : miscGameLibraries) {
            launcher.addToClassPath(lib);
        }
    }

    @Override
    public void launch(ClassLoader loader) {
        var targetClass = entrypoint;

        MethodHandle invoker;
        try {
            // Todo: qyl27: for spigot now.
            List<URL> urls = new ArrayList<>();
            File librariesDir = getLaunchDirectory().resolve("bundler").resolve("libraries").toFile();
            if (librariesDir.isDirectory()) {
                for (File f : librariesDir.listFiles()) {
                    urls.add(f.toURI().toURL());
                }
            }

            URLClassLoader cl = new URLClassLoader(urls.toArray(new URL[0]), loader);
            var c = cl.loadClass(targetClass);
            invoker = MethodHandles.lookup().findStatic(c, "main", MethodType.methodType(void.class, String[].class));
        } catch (NoSuchMethodException | IllegalAccessException | ClassNotFoundException | MalformedURLException ex) {
            throw new FormattedException("Failed to start Minecraft", ex);
        }

        try {
            invoker.invokeExact(arguments.toArray());
        } catch (Throwable ex) {
            throw new FormattedException("Minecraft has crashed!", ex);
        }
    }
}
