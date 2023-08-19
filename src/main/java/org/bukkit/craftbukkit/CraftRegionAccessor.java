package org.bukkit.craftbukkit;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import net.minecraft.block.AbstractRedstoneGateBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChorusFlowerBlock;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.EyeOfEnderEntity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.decoration.LeashKnotEntity;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.mob.EvokerFangsEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.entity.vehicle.ChestMinecartEntity;
import net.minecraft.entity.vehicle.CommandBlockMinecartEntity;
import net.minecraft.entity.vehicle.FurnaceMinecartEntity;
import net.minecraft.entity.vehicle.HopperMinecartEntity;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.entity.vehicle.SpawnerMinecartEntity;
import net.minecraft.entity.vehicle.TntMinecartEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeConfiguredFeatures;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.RegionAccessor;
import org.bukkit.TreeType;
import org.bukkit.block.Biome;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.CraftBlock;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.craftbukkit.potion.CraftPotionUtil;
import org.bukkit.craftbukkit.util.BlockStateListPopulator;
import org.bukkit.craftbukkit.util.CraftLocation;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;
import org.bukkit.craftbukkit.util.RandomSourceWrapper;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.AbstractSkeleton;
import org.bukkit.entity.AbstractVillager;
import org.bukkit.entity.Allay;
import org.bukkit.entity.Ambient;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Camel;
import org.bukkit.entity.Cat;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.ChestBoat;
import org.bukkit.entity.ChestedHorse;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cod;
import org.bukkit.entity.ComplexLivingEntity;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Display;
import org.bukkit.entity.Dolphin;
import org.bukkit.entity.Donkey;
import org.bukkit.entity.DragonFireball;
import org.bukkit.entity.Drowned;
import org.bukkit.entity.Egg;
import org.bukkit.entity.ElderGuardian;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.EnderSignal;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Evoker;
import org.bukkit.entity.EvokerFangs;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Fish;
import org.bukkit.entity.Fox;
import org.bukkit.entity.Frog;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.GlowItemFrame;
import org.bukkit.entity.GlowSquid;
import org.bukkit.entity.Goat;
import org.bukkit.entity.Golem;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.Hoglin;
import org.bukkit.entity.Husk;
import org.bukkit.entity.Illager;
import org.bukkit.entity.Illusioner;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LeashHitch;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LingeringPotion;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Llama;
import org.bukkit.entity.LlamaSpit;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Marker;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Mule;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Panda;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Piglin;
import org.bukkit.entity.PiglinBrute;
import org.bukkit.entity.Pillager;
import org.bukkit.entity.Player;
import org.bukkit.entity.PolarBear;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.PufferFish;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Ravager;
import org.bukkit.entity.Salmon;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Shulker;
import org.bukkit.entity.ShulkerBullet;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.SkeletonHorse;
import org.bukkit.entity.Slime;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Sniffer;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.entity.Spellcaster;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Stray;
import org.bukkit.entity.Strider;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Tadpole;
import org.bukkit.entity.Tameable;
import org.bukkit.entity.TextDisplay;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.TippedArrow;
import org.bukkit.entity.TraderLlama;
import org.bukkit.entity.Trident;
import org.bukkit.entity.TropicalFish;
import org.bukkit.entity.Turtle;
import org.bukkit.entity.Vex;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Vindicator;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.entity.Warden;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Wither;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.entity.WitherSkull;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zoglin;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.ZombieHorse;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.entity.minecart.CommandMinecart;
import org.bukkit.entity.minecart.ExplosiveMinecart;
import org.bukkit.entity.minecart.HopperMinecart;
import org.bukkit.entity.minecart.PoweredMinecart;
import org.bukkit.entity.minecart.SpawnerMinecart;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Consumer;
import org.bukkit.util.Vector;

public abstract class CraftRegionAccessor implements RegionAccessor {

    public abstract StructureWorldAccess getHandle();

    public boolean isNormalWorld() {
        return getHandle() instanceof net.minecraft.server.world.ServerWorld;
    }

