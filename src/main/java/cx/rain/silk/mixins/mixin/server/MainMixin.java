package cx.rain.silk.mixins.mixin.server;

import net.minecraft.SharedConstants;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.server.Main;
import net.minecraft.server.SaveLoading;
import net.minecraft.server.dedicated.ServerPropertiesHandler;
import net.minecraft.util.WorldSavePath;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Mixin(Main.class)
public abstract class MainMixin {
    @Inject(at = @At("HEAD"), method = "createServerConfig")
    private static void silk$beforeCreateServerConfig(ServerPropertiesHandler serverPropertiesHandler,
                                                      LevelStorage.Session session,
                                                      boolean safeMode, ResourcePackManager dataPackManager,
                                                      CallbackInfoReturnable<SaveLoading.ServerConfig> cir) {
        var bukkitDatapackFolder = new File(session.getDirectory(WorldSavePath.DATAPACKS).toFile(), "bukkit");
        if (!bukkitDatapackFolder.exists()) {
            bukkitDatapackFolder.mkdirs();
        }

        File mcMeta = new File(bukkitDatapackFolder, "pack.mcmeta");
        try {
            Files.writeString(mcMeta.toPath(), """
                    {
                        "pack": {
                            "description": "Data pack for resources provided by Bukkit plugins",
                            "pack_format":\s""" + SharedConstants.getGameVersion().getResourceVersion(ResourceType.SERVER_DATA) + """
                        }
                    }
                    """, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            throw new RuntimeException("Could not initialize Bukkit datapack", ex);
        }
    }

}
