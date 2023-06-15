package org.spigotmc;

import cx.rain.silk.mixins.interfaces.server.IMinecraftServerMixin;

public class AsyncCatcher
{

    public static boolean enabled = true;

    public static void catchOp(String reason)
    {
        if ( enabled && Thread.currentThread() != IMinecraftServerMixin.getServer().serverThread )
        {
            throw new IllegalStateException( "Asynchronous " + reason + "!" );
        }
    }
}
