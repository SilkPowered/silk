package org.bukkit.unrealized.craftbukkit.util.permissions;

import org.bukkit.unrealized.permissions.Permission;
import org.bukkit.unrealized.util.permissions.DefaultPermissions;

public final class CraftDefaultPermissions {
    private static final String ROOT = "minecraft";

    private CraftDefaultPermissions() {}

    public static void registerCorePermissions() {
        Permission parent = DefaultPermissions.registerPermission(ROOT, "Gives the user the ability to use all vanilla utilities and commands");
        CommandPermissions.registerPermissions(parent);
        parent.recalculatePermissibles();
    }
}
