package org.bukkit.unrealized.entity;

import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a single part of a {@link ComplexLivingEntity}
 */
public interface ComplexEntityPart extends Entity {

    /**
     * Gets the parent {@link ComplexLivingEntity} of this part.
     *
     * @return Parent complex entity
     */
    @NotNull
    public ComplexLivingEntity getParent();
}
