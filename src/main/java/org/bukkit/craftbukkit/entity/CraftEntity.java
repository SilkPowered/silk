package org.bukkit.craftbukkit.entity;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import cx.rain.silk.mixins.bridge.entity.IEntityBridge;
import cx.rain.silk.mixins.interfaces.entity.IEntityMixin;
import cx.rain.silk.mixins.interfaces.world.IWorldMixin;
import net.md_5.bungee.api.chat.BaseComponent;
import net.minecraft.entity.*;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.decoration.*;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.projectile.thrown.*;
import net.minecraft.entity.vehicle.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import net.minecraft.util.math.Box;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftSound;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.block.CraftBlock;
import org.bukkit.craftbukkit.persistence.CraftPersistentDataContainer;
import org.bukkit.craftbukkit.persistence.CraftPersistentDataTypeRegistry;
import org.bukkit.craftbukkit.util.CraftChatMessage;
import org.bukkit.craftbukkit.util.CraftLocation;
import org.bukkit.craftbukkit.util.CraftSpawnCategory;
import org.bukkit.craftbukkit.util.CraftVector;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public abstract class CraftEntity implements org.bukkit.entity.Entity {
    private static PermissibleBase perm;
    private static final CraftPersistentDataTypeRegistry DATA_TYPE_REGISTRY = new CraftPersistentDataTypeRegistry();

    protected final CraftServer server;
    protected Entity entity;
    private EntityDamageEvent lastDamageEvent;
    private final CraftPersistentDataContainer persistentDataContainer = new CraftPersistentDataContainer(DATA_TYPE_REGISTRY);

    public CraftEntity(final CraftServer server, final Entity entity) {
        this.server = server;
        this.entity = entity;
    }

    public static CraftEntity getEntity(CraftServer server, Entity entity) {
        /*
         * Order is *EXTREMELY* important -- keep it right! =D
         */
        // CHECKSTYLE:OFF
        if (entity instanceof LivingEntity) {
            // Players
            if (entity instanceof PlayerEntity) {
                if (entity instanceof ServerPlayerEntity) {
                    return new CraftPlayer(server, (ServerPlayerEntity) entity);
                } else {
                    return new CraftHumanEntity(server, (PlayerEntity) entity);
                }
            }
            // Water Animals
            else if (entity instanceof WaterCreatureEntity) {
                if (entity instanceof SquidEntity) {
                    if (entity instanceof GlowSquidEntity) {
                        return new CraftGlowSquid(server, (GlowSquidEntity) entity);
                    } else {
                        return new CraftSquid(server, (SquidEntity) entity);
                    }
                } else if (entity instanceof FishEntity) {
                    if (entity instanceof CodEntity) {
                        return new CraftCod(server, (CodEntity) entity);
                    } else if (entity instanceof PufferfishEntity) {
                        return new CraftPufferFish(server, (PufferfishEntity) entity);
                    } else if (entity instanceof SalmonEntity) {
                        return new CraftSalmon(server, (SalmonEntity) entity);
                    } else if (entity instanceof TropicalFishEntity) {
                        return new CraftTropicalFish(server, (TropicalFishEntity) entity);
                    } else if (entity instanceof TadpoleEntity) {
                        return new CraftTadpole(server, (TadpoleEntity) entity);
                    } else {
                        return new CraftFish(server, (FishEntity) entity);
                    }
                } else if (entity instanceof DolphinEntity) {
                    return new CraftDolphin(server, (DolphinEntity) entity);
                } else {
                    return new CraftWaterMob(server, (WaterCreatureEntity) entity);
                }
            } else if (entity instanceof PathAwareEntity) {
                // Animals
                if (entity instanceof AnimalEntity) {
                    if (entity instanceof ChickenEntity) {
                        return new CraftChicken(server, (ChickenEntity) entity);
                    } else if (entity instanceof CowEntity) {
                        if (entity instanceof MooshroomEntity) {
                            return new CraftMushroomCow(server, (MooshroomEntity) entity);
                        } else {
                            return new CraftCow(server, (CowEntity) entity);
                        }
                    } else if (entity instanceof PigEntity) {
                        return new CraftPig(server, (PigEntity) entity);
                    } else if (entity instanceof TameableEntity) {
                        if (entity instanceof WolfEntity) {
                            return new CraftWolf(server, (WolfEntity) entity);
                        } else if (entity instanceof CatEntity) {
                            return new CraftCat(server, (CatEntity) entity);
                        } else if (entity instanceof ParrotEntity) {
                            return new CraftParrot(server, (ParrotEntity) entity);
                        }
                    } else if (entity instanceof SheepEntity) {
                        return new CraftSheep(server, (SheepEntity) entity);
                    } else if (entity instanceof AbstractHorseEntity) {
                        if (entity instanceof AbstractDonkeyEntity) {
                            if (entity instanceof DonkeyEntity) {
                                return new CraftDonkey(server, (DonkeyEntity) entity);
                            } else if (entity instanceof MuleEntity) {
                                return new CraftMule(server, (MuleEntity) entity);
                            } else if (entity instanceof TraderLlamaEntity) {
                                return new CraftTraderLlama(server, (TraderLlamaEntity) entity);
                            } else if (entity instanceof LlamaEntity) {
                                return new CraftLlama(server, (LlamaEntity) entity);
                            }
                        } else if (entity instanceof HorseEntity) {
                            return new CraftHorse(server, (HorseEntity) entity);
                        } else if (entity instanceof SkeletonHorseEntity) {
                            return new CraftSkeletonHorse(server, (SkeletonHorseEntity) entity);
                        } else if (entity instanceof ZombieHorseEntity) {
                            return new CraftZombieHorse(server, (ZombieHorseEntity) entity);
                        } else if (entity instanceof CamelEntity) {
                            return new CraftCamel(server, (CamelEntity) entity);
                        }
                    } else if (entity instanceof RabbitEntity) {
                        return new CraftRabbit(server, (RabbitEntity) entity);
                    } else if (entity instanceof PolarBearEntity) {
                        return new CraftPolarBear(server, (PolarBearEntity) entity);
                    } else if (entity instanceof TurtleEntity) {
                        return new CraftTurtle(server, (TurtleEntity) entity);
                    } else if (entity instanceof OcelotEntity) {
                        return new CraftOcelot(server, (OcelotEntity) entity);
                    } else if (entity instanceof PandaEntity) {
                        return new CraftPanda(server, (PandaEntity) entity);
                    } else if (entity instanceof FoxEntity) {
                        return new CraftFox(server, (FoxEntity) entity);
                    } else if (entity instanceof BeeEntity) {
                        return new CraftBee(server, (BeeEntity) entity);
                    } else if (entity instanceof HoglinEntity) {
                        return new CraftHoglin(server, (HoglinEntity) entity);
                    } else if (entity instanceof StriderEntity) {
                        return new CraftStrider(server, (StriderEntity) entity);
                    } else if (entity instanceof AxolotlEntity) {
                        return new CraftAxolotl(server, (AxolotlEntity) entity);
                    } else if (entity instanceof GoatEntity) {
                        return new CraftGoat(server, (GoatEntity) entity);
                    } else if (entity instanceof FrogEntity) {
                        return new CraftFrog(server, (FrogEntity) entity);
                    } else if (entity instanceof SnifferEntity) {
                        return new CraftSniffer(server, (SnifferEntity) entity);
                    } else {
                        return new CraftAnimals(server, (AnimalEntity) entity);
                    }
                }
                // Monsters
                else if (entity instanceof MobEntity) {
                    if (entity instanceof ZombieEntity) {
                        if (entity instanceof ZombifiedPiglinEntity) {
                            return new CraftPigZombie(server, (ZombifiedPiglinEntity) entity);
                        } else if (entity instanceof HuskEntity) {
                            return new CraftHusk(server, (HuskEntity) entity);
                        } else if (entity instanceof ZombieVillagerEntity) {
                            return new CraftVillagerZombie(server, (ZombieVillagerEntity) entity);
                        } else if (entity instanceof DrownedEntity) {
                            return new CraftDrowned(server, (DrownedEntity) entity);
                        } else {
                            return new CraftZombie(server, (ZombieEntity) entity);
                        }
                    } else if (entity instanceof CreeperEntity) {
                        return new CraftCreeper(server, (CreeperEntity) entity);
                    } else if (entity instanceof EndermanEntity) {
                        return new CraftEnderman(server, (EndermanEntity) entity);
                    } else if (entity instanceof SilverfishEntity) {
                        return new CraftSilverfish(server, (SilverfishEntity) entity);
                    } else if (entity instanceof GiantEntity) {
                        return new CraftGiant(server, (GiantEntity) entity);
                    } else if (entity instanceof AbstractSkeletonEntity) {
                        if (entity instanceof StrayEntity) {
                            return new CraftStray(server, (StrayEntity) entity);
                        } else if (entity instanceof WitherSkeletonEntity) {
                            return new CraftWitherSkeleton(server, (WitherSkeletonEntity) entity);
                        } else if (entity instanceof SkeletonEntity) {
                            return new CraftSkeleton(server, (SkeletonEntity) entity);
                        }
                    } else if (entity instanceof BlazeEntity) {
                        return new CraftBlaze(server, (BlazeEntity) entity);
                    } else if (entity instanceof WitchEntity) {
                        return new CraftWitch(server, (WitchEntity) entity);
                    } else if (entity instanceof WitherEntity) {
                        return new CraftWither(server, (WitherEntity) entity);
                    } else if (entity instanceof SpiderEntity) {
                        if (entity instanceof CaveSpiderEntity) {
                            return new CraftCaveSpider(server, (CaveSpiderEntity) entity);
                        } else {
                            return new CraftSpider(server, (SpiderEntity) entity);
                        }
                    } else if (entity instanceof EndermiteEntity) {
                        return new CraftEndermite(server, (EndermiteEntity) entity);
                    } else if (entity instanceof GuardianEntity) {
                        if (entity instanceof ElderGuardianEntity) {
                            return new CraftElderGuardian(server, (ElderGuardianEntity) entity);
                        } else {
                            return new CraftGuardian(server, (GuardianEntity) entity);
                        }
                    } else if (entity instanceof VexEntity) {
                        return new CraftVex(server, (VexEntity) entity);
                    } else if (entity instanceof IllagerEntity) {
                        if (entity instanceof SpellcastingIllagerEntity) {
                            if (entity instanceof EvokerEntity) {
                                return new CraftEvoker(server, (EvokerEntity) entity);
                            } else if (entity instanceof IllusionerEntity) {
                                return new CraftIllusioner(server, (IllusionerEntity) entity);
                            } else {
                                return new CraftSpellcaster(server, (SpellcastingIllagerEntity) entity);
                            }
                        } else if (entity instanceof VindicatorEntity) {
                            return new CraftVindicator(server, (VindicatorEntity) entity);
                        } else if (entity instanceof PillagerEntity) {
                            return new CraftPillager(server, (PillagerEntity) entity);
                        } else {
                            return new CraftIllager(server, (IllagerEntity) entity);
                        }
                    } else if (entity instanceof RavagerEntity) {
                        return new CraftRavager(server, (RavagerEntity) entity);
                    } else if (entity instanceof AbstractPiglinEntity) {
                        if (entity instanceof PiglinEntity) {
                            return new CraftPiglin(server, (PiglinEntity) entity);
                        }
                        else if (entity instanceof PiglinBruteEntity) {
                            return new CraftPiglinBrute(server, (PiglinBruteEntity) entity);
                        } else {
                            return new CraftPiglinAbstract(server, (AbstractPiglinEntity) entity);
                        }
                    } else if (entity instanceof ZoglinEntity) {
                        return new CraftZoglin(server, (ZoglinEntity) entity);
                    } else if (entity instanceof WardenEntity) {
                        return new CraftWarden(server, (WardenEntity) entity);
                    } else {
                        return new CraftMonster(server, (MobEntity) entity);
                    }
                } else if (entity instanceof GolemEntity) {
                    if (entity instanceof SnowGolemEntity) {
                        return new CraftSnowman(server, (SnowGolemEntity) entity);
                    } else if (entity instanceof IronGolemEntity) {
                        return new CraftIronGolem(server, (IronGolemEntity) entity);
                    } else if (entity instanceof ShulkerEntity) {
                        return new CraftShulker(server, (ShulkerEntity) entity);
                    }
                } else if (entity instanceof MerchantEntity) {
                    if (entity instanceof VillagerEntity) {
                        return new CraftVillager(server, (VillagerEntity) entity);
                    } else if (entity instanceof WanderingTraderEntity) {
                        return new CraftWanderingTrader(server, (WanderingTraderEntity) entity);
                    } else {
                        return new CraftAbstractVillager(server, (MerchantEntity) entity);
                    }
                } else if (entity instanceof AllayEntity) {
                    return new CraftAllay(server, (AllayEntity) entity);
                } else {
                    return new CraftCreature(server, (PathAwareEntity) entity);
                }
            }
            // Slimes are a special (and broken) case
            else if (entity instanceof SlimeEntity) {
                if (entity instanceof MagmaCubeEntity) {
                    return new CraftMagmaCube(server, (MagmaCubeEntity) entity);
                } else {
                    return new CraftSlime(server, (SlimeEntity) entity);
                }
            }
            // Flying
            else if (entity instanceof FlyingEntity) {
                if (entity instanceof GhastEntity) {
                    return new CraftGhast(server, (GhastEntity) entity);
                } else if (entity instanceof PhantomEntity) {
                    return new CraftPhantom(server, (PhantomEntity) entity);
                } else {
                    return new CraftFlying(server, (FlyingEntity) entity);
                }
            } else if (entity instanceof EnderDragonEntity) {
                return new CraftEnderDragon(server, (EnderDragonEntity) entity);
            }
            // Ambient
            else if (entity instanceof AmbientEntity) {
                if (entity instanceof BatEntity) {
                    return new CraftBat(server, (BatEntity) entity);
                } else {
                    return new CraftAmbient(server, (AmbientEntity) entity);
                }
            } else if (entity instanceof ArmorStandEntity) {
                return new CraftArmorStand(server, (ArmorStandEntity) entity);
            } else {
                return new CraftLivingEntity(server, (LivingEntity) entity);
            }
        } else if (entity instanceof EnderDragonPart) {
            EnderDragonPart part = (EnderDragonPart) entity;
            if (part.owner instanceof EnderDragonEntity) {
                return new CraftEnderDragonPart(server, (EnderDragonPart) entity);
            } else {
                return new CraftComplexPart(server, (EnderDragonPart) entity);
            }
        } else if (entity instanceof ExperienceOrbEntity) {
            return new CraftExperienceOrb(server, (ExperienceOrbEntity) entity);
        } else if (entity instanceof ArrowEntity) {
            return new CraftTippedArrow(server, (ArrowEntity) entity);
        } else if (entity instanceof SpectralArrowEntity) {
            return new CraftSpectralArrow(server, (SpectralArrowEntity) entity);
        } else if (entity instanceof PersistentProjectileEntity) {
            if (entity instanceof TridentEntity) {
                return new CraftTrident(server, (TridentEntity) entity);
            } else {
                return new CraftArrow(server, (PersistentProjectileEntity) entity);
            }
        } else if (entity instanceof BoatEntity) {
            if (entity instanceof ChestBoatEntity) {
                return new CraftChestBoat(server, (ChestBoatEntity) entity);
            } else {
                return new CraftBoat(server, (BoatEntity) entity);
            }
        } else if (entity instanceof ThrownItemEntity) {
            if (entity instanceof EggEntity) {
                return new CraftEgg(server, (EggEntity) entity);
            } else if (entity instanceof SnowballEntity) {
                return new CraftSnowball(server, (SnowballEntity) entity);
            } else if (entity instanceof PotionEntity) {
                return new CraftThrownPotion(server, (PotionEntity) entity);
            } else if (entity instanceof EnderPearlEntity) {
                return new CraftEnderPearl(server, (EnderPearlEntity) entity);
            } else if (entity instanceof ExperienceBottleEntity) {
                return new CraftThrownExpBottle(server, (ExperienceBottleEntity) entity);
            }
        } else if (entity instanceof FallingBlockEntity) {
            return new CraftFallingBlock(server, (FallingBlockEntity) entity);
        } else if (entity instanceof AbstractFireballEntity) {
            if (entity instanceof SmallFireballEntity) {
                return new CraftSmallFireball(server, (SmallFireballEntity) entity);
            } else if (entity instanceof FireballEntity) {
                return new CraftLargeFireball(server, (FireballEntity) entity);
            } else {
                return new CraftFireball(server, (AbstractFireballEntity) entity);
            }
        } else if (entity instanceof WitherSkullEntity) {
            return new CraftWitherSkull(server, (WitherSkullEntity) entity);
        } else if (entity instanceof DragonFireballEntity) {
            return new CraftDragonFireball(server, (DragonFireballEntity) entity);
        } else if (entity instanceof EyeOfEnderEntity) {
            return new CraftEnderSignal(server, (EyeOfEnderEntity) entity);
        } else if (entity instanceof EndCrystalEntity) {
            return new CraftEnderCrystal(server, (EndCrystalEntity) entity);
        } else if (entity instanceof FishingBobberEntity) {
            return new CraftFishHook(server, (FishingBobberEntity) entity);
        } else if (entity instanceof ItemEntity) {
            return new CraftItem(server, (ItemEntity) entity);
        } else if (entity instanceof LightningEntity) {
            return new CraftLightningStrike(server, (LightningEntity) entity);
        } else if (entity instanceof AbstractMinecartEntity) {
            if (entity instanceof FurnaceMinecartEntity) {
                return new CraftMinecartFurnace(server, (FurnaceMinecartEntity) entity);
            } else if (entity instanceof ChestMinecartEntity) {
                return new CraftMinecartChest(server, (ChestMinecartEntity) entity);
            } else if (entity instanceof TntMinecartEntity) {
                return new CraftMinecartTNT(server, (TntMinecartEntity) entity);
            } else if (entity instanceof HopperMinecartEntity) {
                return new CraftMinecartHopper(server, (HopperMinecartEntity) entity);
            } else if (entity instanceof SpawnerMinecartEntity) {
                return new CraftMinecartMobSpawner(server, (SpawnerMinecartEntity) entity);
            } else if (entity instanceof MinecartEntity) {
                return new CraftMinecartRideable(server, (MinecartEntity) entity);
            } else if (entity instanceof CommandBlockMinecartEntity) {
                return new CraftMinecartCommand(server, (CommandBlockMinecartEntity) entity);
            }
        } else if (entity instanceof AbstractDecorationEntity) {
            if (entity instanceof PaintingEntity) {
                return new CraftPainting(server, (PaintingEntity) entity);
            } else if (entity instanceof ItemFrameEntity) {
                if (entity instanceof GlowItemFrameEntity) {
                    return new CraftGlowItemFrame(server, (GlowItemFrameEntity) entity);
                } else {
                    return new CraftItemFrame(server, (ItemFrameEntity) entity);
                }
            } else if (entity instanceof LeashKnotEntity) {
                return new CraftLeash(server, (LeashKnotEntity) entity);
            } else {
                return new CraftHanging(server, (AbstractDecorationEntity) entity);
            }
        } else if (entity instanceof TntEntity) {
            return new CraftTNTPrimed(server, (TntEntity) entity);
        } else if (entity instanceof FireworkRocketEntity) {
            return new CraftFirework(server, (FireworkRocketEntity) entity);
        } else if (entity instanceof ShulkerBulletEntity) {
            return new CraftShulkerBullet(server, (ShulkerBulletEntity) entity);
        } else if (entity instanceof AreaEffectCloudEntity) {
            return new CraftAreaEffectCloud(server, (AreaEffectCloudEntity) entity);
        } else if (entity instanceof EvokerFangsEntity) {
            return new CraftEvokerFangs(server, (EvokerFangsEntity) entity);
        } else if (entity instanceof LlamaSpitEntity) {
            return new CraftLlamaSpit(server, (LlamaSpitEntity) entity);
        } else if (entity instanceof MarkerEntity) {
            return new CraftMarker(server, (MarkerEntity) entity);
        } else if (entity instanceof InteractionEntity) {
            return new CraftInteraction(server, (InteractionEntity) entity);
        } else if (entity instanceof DisplayEntity) {
            if (entity instanceof DisplayEntity.BlockDisplayEntity) {
                return new CraftBlockDisplay(server, (DisplayEntity.BlockDisplayEntity) entity);
            } else if (entity instanceof DisplayEntity.ItemDisplayEntity) {
                return new CraftItemDisplay(server, (DisplayEntity.ItemDisplayEntity) entity);
            } else if (entity instanceof DisplayEntity.TextDisplayEntity) {
                return new CraftTextDisplay(server, (DisplayEntity.TextDisplayEntity) entity);
            } else {
                return new CraftDisplay(server, (DisplayEntity) entity);
            }
        }
        // CHECKSTYLE:ON

        throw new AssertionError("Unknown entity " + (entity == null ? null : entity.getClass()));
    }

    @Override
    public Location getLocation() {
        return CraftLocation.toBukkit(entity.getPos(), getWorld(), entity.getYaw(), entity.getPitch());
    }

    @Override
    public Location getLocation(Location loc) {
        if (loc != null) {
            loc.setWorld(getWorld());
            loc.setX(entity.getX());
            loc.setY(entity.getY());
            loc.setZ(entity.getZ());
            loc.setYaw(entity.getYaw());
            loc.setPitch(entity.getPitch());
        }

        return loc;
    }

    @Override
    public Vector getVelocity() {
        return CraftVector.toBukkit(entity.getVelocity());
    }

    @Override
    public void setVelocity(Vector velocity) {
        Preconditions.checkArgument(velocity != null, "velocity");
        velocity.checkFinite();
        entity.setVelocity(CraftVector.toNMS(velocity));
        entity.velocityModified = true;
    }

    @Override
    public double getHeight() {
        return getHandle().getHeight();
    }

    @Override
    public double getWidth() {
        return getHandle().getWidth();
    }

    @Override
    public BoundingBox getBoundingBox() {
        Box bb = getHandle().getBoundingBox();
        return new BoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ);
    }

    @Override
    public boolean isOnGround() {
        if (entity instanceof PersistentProjectileEntity) {
            return ((PersistentProjectileEntity) entity).inGround;
        }
        return entity.isOnGround();
    }

    @Override
    public boolean isInWater() {
        return entity.isTouchingWater();
    }

    @Override
    public World getWorld() {
        return ((IWorldMixin) entity.getWorld()).getWorld();
    }

    @Override
    public void setRotation(float yaw, float pitch) {
        NumberConversions.checkFinite(pitch, "pitch not finite");
        NumberConversions.checkFinite(yaw, "yaw not finite");

        yaw = Location.normalizeYaw(yaw);
        pitch = Location.normalizePitch(pitch);

        entity.setYaw(yaw);
        entity.setPitch(pitch);
        entity.prevYaw = yaw;
        entity.prevPitch = pitch;
        entity.setHeadYaw(yaw);
    }

    @Override
    public boolean teleport(Location location) {
        return teleport(location, TeleportCause.PLUGIN);
    }

    @Override
    public boolean teleport(Location location, TeleportCause cause) {
        Preconditions.checkArgument(location != null, "location cannot be null");
        location.checkFinite();

        if (entity.hasPassengers() || entity.isRemoved()) {
            return false;
        }

        // If this entity is riding another entity, we must dismount before teleporting.
        entity.stopRiding();

        // Let the server handle cross world teleports
        if (location.getWorld() != null && !location.getWorld().equals(getWorld())) {
            // Prevent teleportation to an other world during world generation
            Preconditions.checkState(!((IEntityBridge) entity).silk$getGeneration(), "Cannot teleport entity to an other world during world generation");
            ((IEntityMixin) entity).teleportTo(((CraftWorld) location.getWorld()).getHandle(), CraftLocation.toPosition(location));
            return true;
        }

        // entity.setLocation() throws no event, and so cannot be cancelled
        entity.updatePositionAndAngles(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        // SPIGOT-619: Force sync head rotation also
        entity.setYaw(location.getYaw());

        return true;
    }

    @Override
    public boolean teleport(org.bukkit.entity.Entity destination) {
        return teleport(destination.getLocation());
    }

    @Override
    public boolean teleport(org.bukkit.entity.Entity destination, TeleportCause cause) {
        return teleport(destination.getLocation(), cause);
    }

    @Override
    public List<org.bukkit.entity.Entity> getNearbyEntities(double x, double y, double z) {
        Preconditions.checkState(!((IEntityBridge) entity).silk$getGeneration(), "Cannot get nearby entities during world generation");
        org.spigotmc.AsyncCatcher.catchOp("getNearbyEntities"); // Spigot

        List<Entity> notchEntityList = entity.getEntityWorld().getOtherEntities(entity, entity.getBoundingBox().expand(x, y, z), Predicates.alwaysTrue());
        List<org.bukkit.entity.Entity> bukkitEntityList = new java.util.ArrayList<org.bukkit.entity.Entity>(notchEntityList.size());

        for (Entity e : notchEntityList) {
            bukkitEntityList.add(((IEntityMixin) e).getBukkitEntity());
        }
        return bukkitEntityList;
    }

    @Override
    public int getEntityId() {
        return entity.getId();
    }

    @Override
    public int getFireTicks() {
        return entity.getFireTicks();
    }

    @Override
    public int getMaxFireTicks() {
        return entity.getBurningDuration();
    }

    @Override
    public void setFireTicks(int ticks) {
        entity.setFireTicks(ticks);
    }

    @Override
    public void setVisualFire(boolean fire) {
        getHandle().hasVisualFire = fire;
    }

    @Override
    public boolean isVisualFire() {
        return getHandle().hasVisualFire;
    }

    @Override
    public int getFreezeTicks() {
        return getHandle().getFrozenTicks();
    }

    @Override
    public int getMaxFreezeTicks() {
        return getHandle().getMinFreezeDamageTicks();
    }

    @Override
    public void setFreezeTicks(int ticks) {
        Preconditions.checkArgument(0 <= ticks, "Ticks (%s) cannot be less than 0", ticks);

        getHandle().setFrozenTicks(ticks);
    }

    @Override
    public boolean isFrozen() {
        return getHandle().isFrozen();
    }

    @Override
    public void remove() {
        entity.discard();
    }

    @Override
    public boolean isDead() {
        return !entity.isAlive();
    }

    @Override
    public boolean isValid() {
        return entity.isAlive() && entity.isValid && entity.isChunkLoaded();
    }

    @Override
    public Server getServer() {
        return server;
    }

    @Override
    public boolean isPersistent() {
        return entity.persist;
    }

    @Override
    public void setPersistent(boolean persistent) {
        entity.persist = persistent;
    }

    public Vector getMomentum() {
        return getVelocity();
    }

    public void setMomentum(Vector value) {
        setVelocity(value);
    }

    @Override
    public org.bukkit.entity.Entity getPassenger() {
        return isEmpty() ? null : getHandle().passengers.get(0).getBukkitEntity();
    }

    @Override
    public boolean setPassenger(org.bukkit.entity.Entity passenger) {
        Preconditions.checkArgument(!this.equals(passenger), "Entity cannot ride itself.");
        if (passenger instanceof CraftEntity) {
            eject();
            return ((CraftEntity) passenger).getHandle().startRiding(getHandle());
        } else {
            return false;
        }
    }

    @Override
    public List<org.bukkit.entity.Entity> getPassengers() {
        return Lists.newArrayList(Lists.transform(getHandle().passengers, (Function<Entity, org.bukkit.entity.Entity>) input -> input.getBukkitEntity()));
    }

    @Override
    public boolean addPassenger(org.bukkit.entity.Entity passenger) {
        Preconditions.checkArgument(passenger != null, "Entity passenger cannot be null");
        Preconditions.checkArgument(!this.equals(passenger), "Entity cannot ride itself.");

        return ((CraftEntity) passenger).getHandle().startRiding(getHandle(), true);
    }

    @Override
    public boolean removePassenger(org.bukkit.entity.Entity passenger) {
        Preconditions.checkArgument(passenger != null, "Entity passenger cannot be null");

        ((CraftEntity) passenger).getHandle().stopRiding();
        return true;
    }

    @Override
    public boolean isEmpty() {
        return !getHandle().isVehicle();
    }

    @Override
    public boolean eject() {
        if (isEmpty()) {
            return false;
        }

        getHandle().ejectPassengers();
        return true;
    }

    @Override
    public float getFallDistance() {
        return getHandle().fallDistance;
    }

    @Override
    public void setFallDistance(float distance) {
        getHandle().fallDistance = distance;
    }

    @Override
    public void setLastDamageCause(EntityDamageEvent event) {
        lastDamageEvent = event;
    }

    @Override
    public EntityDamageEvent getLastDamageCause() {
        return lastDamageEvent;
    }

    @Override
    public UUID getUniqueId() {
        return getHandle().getUUID();
    }

    @Override
    public int getTicksLived() {
        return getHandle().tickCount;
    }

    @Override
    public void setTicksLived(int value) {
        Preconditions.checkArgument(value > 0, "Age value (%s) must be greater than 0", value);
        getHandle().tickCount = value;
    }

    public Entity getHandle() {
        return entity;
    }

    @Override
    public void playEffect(EntityEffect type) {
        Preconditions.checkArgument(type != null, "type");
        Preconditions.checkState(!entity.generation, "Cannot play effect during world generation");

        if (type.getApplicable().isInstance(this)) {
            this.getHandle().level().broadcastEntityEvent(getHandle(), type.getData());
        }
    }

    @Override
    public Sound getSwimSound() {
        return CraftSound.getBukkit(getHandle().getSwimSound0());
    }

    @Override
    public Sound getSwimSplashSound() {
        return CraftSound.getBukkit(getHandle().getSwimSplashSound0());
    }

    @Override
    public Sound getSwimHighSpeedSplashSound() {
        return CraftSound.getBukkit(getHandle().getSwimHighSpeedSplashSound0());
    }

    public void setHandle(final Entity entity) {
        this.entity = entity;
    }

    @Override
    public String toString() {
        return "CraftEntity{" + "id=" + getEntityId() + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CraftEntity other = (CraftEntity) obj;
        return (this.getEntityId() == other.getEntityId());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.getEntityId();
        return hash;
    }

    @Override
    public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
        server.getEntityMetadata().setMetadata(this, metadataKey, newMetadataValue);
    }

    @Override
    public List<MetadataValue> getMetadata(String metadataKey) {
        return server.getEntityMetadata().getMetadata(this, metadataKey);
    }

    @Override
    public boolean hasMetadata(String metadataKey) {
        return server.getEntityMetadata().hasMetadata(this, metadataKey);
    }

    @Override
    public void removeMetadata(String metadataKey, Plugin owningPlugin) {
        server.getEntityMetadata().removeMetadata(this, metadataKey, owningPlugin);
    }

    @Override
    public boolean isInsideVehicle() {
        return getHandle().isPassenger();
    }

    @Override
    public boolean leaveVehicle() {
        if (!isInsideVehicle()) {
            return false;
        }

        getHandle().stopRiding();
        return true;
    }

    @Override
    public org.bukkit.entity.Entity getVehicle() {
        if (!isInsideVehicle()) {
            return null;
        }

        return getHandle().getVehicle().getBukkitEntity();
    }

    @Override
    public void setCustomName(String name) {
        // sane limit for name length
        if (name != null && name.length() > 256) {
            name = name.substring(0, 256);
        }

        getHandle().setCustomName(CraftChatMessage.fromStringOrNull(name));
    }

    @Override
    public String getCustomName() {
        IChatBaseComponent name = getHandle().getCustomName();

        if (name == null) {
            return null;
        }

        return CraftChatMessage.fromComponent(name);
    }

    @Override
    public void setCustomNameVisible(boolean flag) {
        getHandle().setCustomNameVisible(flag);
    }

    @Override
    public boolean isCustomNameVisible() {
        return getHandle().isCustomNameVisible();
    }

    @Override
    public void setVisibleByDefault(boolean visible) {
        if (getHandle().visibleByDefault != visible) {
            if (visible) {
                // Making visible by default, reset and show to all players
                for (Player player : server.getOnlinePlayers()) {
                    ((CraftPlayer) player).resetAndShowEntity(this);
                }
            } else {
                // Hiding by default, reset and hide from all players
                for (Player player : server.getOnlinePlayers()) {
                    ((CraftPlayer) player).resetAndHideEntity(this);
                }
            }

            getHandle().visibleByDefault = visible;
        }
    }

    @Override
    public boolean isVisibleByDefault() {
        return getHandle().visibleByDefault;
    }

    @Override
    public void sendMessage(String message) {

    }

    @Override
    public void sendMessage(String... messages) {

    }

    @Override
    public void sendMessage(UUID sender, String message) {
        this.sendMessage(message); // Most entities don't know about senders
    }

    @Override
    public void sendMessage(UUID sender, String... messages) {
        this.sendMessage(messages); // Most entities don't know about senders
    }

    @Override
    public String getName() {
        return CraftChatMessage.fromComponent(getHandle().getName());
    }

    @Override
    public boolean isPermissionSet(String name) {
        return getPermissibleBase().isPermissionSet(name);
    }

    @Override
    public boolean isPermissionSet(Permission perm) {
        return CraftEntity.getPermissibleBase().isPermissionSet(perm);
    }

    @Override
    public boolean hasPermission(String name) {
        return getPermissibleBase().hasPermission(name);
    }

    @Override
    public boolean hasPermission(Permission perm) {
        return getPermissibleBase().hasPermission(perm);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
        return getPermissibleBase().addAttachment(plugin, name, value);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin) {
        return getPermissibleBase().addAttachment(plugin);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) {
        return getPermissibleBase().addAttachment(plugin, name, value, ticks);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
        return getPermissibleBase().addAttachment(plugin, ticks);
    }

    @Override
    public void removeAttachment(PermissionAttachment attachment) {
        getPermissibleBase().removeAttachment(attachment);
    }

    @Override
    public void recalculatePermissions() {
        getPermissibleBase().recalculatePermissions();
    }

    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return getPermissibleBase().getEffectivePermissions();
    }

    @Override
    public boolean isOp() {
        return getPermissibleBase().isOp();
    }

    @Override
    public void setOp(boolean value) {
        getPermissibleBase().setOp(value);
    }

    @Override
    public void setGlowing(boolean flag) {
        getHandle().setGlowing(flag);
    }

    @Override
    public boolean isGlowing() {
        return getHandle().isGlowing();
    }

    @Override
    public void setInvulnerable(boolean flag) {
        getHandle().setInvulnerable(flag);
    }

    @Override
    public boolean isInvulnerable() {
        return getHandle().isInvulnerableTo(getHandle().getDamageSources().generic());
    }

    @Override
    public boolean isSilent() {
        return getHandle().isSilent();
    }

    @Override
    public void setSilent(boolean flag) {
        getHandle().setSilent(flag);
    }

    @Override
    public boolean hasGravity() {
        return !getHandle().hasNoGravity();
    }

    @Override
    public void setGravity(boolean gravity) {
        getHandle().setNoGravity(!gravity);
    }

    @Override
    public int getPortalCooldown() {
        return getHandle().portalCooldown;
    }

    @Override
    public void setPortalCooldown(int cooldown) {
        getHandle().portalCooldown = cooldown;
    }

    @Override
    public Set<String> getScoreboardTags() {
        return getHandle().getCommandTags();
    }

    @Override
    public boolean addScoreboardTag(String tag) {
        return getHandle().addCommandTag(tag);
    }

    @Override
    public boolean removeScoreboardTag(String tag) {
        return getHandle().removeScoreboardTag(tag);
    }

    @Override
    public PistonMoveReaction getPistonMoveReaction() {
        return PistonMoveReaction.getById(getHandle().getPistonBehavior().ordinal());
    }

    @Override
    public BlockFace getFacing() {
        // Use this method over getDirection because it handles boats and minecarts.
        return CraftBlock.notchToBlockFace(getHandle().getMovementDirection());
    }

    @Override
    public CraftPersistentDataContainer getPersistentDataContainer() {
        return persistentDataContainer;
    }

    @Override
    public Pose getPose() {
        return Pose.values()[getHandle().getPose().ordinal()];
    }

    @Override
    public SpawnCategory getSpawnCategory() {
        return CraftSpawnCategory.toBukkit(getHandle().getType().getSpawnGroup());
    }

    public void storeBukkitValues(NbtCompound c) {
        if (!this.persistentDataContainer.isEmpty()) {
            c.put("BukkitValues", this.persistentDataContainer.toTagCompound());
        }
    }

    public void readBukkitValues(NbtCompound c) {
        NbtElement base = c.get("BukkitValues");
        if (base instanceof NbtCompound) {
            this.persistentDataContainer.putAll((NbtCompound) base);
        }
    }

    protected NbtCompound save() {
        NbtCompound nbttagcompound = new NbtCompound();

        nbttagcompound.putString("id", getHandle().getSavedEntityId());
        getHandle().writeNbt(nbttagcompound);

        return nbttagcompound;
    }

    // re-sends the spawn entity packet to updated values which cannot be updated otherwise
    protected void update() {
        if (!getHandle().isAlive()) {
            return;
        }

        ServerWorld world = ((CraftWorld) getWorld()).getHandle();
        ThreadedAnvilChunkStorage.EntityTracker entityTracker = world.getChunkManager().threadedAnvilChunkStorage.entityTrackers.get(getEntityId());

        if (entityTracker == null) {
            return;
        }

        entityTracker.sendToOtherNearbyPlayers(getHandle().createSpawnPacket());
    }

    private static PermissibleBase getPermissibleBase() {
        if (perm == null) {
            perm = new PermissibleBase(new ServerOperator() {

                @Override
                public boolean isOp() {
                    return false;
                }

                @Override
                public void setOp(boolean value) {

                }
            });
        }
        return perm;
    }

    // Spigot start
    private final Spigot spigot = new Spigot() {

        @Override
        public void sendMessage(BaseComponent component) {
        }

        @Override
        public void sendMessage(BaseComponent... components) {
        }

        @Override
        public void sendMessage(UUID sender, BaseComponent... components) {
        }

        @Override
        public void sendMessage(UUID sender, BaseComponent component) {
        }
    };

    public Spigot spigot() {
        return spigot;
    }
    // Spigot end
}
