package org.bukkit.unrealized.event.server;

import org.bukkit.unrealized.event.Event;

/**
 * Miscellaneous server events
 */
public abstract class ServerEvent extends Event {

    public ServerEvent() {
        super();
    }

    public ServerEvent(boolean isAsync) {
        super(isAsync);
    }
}
