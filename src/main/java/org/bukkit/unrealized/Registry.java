package org.bukkit.unrealized;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMap;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Predicate;

import org.bukkit.Bukkit;
import org.bukkit.unrealized.advancement.Advancement;
import org.bukkit.unrealized.attribute.Attribute;
import org.bukkit.unrealized.block.Biome;
import org.bukkit.unrealized.boss.KeyedBossBar;
import org.bukkit.unrealized.enchantments.Enchantment;
import org.bukkit.unrealized.entity.EntityType;
import org.bukkit.unrealized.entity.Frog;
import org.bukkit.unrealized.entity.Villager;
import org.bukkit.unrealized.entity.memory.MemoryKey;
import org.bukkit.unrealized.generator.structure.Structure;
import org.bukkit.unrealized.generator.structure.StructureType;
import org.bukkit.unrealized.inventory.meta.trim.TrimMaterial;
import org.bukkit.unrealized.inventory.meta.trim.TrimPattern;
import org.bukkit.unrealized.loot.LootTables;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a registry of Bukkit objects that may be retrieved by
 * {@link NamespacedKey}.
 *
 * @param <T> type of item in the registry
 */
public interface Registry<T extends Keyed> extends Iterable<T> {

    /**
     * Server advancements.
     *
     * @see Bukkit#getAdvancement(NamespacedKey)
     * @see Bukkit#advancementIterator()
     */
    Registry<Advancement> ADVANCEMENT = new Registry<Advancement>() {

        @Nullable
        @Override
        public Advancement get(@NotNull NamespacedKey key) {
            return Bukkit.getAdvancement(key);
        }

        @NotNull
        @Override
        public Iterator<Advancement> iterator() {
            return Bukkit.advancementIterator();
        }
    };
    /**
     * Server art.
     *
     * @see Art
     */
    Registry<Art> ART = new SimpleRegistry<>(Art.class);
    /**
     * Attribute.
     *
     * @see Attribute
     */
    Registry<Attribute> ATTRIBUTE = new SimpleRegistry<>(Attribute.class);
    /**
     * Server biomes.
     *
     * @see Biome
     */
    Registry<Biome> BIOME = new SimpleRegistry<>(Biome.class);
    /**
     * Custom boss bars.
     *
     * @see Bukkit#getBossBar(NamespacedKey)
     * @see Bukkit#getBossBars()
     */
    Registry<KeyedBossBar> BOSS_BARS = new Registry<KeyedBossBar>() {

        @Nullable
        @Override
        public KeyedBossBar get(@NotNull NamespacedKey key) {
            return Bukkit.getBossBar(key);
        }

        @NotNull
        @Override
        public Iterator<KeyedBossBar> iterator() {
            return Bukkit.getBossBars();
        }
    };
    /**
     * Server enchantments.
     *
     * @see Enchantment#getByKey(NamespacedKey)
     */
    Registry<Enchantment> ENCHANTMENT = new Registry<Enchantment>() {

        @Nullable
        @Override
        public Enchantment get(@NotNull NamespacedKey key) {
            return Enchantment.getByKey(key);
        }

        @NotNull
        @Override
        public Iterator<Enchantment> iterator() {
            return Arrays.asList(Enchantment.values()).iterator();
        }
    };
    /**
     * Server entity types.
     *
     * @see EntityType
     */
    Registry<EntityType> ENTITY_TYPE = new SimpleRegistry<>(EntityType.class, (entity) -> entity != EntityType.UNKNOWN);
    /**
     * Default server loot tables.
     *
     * @see LootTables
     */
    Registry<LootTables> LOOT_TABLES = new SimpleRegistry<>(LootTables.class);
    /**
     * Server materials.
     *
     * @see Material
     */
    Registry<Material> MATERIAL = new SimpleRegistry<>(Material.class, (mat) -> !mat.isLegacy());
    /**
     * Server statistics.
     *
     * @see Statistic
     */
    Registry<Statistic> STATISTIC = new SimpleRegistry<>(Statistic.class);
    /**
     * Server structures.
     *
     * @see Structure
     */
    Registry<Structure> STRUCTURE = Bukkit.getRegistry(Structure.class);
    /**
     * Server structure types.
     *
     * @see StructureType
     */
    Registry<StructureType> STRUCTURE_TYPE = Bukkit.getRegistry(StructureType.class);
    /**
     * Sound keys.
     *
     * @see Sound
     */
    Registry<Sound> SOUNDS = new SimpleRegistry<>(Sound.class);
    /**
     * Trim materials.
     *
     * @see TrimMaterial
     */
    @ApiStatus.Experimental
    Registry<TrimMaterial> TRIM_MATERIAL = Bukkit.getRegistry(TrimMaterial.class);
    /**
     * Trim patterns.
     *
     * @see TrimPattern
     */
    @ApiStatus.Experimental
    Registry<TrimPattern> TRIM_PATTERN = Bukkit.getRegistry(TrimPattern.class);
    /**
     * Villager profession.
     *
     * @see Villager.Profession
     */
    Registry<Villager.Profession> VILLAGER_PROFESSION = new SimpleRegistry<>(Villager.Profession.class);
    /**
     * Villager type.
     *
     * @see Villager.Type
     */
    Registry<Villager.Type> VILLAGER_TYPE = new SimpleRegistry<>(Villager.Type.class);
    /**
     * Memory Keys.
     *
     * @see MemoryKey
     */
    Registry<MemoryKey> MEMORY_MODULE_TYPE = new Registry<MemoryKey>() {

        @NotNull
        @Override
        public Iterator iterator() {
            return MemoryKey.values().iterator();
        }

        @Nullable
        @Override
        public MemoryKey get(@NotNull NamespacedKey key) {
            return MemoryKey.getByKey(key);
        }
    };
    /**
     * Server fluids.
     *
     * @see Fluid
     */
    Registry<Fluid> FLUID = new SimpleRegistry<>(Fluid.class);
    /**
     * Frog variants.
     *
     * @see Frog.Variant
     */
    Registry<Frog.Variant> FROG_VARIANT = new SimpleRegistry<>(Frog.Variant.class);
    /**
     * Game events.
     *
     * @see GameEvent
     */
    Registry<GameEvent> GAME_EVENT = new Registry<GameEvent>() {

        @NotNull
        @Override
        public Iterator iterator() {
            return GameEvent.values().iterator();
        }

        @Nullable
        @Override
        public GameEvent get(@NotNull NamespacedKey key) {
            return GameEvent.getByKey(key);
        }
    };

