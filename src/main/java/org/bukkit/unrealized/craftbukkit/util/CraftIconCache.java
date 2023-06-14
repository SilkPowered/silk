package org.bukkit.unrealized.craftbukkit.util;

import org.bukkit.unrealized.util.CachedServerIcon;

public class CraftIconCache implements CachedServerIcon {
    public final byte[] value;

    public CraftIconCache(final byte[] value) {
        this.value = value;
    }
}
