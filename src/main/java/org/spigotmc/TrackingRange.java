package org.spigotmc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.decoration.DisplayEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class TrackingRange
{

    /**
     * Gets the range an entity should be 'tracked' by players and visible in
     * the client.
     *
     * @param entity
     * @param defaultRange Default range defined by Mojang
     * @return
     */
    public static int getEntityTrackingRange(Entity entity, int defaultRange)
    {
        if ( defaultRange == 0 )
        {
            return defaultRange;
        }
        SpigotWorldConfig config = entity.getWorld().spigotConfig;
        if ( entity instanceof ServerPlayerEntity )
        {
            return config.playerTrackingRange;
        } else if ( entity.activationType == ActivationRange.ActivationType.MONSTER || entity.activationType == ActivationRange.ActivationType.RAIDER )
        {
            return config.monsterTrackingRange;
        } else if ( entity instanceof GhastEntity )
        {
            if ( config.monsterTrackingRange > config.monsterActivationRange )
            {
                return config.monsterTrackingRange;
            } else
            {
                return config.monsterActivationRange;
            }
        } else if ( entity.activationType == ActivationRange.ActivationType.ANIMAL )
        {
            return config.animalTrackingRange;
        } else if ( entity instanceof ItemFrameEntity || entity instanceof PaintingEntity || entity instanceof ItemEntity || entity instanceof ExperienceOrbEntity )
        {
            return config.miscTrackingRange;
        } else if ( entity instanceof DisplayEntity )
        {
            return config.displayTrackingRange;
        } else
        {
            return config.otherTrackingRange;
        }
    }
}
