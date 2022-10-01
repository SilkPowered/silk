package cx.rain.silk.early_loading;

public class SilkEarlyLoadingConstants {
    public static final String CLASS_MINECRAFT_GAME_PROVIDER = "net/fabricmc/loader/impl/game/minecraft/MinecraftGameProvider";
    public static final String METHOD_MINECRAFT_GAME_PROVIDER_LOCATE_GAME = "locateGame";
    public static final String METHOD_DESC_MINECRAFT_GAME_PROVIDER_LOCATE_GAME ="(Lnet/fabricmc/loader/impl/launch/FabricLauncher;[Ljava/lang/String)Z";

    public static final String CLASS_SILK_WORKER_HELLO = "cx/rain/silk/early_loading/worker/Hello";
    public static final String METHOD_SILK_WORKER_HELLO = "printHello";
    public static final String METHOD_DESC_SILK_WORKER_HELLO = "()V";
}
