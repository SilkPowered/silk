package org.bukkit.unrealized.event.entity;

import org.bukkit.unrealized.Material;
import org.bukkit.unrealized.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.unrealized.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

/**
 * Called when an {@link Entity} breaks a door
 * <p>
 * Cancelling the event will cause the event to be delayed
 */
public class EntityBreakDoorEvent extends EntityChangeBlockEvent {
    public EntityBreakDoorEvent(@NotNull final LivingEntity entity, @NotNull final Block targetBlock) {
        super(entity, targetBlock, Material.AIR.createBlockData());
    }

    @NotNull
    @Override
    public LivingEntity getEntity() {
        return (LivingEntity) entity;
    }
}
