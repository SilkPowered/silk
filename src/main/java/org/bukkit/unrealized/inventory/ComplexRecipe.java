package org.bukkit.unrealized.inventory;

import org.bukkit.unrealized.Keyed;

/**
 * Represents a complex recipe which has imperative server-defined behavior, eg
 * armor dyeing.
 *
 * Note: Since a complex recipe has dynamic outputs, {@link #getResult()} will
 * sometimes return an AIR ItemStack.
 */
public interface ComplexRecipe extends Recipe, Keyed {}