    /**
     * Get the object by its key.
     *
     * @param key non-null key
     * @return item or null if does not exist
     */
    @Nullable
    T get(@NotNull NamespacedKey key);

    /**
     * Attempts to match the registered object with the given key.
     * <p>
     * This will attempt to find a reasonable match based on the provided input
     * and may do so through unspecified means.
     *
     * @param input non-null input
     * @return registered object or null if does not exist
     */
    @Nullable
    default T match(@NotNull String input) {
        Preconditions.checkArgument(input != null, "input must not be null");

        String filtered = input.toLowerCase().replaceAll("\\s+", "_");
        NamespacedKey namespacedKey = NamespacedKey.fromString(filtered);
        return (namespacedKey != null) ? get(namespacedKey) : null;
    }

    static final class SimpleRegistry<T extends Enum<T> & Keyed> implements Registry<T> {

        private final Map<NamespacedKey, T> map;

        protected SimpleRegistry(@NotNull Class<T> type) {
            this(type, Predicates.<T>alwaysTrue());
        }

        protected SimpleRegistry(@NotNull Class<T> type, @NotNull Predicate<T> predicate) {
            ImmutableMap.Builder<NamespacedKey, T> builder = ImmutableMap.builder();

            for (T entry : type.getEnumConstants()) {
                if (predicate.test(entry)) {
                    builder.put(entry.getKey(), entry);
                }
            }

            map = builder.build();
        }

        @Nullable
        @Override
        public T get(@NotNull NamespacedKey key) {
            return map.get(key);
        }

        @NotNull
        @Override
        public Iterator<T> iterator() {
            return map.values().iterator();
        }
    }
}