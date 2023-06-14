package org.bukkit.unrealized.entity.minecart;

import org.bukkit.unrealized.entity.Minecart;
import org.bukkit.entity.Entity;
import org.bukkit.unrealized.entity.IronGolem;
import org.bukkit.unrealized.entity.LivingEntity;

/**
 * Represents a minecart that can have certain {@link
 * Entity entities} as passengers. Normal passengers
 * include all {@link LivingEntity living entities} with
 * the exception of {@link IronGolem iron golems}.
 * Non-player entities that meet normal passenger criteria automatically
 * mount these minecarts when close enough.
 */
public interface RideableMinecart extends Minecart {
}
