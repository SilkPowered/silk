package org.bukkit.unrealized.util;

import org.bukkit.unrealized.Server;
import org.bukkit.unrealized.event.server.ServerListPingEvent;

/**
 * This is a cached version of a server-icon. It's internal representation
 * and implementation is undefined.
 *
 * @see Server#getServerIcon()
 * @see Server#loadServerIcon(java.awt.image.BufferedImage)
 * @see Server#loadServerIcon(java.io.File)
 * @see ServerListPingEvent#setServerIcon(CachedServerIcon)
 */
public interface CachedServerIcon {}
