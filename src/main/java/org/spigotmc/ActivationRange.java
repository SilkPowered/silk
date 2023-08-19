package org.spigotmc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.bukkit.craftbukkit.SpigotTimings;

public class ActivationRange
{

    public enum ActivationType
    {
        MONSTER,
        ANIMAL,
        RAIDER,
        MISC;

        Box boundingBox = new Box( 0, 0, 0, 0, 0, 0 );
    }

    static Box maxBB = new Box( 0, 0, 0, 0, 0, 0 );

    /**
     * Initializes an entities type on construction to specify what group this
     * entity is in for activation ranges.
     *
     * @param entity
     * @return group id
     */
    public static ActivationType initializeEntityActivationType(Entity entity)
    {
        if ( entity instanceof RaiderEntity )
        {
            return ActivationType.RAIDER;
        } else if ( entity instanceof HostileEntity || entity instanceof SlimeEntity )
        {
            return ActivationType.MONSTER;
        } else if ( entity instanceof PathAwareEntity || entity instanceof AmbientEntity )
        {
            return ActivationType.ANIMAL;
        } else
        {
            return ActivationType.MISC;
        }
    }

    /**
     * These entities are excluded from Activation range checks.
     *
     * @param entity
     * @param config
     * @return boolean If it should always tick.
     */
    public static boolean initializeEntityActivationState(Entity entity, SpigotWorldConfig config)
    {
        if ( ( entity.activationType == ActivationType.MISC && config.miscActivationRange == 0 )
                || ( entity.activationType == ActivationType.RAIDER && config.raiderActivationRange == 0 )
                || ( entity.activationType == ActivationType.ANIMAL && config.animalActivationRange == 0 )
                || ( entity.activationType == ActivationType.MONSTER && config.monsterActivationRange == 0 )
                || entity instanceof PlayerEntity
                || entity instanceof ThrownEntity
                || entity instanceof EnderDragonEntity
                || entity instanceof EnderDragonPart
                || entity instanceof WitherEntity
                || entity instanceof ExplosiveProjectileEntity
                || entity instanceof LightningEntity
                || entity instanceof TntEntity
                || entity instanceof EndCrystalEntity
                || entity instanceof FireworkRocketEntity
                || entity instanceof TridentEntity )
        {
            return true;
        }

        return false;
    }

    /**
     * Find what entities are in range of the players in the world and set
     * active if in range.
     *
     * @param world
     */
    public static void activateEntities(World world)
    {
        SpigotTimings.entityActivationCheckTimer.startTiming();
        final int miscActivationRange = world.spigotConfig.miscActivationRange;
        final int raiderActivationRange = world.spigotConfig.raiderActivationRange;
        final int animalActivationRange = world.spigotConfig.animalActivationRange;
        final int monsterActivationRange = world.spigotConfig.monsterActivationRange;

        int maxRange = Math.max( monsterActivationRange, animalActivationRange );
        maxRange = Math.max( maxRange, raiderActivationRange );
        maxRange = Math.max( maxRange, miscActivationRange );
        maxRange = Math.min( ( world.spigotConfig.simulationDistance << 4 ) - 8, maxRange );

        for ( PlayerEntity player : world.getPlayers() )
        {
            player.activatedTick = MinecraftServer.currentTick;
            if ( world.spigotConfig.ignoreSpectatorActivation && player.G_() )
            {
                continue;
            }

            maxBB = player.cE().expand( maxRange, 256, maxRange );
            ActivationType.MISC.boundingBox = player.cE().expand( miscActivationRange, 256, miscActivationRange );
            ActivationType.RAIDER.boundingBox = player.cE().expand( raiderActivationRange, 256, raiderActivationRange );
            ActivationType.ANIMAL.boundingBox = player.cE().expand( animalActivationRange, 256, animalActivationRange );
            ActivationType.MONSTER.boundingBox = player.cE().expand( monsterActivationRange, 256, monsterActivationRange );

            world.getEntityLookup().forEachIntersects(maxBB, ActivationRange::activateEntity);
        }
        SpigotTimings.entityActivationCheckTimer.stopTiming();
    }

    /**
     * Checks for the activation state of all entities in this chunk.
     *
     * @param chunk
     */
    private static void activateEntity(Entity entity)
    {
        if ( MinecraftServer.currentTick > entity.activatedTick )
        {
            if ( entity.defaultActivationState )
            {
                entity.activatedTick = MinecraftServer.currentTick;
                return;
            }
            if ( entity.activationType.boundingBox.intersects( entity.cE() ) )
            {
                entity.activatedTick = MinecraftServer.currentTick;
            }
        }
    }

    /**
     * If an entity is not in range, do some more checks to see if we should
     * give it a shot.
     *
     * @param entity
     * @return
     */
    public static boolean checkEntityImmunities(Entity entity)
    {
        // quick checks.
        if ( entity.wasTouchingWater || entity.getFireTicks() > 0 )
        {
            return true;
        }
        if ( !( entity instanceof PersistentProjectileEntity ) )
        {
            if ( !entity.isOnGround() || !entity.passengers.isEmpty() || entity.hasVehicle() )
            {
                return true;
            }
        } else if ( !( (PersistentProjectileEntity) entity ).inGround )
        {
            return true;
        }
        // special cases.
        if ( entity instanceof LivingEntity )
        {
            LivingEntity living = (LivingEntity) entity;
            if ( /*TODO: Missed mapping? living.attackTicks > 0 || */ living.hurtTime > 0 || living.activeEffects.size() > 0 )
            {
                return true;
            }
            if ( entity instanceof PathAwareEntity && ( (PathAwareEntity) entity ).j() != null )
            {
                return true;
            }
            if ( entity instanceof VillagerEntity && ( (VillagerEntity) entity ).P_() )
            {
                return true;
            }
            if ( entity instanceof AnimalEntity )
            {
                AnimalEntity animal = (AnimalEntity) entity;
                if ( animal.h_() || animal.isInLove() )
                {
                    return true;
                }
                if ( entity instanceof SheepEntity && ( (SheepEntity) entity ).isSheared() )
                {
                    return true;
                }
            }
            if (entity instanceof CreeperEntity && ((CreeperEntity) entity).isIgnited()) { // isExplosive
                return true;
            }
        }
        // SPIGOT-6644: Otherwise the target refresh tick will be missed
        if (entity instanceof ExperienceOrbEntity) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the entity is active for this tick.
     *
     * @param entity
     * @return
     */
    public static boolean checkIfActive(Entity entity)
    {
        SpigotTimings.checkIfActiveTimer.startTiming();
        // Never safe to skip fireworks or entities not yet added to chunk
        if ( entity instanceof FireworkRocketEntity ) {
            SpigotTimings.checkIfActiveTimer.stopTiming();
            return true;
        }

        boolean isActive = entity.activatedTick >= MinecraftServer.currentTick || entity.defaultActivationState;

        // Should this entity tick?
        if ( !isActive )
        {
            if ( ( MinecraftServer.currentTick - entity.activatedTick - 1 ) % 20 == 0 )
            {
                // Check immunities every 20 ticks.
                if ( checkEntityImmunities( entity ) )
                {
                    // Triggered some sort of immunity, give 20 full ticks before we check again.
                    entity.activatedTick = MinecraftServer.currentTick + 20;
                }
                isActive = true;
            }
            // Add a little performance juice to active entities. Skip 1/4 if not immune.
        } else if ( !entity.defaultActivationState && entity.tickCount % 4 == 0 && !checkEntityImmunities( entity ) )
        {
            isActive = false;
        }
        SpigotTimings.checkIfActiveTimer.stopTiming();
        return isActive;
    }
}
