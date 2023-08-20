package cx.rain.silk.mixins_legacy.mixin.server;

import cx.rain.silk.mixins_legacy.bridge.server.IMinecraftServerBridge;
import net.minecraft.registry.CombinedDynamicRegistries;
import net.minecraft.registry.ServerDynamicRegistryType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.WorldSaveHandler;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.command.ColouredConsoleSender;
import org.bukkit.craftbukkit.command.ConsoleCommandCompleter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin {
    private CraftServer silk$cserver;
    private final Map<String, ServerPlayerEntity> silk$playersByName = new HashMap<>();

    @Inject(at = @At("TAIL"), method = "<init>")
    private void silk$afterPlayerManagerInit(MinecraftServer server,
                                                    CombinedDynamicRegistries<ServerDynamicRegistryType> registryManager,
                                                    WorldSaveHandler saveHandler, int maxPlayers, CallbackInfo ci) {
        silk$cserver = new CraftServer((MinecraftDedicatedServer) server, (PlayerManager) (Object) this);
        ((IMinecraftServerBridge) server).silk$setCraftServer(silk$cserver);
        ((IMinecraftServerBridge) server).silk$setConsole(ColouredConsoleSender.getInstance());
        ((IMinecraftServerBridge) server).silk$getReader().addCompleter(new ConsoleCommandCompleter(silk$cserver));
    }
}
