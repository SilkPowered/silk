package cx.rain.silk.loader.provider;

import cx.rain.silk.loader.bukkit.BukkitVersion;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.ObjectShare;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.fabricmc.loader.impl.game.GameProviderHelper;
import net.fabricmc.loader.impl.game.LibClassifier;
import net.fabricmc.loader.impl.game.minecraft.*;
import net.fabricmc.loader.impl.launch.FabricLauncher;
import net.fabricmc.loader.impl.util.Arguments;
import net.fabricmc.loader.impl.util.ExceptionUtil;
import net.fabricmc.loader.impl.util.SystemProperties;
import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class BukkitGameProvider extends MinecraftGameProvider {
    private BukkitVersion bukkitVersion;

//    private McVersion versionData;
//    private EnvType envType;
//    private String entrypoint;
//    private Arguments arguments;
//    private final List<Path> gameJars = new ArrayList<>(2); // env game jar and potentially common game jar
//    private Path realmsJar;
//    private final Set<Path> logJars = new HashSet<>();
//    private boolean log4jAvailable;
//    private boolean slf4jAvailable;
//    private final List<Path> miscGameLibraries = new ArrayList<>(); // libraries not relevant for loader's uses
//    private Collection<Path> validParentClassPath; // computed parent class path restriction (loader+deps)
//    private boolean hasModLoader = false;

    @Override
    public String getGameName() {
        return super.getGameName() + " Bukkit";
    }

    @Override
    public boolean locateGame(FabricLauncher launcher, String[] args) {
        // Todo: qyl27: maybe we need not a game provider?
        return false;
    }
}