    @Override
    public Biome getBiome(Location location) {
        return getBiome(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    @Override
    public Biome getBiome(int x, int y, int z) {
        return CraftBlock.biomeBaseToBiome(getHandle().getRegistryManager().get(RegistryKeys.BIOME), getHandle().getBiomeForNoiseGen(x >> 2, y >> 2, z >> 2));
    }

    @Override
    public void setBiome(Location location, Biome biome) {
        setBiome(location.getBlockX(), location.getBlockY(), location.getBlockZ(), biome);
    }

    @Override
    public void setBiome(int x, int y, int z, Biome biome) {
        Preconditions.checkArgument(biome != Biome.CUSTOM, "Cannot set the biome to %s", biome);
        RegistryEntry<net.minecraft.world.biome.Biome> biomeBase = CraftBlock.biomeToBiomeBase(getHandle().getRegistryManager().get(RegistryKeys.BIOME), biome);
        setBiome(x, y, z, biomeBase);
    }

    public abstract void setBiome(int x, int y, int z, RegistryEntry<net.minecraft.world.biome.Biome> biomeBase);

    @Override
    public BlockState getBlockState(Location location) {
        return getBlockState(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    @Override
    public BlockState getBlockState(int x, int y, int z) {
        return CraftBlock.at(getHandle(), new BlockPos(x, y, z)).getState();
    }

    @Override
    public BlockData getBlockData(Location location) {
        return getBlockData(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    @Override
    public BlockData getBlockData(int x, int y, int z) {
        return CraftBlockData.fromData(getData(x, y, z));
    }

    @Override
    public Material getType(Location location) {
        return getType(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    @Override
    public Material getType(int x, int y, int z) {
        return CraftMagicNumbers.getMaterial(getData(x, y, z).getBlock());
    }

    private net.minecraft.block.BlockState getData(int x, int y, int z) {
        return getHandle().getBlockState(new BlockPos(x, y, z));
    }

    @Override
    public void setBlockData(Location location, BlockData blockData) {
        setBlockData(location.getBlockX(), location.getBlockY(), location.getBlockZ(), blockData);
    }

    @Override
    public void setBlockData(int x, int y, int z, BlockData blockData) {
        StructureWorldAccess world = getHandle();
        BlockPos pos = new BlockPos(x, y, z);
        net.minecraft.block.BlockState old = getHandle().getBlockState(pos);

        CraftBlock.setTypeAndData(world, pos, old, ((CraftBlockData) blockData).getState(), true);
    }

    @Override
    public void setType(Location location, Material material) {
        setType(location.getBlockX(), location.getBlockY(), location.getBlockZ(), material);
    }

    @Override
    public void setType(int x, int y, int z, Material material) {
        setBlockData(x, y, z, material.createBlockData());
    }

    @Override
    public int getHighestBlockYAt(int x, int z) {
        return getHighestBlockYAt(x, z, org.bukkit.HeightMap.MOTION_BLOCKING);
    }

    @Override
    public int getHighestBlockYAt(Location location) {
        return getHighestBlockYAt(location.getBlockX(), location.getBlockZ());
    }

    @Override
    public int getHighestBlockYAt(int x, int z, org.bukkit.HeightMap heightMap) {
        return getHandle().getTopY(CraftHeightMap.toNMS(heightMap), x, z);
    }

    @Override
    public int getHighestBlockYAt(Location location, org.bukkit.HeightMap heightMap) {
        return getHighestBlockYAt(location.getBlockX(), location.getBlockZ(), heightMap);
    }

    @Override
    public boolean generateTree(Location location, Random random, TreeType treeType) {
        BlockPos pos = CraftLocation.toBlockPosition(location);
        return generateTree(getHandle(), getHandle().getMinecraftWorld().getChunkManager().getChunkGenerator(), pos, new RandomSourceWrapper(random), treeType);
    }

    @Override
    public boolean generateTree(Location location, Random random, TreeType treeType, Consumer<BlockState> consumer) {
        return generateTree(location, random, treeType, (consumer == null) ? null : (block) -> {
            consumer.accept(block);
            return true;
        });
    }

    @Override
    public boolean generateTree(Location location, Random random, TreeType treeType, Predicate<BlockState> predicate) {
        BlockPos pos = CraftLocation.toBlockPosition(location);
        BlockStateListPopulator populator = new BlockStateListPopulator(getHandle());
        boolean result = generateTree(populator, getHandle().getMinecraftWorld().getChunkManager().getChunkGenerator(), pos, new RandomSourceWrapper(random), treeType);
        populator.refreshTiles();

        for (BlockState blockState : populator.getList()) {
            if (predicate == null || predicate.test(blockState)) {
                blockState.update(true, true);
            }
        }

        return result;
    }

    public boolean generateTree(StructureWorldAccess access, ChunkGenerator chunkGenerator, BlockPos pos, net.minecraft.util.math.random.Random random, TreeType treeType) {
        RegistryKey<ConfiguredFeature<?, ?>> gen;
        switch (treeType) {
            case BIG_TREE:
                gen = TreeConfiguredFeatures.FANCY_OAK;
                break;
            case BIRCH:
                gen = TreeConfiguredFeatures.BIRCH;
                break;
            case REDWOOD:
                gen = TreeConfiguredFeatures.SPRUCE;
                break;
            case TALL_REDWOOD:
                gen = TreeConfiguredFeatures.PINE;
                break;
            case JUNGLE:
                gen = TreeConfiguredFeatures.MEGA_JUNGLE_TREE;
                break;
            case SMALL_JUNGLE:
                gen = TreeConfiguredFeatures.JUNGLE_TREE_NO_VINE;
                break;
            case COCOA_TREE:
                gen = TreeConfiguredFeatures.JUNGLE_TREE;
                break;
            case JUNGLE_BUSH:
                gen = TreeConfiguredFeatures.JUNGLE_BUSH;
                break;
            case RED_MUSHROOM:
                gen = TreeConfiguredFeatures.HUGE_RED_MUSHROOM;
                break;
            case BROWN_MUSHROOM:
                gen = TreeConfiguredFeatures.HUGE_BROWN_MUSHROOM;
                break;
            case SWAMP:
                gen = TreeConfiguredFeatures.SWAMP_OAK;
                break;
            case ACACIA:
                gen = TreeConfiguredFeatures.ACACIA;
                break;
            case DARK_OAK:
                gen = TreeConfiguredFeatures.DARK_OAK;
                break;
            case MEGA_REDWOOD:
                gen = TreeConfiguredFeatures.MEGA_PINE;
                break;
            case TALL_BIRCH:
                gen = TreeConfiguredFeatures.SUPER_BIRCH_BEES_0002;
                break;
            case CHORUS_PLANT:
                ((ChorusFlowerBlock) Blocks.CHORUS_FLOWER).generate(access, pos, random, 8);
                return true;
            case CRIMSON_FUNGUS:
                gen = TreeConfiguredFeatures.CRIMSON_FUNGUS_PLANTED;
                break;
            case WARPED_FUNGUS:
                gen = TreeConfiguredFeatures.WARPED_FUNGUS_PLANTED;
                break;
            case AZALEA:
                gen = TreeConfiguredFeatures.AZALEA_TREE;
                break;
            case MANGROVE:
                gen = TreeConfiguredFeatures.MANGROVE;
                break;
            case TALL_MANGROVE:
                gen = TreeConfiguredFeatures.TALL_MANGROVE;
                break;
            case CHERRY:
                gen = TreeConfiguredFeatures.CHERRY;
                break;
            case TREE:
            default:
                gen = TreeConfiguredFeatures.OAK;
                break;
        }

        RegistryEntry<ConfiguredFeature<?, ?>> holder = access.getRegistryManager().get(RegistryKeys.CONFIGURED_FEATURE).getEntry(gen).orElse(null);
        return (holder != null) ? holder.comp_349().generate(access, chunkGenerator, random, pos) : false;
    }

    @Override
    public Entity spawnEntity(Location location, EntityType entityType) {
        return spawn(location, entityType.getEntityClass());
    }

    @Override
    public Entity spawnEntity(Location loc, EntityType type, boolean randomizeData) {
        return spawn(loc, type.getEntityClass(), null, CreatureSpawnEvent.SpawnReason.CUSTOM, randomizeData);
    }

    @Override
    public List<Entity> getEntities() {
        List<Entity> list = new ArrayList<Entity>();

        getNMSEntities().forEach(entity -> {
            Entity bukkitEntity = entity.getBukkitEntity();

            // Assuming that bukkitEntity isn't null
            if (bukkitEntity != null && (!isNormalWorld() || bukkitEntity.isValid())) {
                list.add(bukkitEntity);
            }
        });

        return list;
    }

    @Override
    public List<LivingEntity> getLivingEntities() {
        List<LivingEntity> list = new ArrayList<LivingEntity>();

        getNMSEntities().forEach(entity -> {
            Entity bukkitEntity = entity.getBukkitEntity();

            // Assuming that bukkitEntity isn't null
            if (bukkitEntity != null && bukkitEntity instanceof LivingEntity && (!isNormalWorld() || bukkitEntity.isValid())) {
                list.add((LivingEntity) bukkitEntity);
            }
        });

        return list;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Entity> Collection<T> getEntitiesByClass(Class<T> clazz) {
        Collection<T> list = new ArrayList<T>();

        getNMSEntities().forEach(entity -> {
            Entity bukkitEntity = entity.getBukkitEntity();

            if (bukkitEntity == null) {
                return;
            }

            Class<?> bukkitClass = bukkitEntity.getClass();

            if (clazz.isAssignableFrom(bukkitClass) && (!isNormalWorld() || bukkitEntity.isValid())) {
                list.add((T) bukkitEntity);
            }
        });

        return list;
    }

    @Override
    public Collection<Entity> getEntitiesByClasses(Class<?>... classes) {
        Collection<Entity> list = new ArrayList<Entity>();

        getNMSEntities().forEach(entity -> {
            Entity bukkitEntity = entity.getBukkitEntity();

            if (bukkitEntity == null) {
                return;
            }

            Class<?> bukkitClass = bukkitEntity.getClass();

            for (Class<?> clazz : classes) {
                if (clazz.isAssignableFrom(bukkitClass)) {
                    if (!isNormalWorld() || bukkitEntity.isValid()) {
                        list.add(bukkitEntity);
                    }
                    break;
                }
            }
        });

        return list;
    }

    public abstract Iterable<net.minecraft.entity.Entity> getNMSEntities();

    @Override
    public <T extends Entity> T spawn(Location location, Class<T> clazz) throws IllegalArgumentException {
        return spawn(location, clazz, null, CreatureSpawnEvent.SpawnReason.CUSTOM);
    }

    @Override
    public <T extends Entity> T spawn(Location location, Class<T> clazz, Consumer<T> function) throws IllegalArgumentException {
        return spawn(location, clazz, function, CreatureSpawnEvent.SpawnReason.CUSTOM);
    }

    @Override
    public <T extends Entity> T spawn(Location location, Class<T> clazz, boolean randomizeData, Consumer<T> function) throws IllegalArgumentException {
        return spawn(location, clazz, function, CreatureSpawnEvent.SpawnReason.CUSTOM, randomizeData);
    }

    public <T extends Entity> T spawn(Location location, Class<T> clazz, Consumer<T> function, CreatureSpawnEvent.SpawnReason reason) throws IllegalArgumentException {
        return spawn(location, clazz, function, reason, true);
    }

    public <T extends Entity> T spawn(Location location, Class<T> clazz, Consumer<T> function, CreatureSpawnEvent.SpawnReason reason, boolean randomizeData) throws IllegalArgumentException {
        net.minecraft.entity.Entity entity = createEntity(location, clazz, randomizeData);

        return addEntity(entity, reason, function, randomizeData);
    }

    @SuppressWarnings("unchecked")
    public <T extends Entity> T addEntity(net.minecraft.entity.Entity entity, CreatureSpawnEvent.SpawnReason reason) throws IllegalArgumentException {
        return addEntity(entity, reason, null, true);
    }

    @SuppressWarnings("unchecked")
    public <T extends Entity> T addEntity(net.minecraft.entity.Entity entity, CreatureSpawnEvent.SpawnReason reason, Consumer<T> function, boolean randomizeData) throws IllegalArgumentException {
        Preconditions.checkArgument(entity != null, "Cannot spawn null entity");

        if (randomizeData && entity instanceof MobEntity) {
            ((MobEntity) entity).initialize(getHandle(), getHandle().getLocalDifficulty(entity.di()), SpawnReason.COMMAND, (EntityData) null, null);
        }

        if (!isNormalWorld()) {
            entity.generation = true;
        }

        if (function != null) {
            function.accept((T) entity.getBukkitEntity());
        }

        addEntityToWorld(entity, reason);
        return (T) entity.getBukkitEntity();
    }

    public abstract void addEntityToWorld(net.minecraft.entity.Entity entity, CreatureSpawnEvent.SpawnReason reason);

    @SuppressWarnings("unchecked")
    public net.minecraft.entity.Entity createEntity(Location location, Class<? extends Entity> clazz) throws IllegalArgumentException {
        return createEntity(location, clazz, true);
    }

    @SuppressWarnings("unchecked")
    public net.minecraft.entity.Entity createEntity(Location location, Class<? extends Entity> clazz, boolean randomizeData) throws IllegalArgumentException {
        Preconditions.checkArgument(location != null, "Location cannot be null");
        Preconditions.checkArgument(clazz != null, "Entity class cannot be null");

        net.minecraft.entity.Entity entity = null;
        net.minecraft.world.World world = getHandle().getMinecraftWorld();

        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float pitch = location.getPitch();
        float yaw = location.getYaw();

        // order is important for some of these
        if (Boat.class.isAssignableFrom(clazz)) {
            if (ChestBoat.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.CHEST_BOAT.create(world);
            } else {
                entity = net.minecraft.entity.EntityType.BOAT.create(world);
            }
            entity.refreshPositionAndAngles(x, y, z, yaw, pitch);
        } else if (FallingBlock.class.isAssignableFrom(clazz)) {
            BlockPos pos = BlockPos.ofFloored(x, y, z);
            entity = FallingBlockEntity.spawnFromBlock(world, pos, getHandle().getBlockState(pos));
        } else if (Projectile.class.isAssignableFrom(clazz)) {
            if (Snowball.class.isAssignableFrom(clazz)) {
                entity = new SnowballEntity(world, x, y, z);
            } else if (Egg.class.isAssignableFrom(clazz)) {
                entity = new EggEntity(world, x, y, z);
            } else if (AbstractArrow.class.isAssignableFrom(clazz)) {
                if (TippedArrow.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.entity.EntityType.ARROW.create(world);
                    ((Arrow) entity.getBukkitEntity()).setBasePotionData(new PotionData(PotionType.WATER, false, false));
                } else if (SpectralArrow.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.entity.EntityType.SPECTRAL_ARROW.create(world);
                } else if (Trident.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.entity.EntityType.TRIDENT.create(world);
                } else {
                    entity = net.minecraft.entity.EntityType.ARROW.create(world);
                }
                entity.refreshPositionAndAngles(x, y, z, 0, 0);
            } else if (ThrownExpBottle.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.EXPERIENCE_BOTTLE.create(world);
                entity.refreshPositionAndAngles(x, y, z, 0, 0);
            } else if (EnderPearl.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.ENDER_PEARL.create(world);
                entity.refreshPositionAndAngles(x, y, z, 0, 0);
            } else if (ThrownPotion.class.isAssignableFrom(clazz)) {
                if (LingeringPotion.class.isAssignableFrom(clazz)) {
                    entity = new PotionEntity(world, x, y, z);
                    ((PotionEntity) entity).setItem(CraftItemStack.asNMSCopy(new ItemStack(org.bukkit.Material.LINGERING_POTION, 1)));
                } else {
                    entity = new PotionEntity(world, x, y, z);
                    ((PotionEntity) entity).setItem(CraftItemStack.asNMSCopy(new ItemStack(org.bukkit.Material.SPLASH_POTION, 1)));
                }
            } else if (Fireball.class.isAssignableFrom(clazz)) {
                if (SmallFireball.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.entity.EntityType.SMALL_FIREBALL.create(world);
                } else if (WitherSkull.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.entity.EntityType.WITHER_SKULL.create(world);
                } else if (DragonFireball.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.entity.EntityType.DRAGON_FIREBALL.create(world);
                } else {
                    entity = net.minecraft.entity.EntityType.FIREBALL.create(world);
                }
                entity.refreshPositionAndAngles(x, y, z, yaw, pitch);
                Vector direction = location.getDirection().multiply(10);
                ((ExplosiveProjectileEntity) entity).setDirection(direction.getX(), direction.getY(), direction.getZ());
            } else if (ShulkerBullet.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.SHULKER_BULLET.create(world);
                entity.refreshPositionAndAngles(x, y, z, yaw, pitch);
            } else if (LlamaSpit.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.LLAMA_SPIT.create(world);
                entity.refreshPositionAndAngles(x, y, z, yaw, pitch);
            } else if (Firework.class.isAssignableFrom(clazz)) {
                entity = new FireworkRocketEntity(world, x, y, z, net.minecraft.item.ItemStack.EMPTY);
            }
        } else if (Minecart.class.isAssignableFrom(clazz)) {
            if (PoweredMinecart.class.isAssignableFrom(clazz)) {
                entity = new FurnaceMinecartEntity(world, x, y, z);
            } else if (StorageMinecart.class.isAssignableFrom(clazz)) {
                entity = new ChestMinecartEntity(world, x, y, z);
            } else if (ExplosiveMinecart.class.isAssignableFrom(clazz)) {
                entity = new TntMinecartEntity(world, x, y, z);
            } else if (HopperMinecart.class.isAssignableFrom(clazz)) {
                entity = new HopperMinecartEntity(world, x, y, z);
            } else if (SpawnerMinecart.class.isAssignableFrom(clazz)) {
                entity = new SpawnerMinecartEntity(world, x, y, z);
            } else if (CommandMinecart.class.isAssignableFrom(clazz)) {
                entity = new CommandBlockMinecartEntity(world, x, y, z);
            } else { // Default to rideable minecart for pre-rideable compatibility
                entity = new MinecartEntity(world, x, y, z);
            }
        } else if (EnderSignal.class.isAssignableFrom(clazz)) {
            entity = new EyeOfEnderEntity(world, x, y, z);
        } else if (EnderCrystal.class.isAssignableFrom(clazz)) {
            entity = net.minecraft.entity.EntityType.END_CRYSTAL.create(world);
            entity.refreshPositionAndAngles(x, y, z, 0, 0);
        } else if (LivingEntity.class.isAssignableFrom(clazz)) {
            if (Chicken.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.CHICKEN.create(world);
            } else if (Cow.class.isAssignableFrom(clazz)) {
                if (MushroomCow.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.entity.EntityType.MOOSHROOM.create(world);
                } else {
                    entity = net.minecraft.entity.EntityType.COW.create(world);
                }
            } else if (Golem.class.isAssignableFrom(clazz)) {
                if (Snowman.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.entity.EntityType.SNOW_GOLEM.create(world);
                } else if (IronGolem.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.entity.EntityType.IRON_GOLEM.create(world);
                } else if (Shulker.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.entity.EntityType.SHULKER.create(world);
                }
            } else if (Creeper.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.CREEPER.create(world);
            } else if (Ghast.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.GHAST.create(world);
            } else if (Pig.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.PIG.create(world);
            } else if (Player.class.isAssignableFrom(clazz)) {
                // need a net server handler for this one
            } else if (Sheep.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.SHEEP.create(world);
            } else if (AbstractHorse.class.isAssignableFrom(clazz)) {
                if (ChestedHorse.class.isAssignableFrom(clazz)) {
                    if (Donkey.class.isAssignableFrom(clazz)) {
                        entity = net.minecraft.entity.EntityType.DONKEY.create(world);
                    } else if (Mule.class.isAssignableFrom(clazz)) {
                        entity = net.minecraft.entity.EntityType.MULE.create(world);
                    } else if (Llama.class.isAssignableFrom(clazz)) {
                        if (TraderLlama.class.isAssignableFrom(clazz)) {
                            entity = net.minecraft.entity.EntityType.TRADER_LLAMA.create(world);
                        } else {
                            entity = net.minecraft.entity.EntityType.LLAMA.create(world);
                        }
                    }
                } else if (SkeletonHorse.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.entity.EntityType.SKELETON_HORSE.create(world);
                } else if (ZombieHorse.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.entity.EntityType.ZOMBIE_HORSE.create(world);
                } else if (Camel.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.entity.EntityType.CAMEL.create(world);
                } else {
                    entity = net.minecraft.entity.EntityType.HORSE.create(world);
                }
            } else if (AbstractSkeleton.class.isAssignableFrom(clazz)) {
                if (Stray.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.entity.EntityType.STRAY.create(world);
                } else if (WitherSkeleton.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.entity.EntityType.WITHER_SKELETON.create(world);
                } else if (Skeleton.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.entity.EntityType.SKELETON.create(world);
                }
            } else if (Slime.class.isAssignableFrom(clazz)) {
                if (MagmaCube.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.entity.EntityType.MAGMA_CUBE.create(world);
                } else {
                    entity = net.minecraft.entity.EntityType.SLIME.create(world);
                }
            } else if (Spider.class.isAssignableFrom(clazz)) {
                if (CaveSpider.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.entity.EntityType.CAVE_SPIDER.create(world);
                } else {
                    entity = net.minecraft.entity.EntityType.SPIDER.create(world);
                }
            } else if (Squid.class.isAssignableFrom(clazz)) {
                if (GlowSquid.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.entity.EntityType.GLOW_SQUID.create(world);
                } else {
                    entity = net.minecraft.entity.EntityType.SQUID.create(world);
                }
            } else if (Tameable.class.isAssignableFrom(clazz)) {
                if (Wolf.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.entity.EntityType.WOLF.create(world);
                } else if (Parrot.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.entity.EntityType.PARROT.create(world);
                } else if (Cat.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.entity.EntityType.CAT.create(world);
                }
            } else if (PigZombie.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.ZOMBIFIED_PIGLIN.create(world);
            } else if (Zombie.class.isAssignableFrom(clazz)) {
                if (Husk.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.entity.EntityType.HUSK.create(world);
                } else if (ZombieVillager.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.entity.EntityType.ZOMBIE_VILLAGER.create(world);
                } else if (Drowned.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.entity.EntityType.DROWNED.create(world);
                } else {
                    entity = new ZombieEntity(world);
                }
            } else if (Giant.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.GIANT.create(world);
            } else if (Silverfish.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.SILVERFISH.create(world);
            } else if (Enderman.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.ENDERMAN.create(world);
            } else if (Blaze.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.BLAZE.create(world);
            } else if (AbstractVillager.class.isAssignableFrom(clazz)) {
                if (Villager.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.entity.EntityType.VILLAGER.create(world);
                } else if (WanderingTrader.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.entity.EntityType.WANDERING_TRADER.create(world);
                }
            } else if (Witch.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.WITCH.create(world);
            } else if (Wither.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.WITHER.create(world);
            } else if (ComplexLivingEntity.class.isAssignableFrom(clazz)) {
                if (EnderDragon.class.isAssignableFrom(clazz)) {
                    Preconditions.checkArgument(this.isNormalWorld(), "Cannot spawn entity %s during world generation", clazz.getName());
                    entity = net.minecraft.entity.EntityType.ENDER_DRAGON.create(getHandle().getMinecraftWorld());
                }
            } else if (Ambient.class.isAssignableFrom(clazz)) {
                if (Bat.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.entity.EntityType.BAT.create(world);
                }
            } else if (Rabbit.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.RABBIT.create(world);
            } else if (Endermite.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.ENDERMITE.create(world);
            } else if (Guardian.class.isAssignableFrom(clazz)) {
                if (ElderGuardian.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.entity.EntityType.ELDER_GUARDIAN.create(world);
                } else {
                    entity = net.minecraft.entity.EntityType.GUARDIAN.create(world);
                }
            } else if (ArmorStand.class.isAssignableFrom(clazz)) {
                entity = new ArmorStandEntity(world, x, y, z);
            } else if (PolarBear.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.POLAR_BEAR.create(world);
            } else if (Vex.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.VEX.create(world);
            } else if (Illager.class.isAssignableFrom(clazz)) {
                if (Spellcaster.class.isAssignableFrom(clazz)) {
                    if (Evoker.class.isAssignableFrom(clazz)) {
                        entity = net.minecraft.entity.EntityType.EVOKER.create(world);
                    } else if (Illusioner.class.isAssignableFrom(clazz)) {
                        entity = net.minecraft.entity.EntityType.ILLUSIONER.create(world);
                    }
                } else if (Vindicator.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.entity.EntityType.VINDICATOR.create(world);
                } else if (Pillager.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.entity.EntityType.PILLAGER.create(world);
                }
            } else if (Turtle.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.TURTLE.create(world);
            } else if (Phantom.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.PHANTOM.create(world);
            } else if (Fish.class.isAssignableFrom(clazz)) {
                if (Cod.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.entity.EntityType.COD.create(world);
                } else if (PufferFish.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.entity.EntityType.PUFFERFISH.create(world);
                } else if (Salmon.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.entity.EntityType.SALMON.create(world);
                } else if (TropicalFish.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.entity.EntityType.TROPICAL_FISH.create(world);
                } else if (Tadpole.class.isAssignableFrom(clazz)) {
                    entity = net.minecraft.entity.EntityType.TADPOLE.create(world);
                }
            } else if (Dolphin.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.DOLPHIN.create(world);
            } else if (Ocelot.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.OCELOT.create(world);
            } else if (Ravager.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.RAVAGER.create(world);
            } else if (Panda.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.PANDA.create(world);
            } else if (Fox.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.FOX.create(world);
            } else if (Bee.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.BEE.create(world);
            } else if (Hoglin.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.HOGLIN.create(world);
            } else if (Piglin.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.PIGLIN.create(world);
            } else if (PiglinBrute.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.PIGLIN_BRUTE.create(world);
            } else if (Strider.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.STRIDER.create(world);
            } else if (Zoglin.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.ZOGLIN.create(world);
            } else if (Axolotl.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.AXOLOTL.create(world);
            } else if (Goat.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.GOAT.create(world);
            } else if (Allay.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.ALLAY.create(world);
            } else if (Frog.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.FROG.create(world);
            } else if (Warden.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.WARDEN.create(world);
            } else if (Sniffer.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.SNIFFER.create(world);
            }

            if (entity != null) {
                entity.updatePositionAndAngles(x, y, z, yaw, pitch);
                entity.setHeadYaw(yaw); // SPIGOT-3587
            }
        } else if (Hanging.class.isAssignableFrom(clazz)) {
            if (LeashHitch.class.isAssignableFrom(clazz)) {
                // SPIGOT-5732: LeashHitch has no direction and is always centered at a block
                entity = new LeashKnotEntity(world, BlockPos.ofFloored(x, y, z));
            } else {
                BlockFace face = BlockFace.SELF;
                BlockFace[] faces = new BlockFace[]{BlockFace.EAST, BlockFace.NORTH, BlockFace.WEST, BlockFace.SOUTH};

                int width = 16; // 1 full block, also painting smallest size.
                int height = 16; // 1 full block, also painting smallest size.

                if (ItemFrame.class.isAssignableFrom(clazz)) {
                    width = 12;
                    height = 12;
                    faces = new BlockFace[]{BlockFace.EAST, BlockFace.NORTH, BlockFace.WEST, BlockFace.SOUTH, BlockFace.UP, BlockFace.DOWN};
                }

                final BlockPos pos = BlockPos.ofFloored(x, y, z);
                for (BlockFace dir : faces) {
                    net.minecraft.block.BlockState nmsBlock = getHandle().getBlockState(pos.offset(CraftBlock.blockFaceToNotch(dir)));
                    if (nmsBlock.isSolid() || AbstractRedstoneGateBlock.isRedstoneGate(nmsBlock)) {
                        boolean taken = false;
                        Box bb = (ItemFrame.class.isAssignableFrom(clazz))
                                ? ItemFrameEntity.calculateBoundingBox(null, pos, CraftBlock.blockFaceToNotch(dir).getOpposite(), width, height)
                                : AbstractDecorationEntity.calculateBoundingBox(null, pos, CraftBlock.blockFaceToNotch(dir).getOpposite(), width, height);
                        List<net.minecraft.entity.Entity> list = (List<net.minecraft.entity.Entity>) getHandle().getOtherEntities(null, bb);
                        for (Iterator<net.minecraft.entity.Entity> it = list.iterator(); !taken && it.hasNext(); ) {
                            net.minecraft.entity.Entity e = it.next();
                            if (e instanceof AbstractDecorationEntity) {
                                taken = true; // Hanging entities do not like hanging entities which intersect them.
                            }
                        }

                        if (!taken) {
                            face = dir;
                            break;
                        }
                    }
                }

                // No valid face found
                if (face == BlockFace.SELF) {
                    // SPIGOT-6387: Allow hanging entities to be placed in midair
                    face = BlockFace.SOUTH;
                    randomizeData = false; // Don't randomize if no valid face is found, prevents null painting
                }

                Direction dir = CraftBlock.blockFaceToNotch(face).getOpposite();
                if (Painting.class.isAssignableFrom(clazz)) {
                    if (isNormalWorld() && randomizeData) {
                        entity = PaintingEntity.placePainting(world, pos, dir).orElse(null);
                    } else {
                        entity = new PaintingEntity(net.minecraft.entity.EntityType.PAINTING, getHandle().getMinecraftWorld());
                        entity.updatePositionAndAngles(x, y, z, yaw, pitch);
                        ((PaintingEntity) entity).setFacing(dir);
                    }
                } else if (ItemFrame.class.isAssignableFrom(clazz)) {
                    if (GlowItemFrame.class.isAssignableFrom(clazz)) {
                        entity = new net.minecraft.entity.decoration.GlowItemFrameEntity(world, BlockPos.ofFloored(x, y, z), dir);
                    } else {
                        entity = new ItemFrameEntity(world, BlockPos.ofFloored(x, y, z), dir);
                    }
                }
            }
        } else if (TNTPrimed.class.isAssignableFrom(clazz)) {
            entity = new TntEntity(world, x, y, z, null);
        } else if (ExperienceOrb.class.isAssignableFrom(clazz)) {
            entity = new ExperienceOrbEntity(world, x, y, z, 0);
        } else if (LightningStrike.class.isAssignableFrom(clazz)) {
            entity = net.minecraft.entity.EntityType.LIGHTNING_BOLT.create(world);
            entity.refreshPositionAfterTeleport(location.getX(), location.getY(), location.getZ());
        } else if (AreaEffectCloud.class.isAssignableFrom(clazz)) {
            entity = new AreaEffectCloudEntity(world, x, y, z);
        } else if (EvokerFangs.class.isAssignableFrom(clazz)) {
            entity = new EvokerFangsEntity(world, x, y, z, (float) Math.toRadians(yaw), 0, null);
        } else if (Marker.class.isAssignableFrom(clazz)) {
            entity = net.minecraft.entity.EntityType.MARKER.create(world);
            entity.setPosition(x, y, z);
        } else if (Interaction.class.isAssignableFrom(clazz)) {
            entity = net.minecraft.entity.EntityType.INTERACTION.create(world);
            entity.setPosition(x, y, z);
        } else if (Display.class.isAssignableFrom(clazz)) {
            if (BlockDisplay.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.BLOCK_DISPLAY.create(world);
            } else if (ItemDisplay.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.ITEM_DISPLAY.create(world);
            } else if (TextDisplay.class.isAssignableFrom(clazz)) {
                entity = net.minecraft.entity.EntityType.TEXT_DISPLAY.create(world);
            }

            if (entity != null) {
                entity.setPosition(x, y, z);
            }
        }

        if (entity != null) {
            return entity;
        }

        throw new IllegalArgumentException("Cannot spawn an entity for " + clazz.getName());
    }
}
