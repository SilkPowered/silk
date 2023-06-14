package org.bukkit.unrealized.entity;

import org.bukkit.entity.Entity;

/**
 * Represents an instance of a lightning strike. May or may not do damage.
 */
public interface LightningStrike extends Entity {

    /**
     * Returns whether the strike is an effect that does no damage.
     *
     * @return whether the strike is an effect
     */
    public boolean isEffect();

}
