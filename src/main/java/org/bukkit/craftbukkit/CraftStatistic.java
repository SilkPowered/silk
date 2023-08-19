package org.bukkit.craftbukkit;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.stat.ServerStatHandler;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.Statistic.Type;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;
import org.bukkit.entity.EntityType;

public enum CraftStatistic {
    DAMAGE_DEALT(Stats.DAMAGE_DEALT),
    DAMAGE_TAKEN(Stats.DAMAGE_TAKEN),
    DEATHS(Stats.DEATHS),
    MOB_KILLS(Stats.MOB_KILLS),
    PLAYER_KILLS(Stats.PLAYER_KILLS),
    FISH_CAUGHT(Stats.FISH_CAUGHT),
    ANIMALS_BRED(Stats.ANIMALS_BRED),
    LEAVE_GAME(Stats.LEAVE_GAME),
    JUMP(Stats.JUMP),
    DROP_COUNT(Stats.DROP),
    DROP(new Identifier("dropped")),
    PICKUP(new Identifier("picked_up")),
    PLAY_ONE_MINUTE(Stats.PLAY_TIME),
    TOTAL_WORLD_TIME(Stats.TOTAL_WORLD_TIME),
    WALK_ONE_CM(Stats.WALK_ONE_CM),
    WALK_ON_WATER_ONE_CM(Stats.WALK_ON_WATER_ONE_CM),
    FALL_ONE_CM(Stats.FALL_ONE_CM),
    SNEAK_TIME(Stats.CROUCH_TIME),
    CLIMB_ONE_CM(Stats.CLIMB_ONE_CM),
    FLY_ONE_CM(Stats.FLY_ONE_CM),
    WALK_UNDER_WATER_ONE_CM(Stats.WALK_UNDER_WATER_ONE_CM),
    MINECART_ONE_CM(Stats.MINECART_ONE_CM),
    BOAT_ONE_CM(Stats.BOAT_ONE_CM),
    PIG_ONE_CM(Stats.PIG_ONE_CM),
    HORSE_ONE_CM(Stats.HORSE_ONE_CM),
    SPRINT_ONE_CM(Stats.SPRINT_ONE_CM),
    CROUCH_ONE_CM(Stats.CROUCH_ONE_CM),
    AVIATE_ONE_CM(Stats.AVIATE_ONE_CM),
    MINE_BLOCK(new Identifier("mined")),
    USE_ITEM(new Identifier("used")),
    BREAK_ITEM(new Identifier("broken")),
    CRAFT_ITEM(new Identifier("crafted")),
    KILL_ENTITY(new Identifier("killed")),
    ENTITY_KILLED_BY(new Identifier("killed_by")),
    TIME_SINCE_DEATH(Stats.TIME_SINCE_DEATH),
    TALKED_TO_VILLAGER(Stats.TALKED_TO_VILLAGER),
    TRADED_WITH_VILLAGER(Stats.TRADED_WITH_VILLAGER),
    CAKE_SLICES_EATEN(Stats.EAT_CAKE_SLICE),
    CAULDRON_FILLED(Stats.FILL_CAULDRON),
    CAULDRON_USED(Stats.USE_CAULDRON),
    ARMOR_CLEANED(Stats.CLEAN_ARMOR),
    BANNER_CLEANED(Stats.CLEAN_BANNER),
    BREWINGSTAND_INTERACTION(Stats.INTERACT_WITH_BREWINGSTAND),
    BEACON_INTERACTION(Stats.INTERACT_WITH_BEACON),
    DROPPER_INSPECTED(Stats.INSPECT_DROPPER),
    HOPPER_INSPECTED(Stats.INSPECT_HOPPER),
    DISPENSER_INSPECTED(Stats.INSPECT_DISPENSER),
    NOTEBLOCK_PLAYED(Stats.PLAY_NOTEBLOCK),
    NOTEBLOCK_TUNED(Stats.TUNE_NOTEBLOCK),
    FLOWER_POTTED(Stats.POT_FLOWER),
    TRAPPED_CHEST_TRIGGERED(Stats.TRIGGER_TRAPPED_CHEST),
    ENDERCHEST_OPENED(Stats.OPEN_ENDERCHEST),
    ITEM_ENCHANTED(Stats.ENCHANT_ITEM),
    RECORD_PLAYED(Stats.PLAY_RECORD),
    FURNACE_INTERACTION(Stats.INTERACT_WITH_FURNACE),
    CRAFTING_TABLE_INTERACTION(Stats.INTERACT_WITH_CRAFTING_TABLE),
    CHEST_OPENED(Stats.OPEN_CHEST),
    SLEEP_IN_BED(Stats.SLEEP_IN_BED),
    SHULKER_BOX_OPENED(Stats.OPEN_SHULKER_BOX),
    TIME_SINCE_REST(Stats.TIME_SINCE_REST),
    SWIM_ONE_CM(Stats.SWIM_ONE_CM),
    DAMAGE_DEALT_ABSORBED(Stats.DAMAGE_DEALT_ABSORBED),
    DAMAGE_DEALT_RESISTED(Stats.DAMAGE_DEALT_RESISTED),
    DAMAGE_BLOCKED_BY_SHIELD(Stats.DAMAGE_BLOCKED_BY_SHIELD),
    DAMAGE_ABSORBED(Stats.DAMAGE_ABSORBED),
    DAMAGE_RESISTED(Stats.DAMAGE_RESISTED),
    CLEAN_SHULKER_BOX(Stats.CLEAN_SHULKER_BOX),
    OPEN_BARREL(Stats.OPEN_BARREL),
    INTERACT_WITH_BLAST_FURNACE(Stats.INTERACT_WITH_BLAST_FURNACE),
    INTERACT_WITH_SMOKER(Stats.INTERACT_WITH_SMOKER),
    INTERACT_WITH_LECTERN(Stats.INTERACT_WITH_LECTERN),
    INTERACT_WITH_CAMPFIRE(Stats.INTERACT_WITH_CAMPFIRE),
    INTERACT_WITH_CARTOGRAPHY_TABLE(Stats.INTERACT_WITH_CARTOGRAPHY_TABLE),
    INTERACT_WITH_LOOM(Stats.INTERACT_WITH_LOOM),
    INTERACT_WITH_STONECUTTER(Stats.INTERACT_WITH_STONECUTTER),
    BELL_RING(Stats.BELL_RING),
    RAID_TRIGGER(Stats.RAID_TRIGGER),
    RAID_WIN(Stats.RAID_WIN),
    INTERACT_WITH_ANVIL(Stats.INTERACT_WITH_ANVIL),
    INTERACT_WITH_GRINDSTONE(Stats.INTERACT_WITH_GRINDSTONE),
    TARGET_HIT(Stats.TARGET_HIT),
    INTERACT_WITH_SMITHING_TABLE(Stats.INTERACT_WITH_SMITHING_TABLE),
    STRIDER_ONE_CM(Stats.STRIDER_ONE_CM);
    private final Identifier minecraftKey;
    private final org.bukkit.Statistic bukkit;
    private static final BiMap<Identifier, org.bukkit.Statistic> statistics;

    static {
        ImmutableBiMap.Builder<Identifier, org.bukkit.Statistic> statisticBuilder = ImmutableBiMap.builder();
        for (CraftStatistic statistic : CraftStatistic.values()) {
            statisticBuilder.put(statistic.minecraftKey, statistic.bukkit);
        }

        statistics = statisticBuilder.build();
    }

    private CraftStatistic(Identifier minecraftKey) {
        this.minecraftKey = minecraftKey;

        this.bukkit = org.bukkit.Statistic.valueOf(this.name());
        Preconditions.checkState(bukkit != null, "Bukkit statistic %s does not exist", this.name());
    }

    public static org.bukkit.Statistic getBukkitStatistic(net.minecraft.stat.Stat<?> statistic) {
        Preconditions.checkArgument(statistic != null, "NMS Statistic cannot be null");
        Registry statRegistry = statistic.getType().getRegistry();
        Identifier nmsKey = Registries.STAT_TYPE.getId(statistic.getType());

        if (statRegistry == Registries.CUSTOM_STAT) {
            nmsKey = (Identifier) statistic.getValue();
        }

        return statistics.get(nmsKey);
    }

    public static net.minecraft.stat.Stat getNMSStatistic(org.bukkit.Statistic bukkit) {
        Preconditions.checkArgument(bukkit.getType() == Statistic.Type.UNTYPED, "This method only accepts untyped statistics");

        net.minecraft.stat.Stat<Identifier> nms = Stats.CUSTOM.getOrCreateStat(statistics.inverse().get(bukkit));
        Preconditions.checkArgument(nms != null, "NMS Statistic %s does not exist", bukkit);

        return nms;
    }

    public static net.minecraft.stat.Stat getMaterialStatistic(org.bukkit.Statistic stat, Material material) {
        try {
            if (stat == Statistic.MINE_BLOCK) {
                return Stats.BLOCK_MINED.getOrCreateStat(CraftMagicNumbers.getBlock(material));
            }
            if (stat == Statistic.CRAFT_ITEM) {
                return Stats.ITEM_CRAFTED.getOrCreateStat(CraftMagicNumbers.getItem(material));
            }
            if (stat == Statistic.USE_ITEM) {
                return Stats.ITEM_USED.getOrCreateStat(CraftMagicNumbers.getItem(material));
            }
            if (stat == Statistic.BREAK_ITEM) {
                return Stats.ITEM_BROKEN.getOrCreateStat(CraftMagicNumbers.getItem(material));
            }
            if (stat == Statistic.PICKUP) {
                return Stats.ITEM_PICKED_UP.getOrCreateStat(CraftMagicNumbers.getItem(material));
            }
            if (stat == Statistic.DROP) {
                return Stats.ITEM_DROPPED.getOrCreateStat(CraftMagicNumbers.getItem(material));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
        return null;
    }

    public static net.minecraft.stat.Stat getEntityStatistic(org.bukkit.Statistic stat, EntityType entity) {
        Preconditions.checkArgument(entity != null, "EntityType cannot be null");
        if (entity.getName() != null) {
            net.minecraft.entity.EntityType<?> nmsEntity = Registries.ENTITY_TYPE.a(new Identifier(entity.getName()));

            if (stat == org.bukkit.Statistic.KILL_ENTITY) {
                return net.minecraft.stat.Stats.ENTITY_KILLED.getOrCreateStat(nmsEntity);
            }
            if (stat == org.bukkit.Statistic.ENTITY_KILLED_BY) {
                return net.minecraft.stat.Stats.ENTITY_KILLED_BY.getOrCreateStat(nmsEntity);
            }
        }
        return null;
    }

    public static EntityType getEntityTypeFromStatistic(net.minecraft.stat.Stat<net.minecraft.entity.EntityType<?>> statistic) {
        Preconditions.checkArgument(statistic != null, "NMS Statistic cannot be null");
        Identifier name = net.minecraft.entity.EntityType.getId(statistic.getValue());
        return EntityType.fromName(name.getPath());
    }

    public static Material getMaterialFromStatistic(net.minecraft.stat.Stat<?> statistic) {
        if (statistic.getValue() instanceof Item statisticItemValue) {
            return CraftMagicNumbers.getMaterial(statisticItemValue);
        }
        if (statistic.getValue() instanceof Block statisticBlockValue) {
            return CraftMagicNumbers.getMaterial(statisticBlockValue);
        }
        return null;
    }

    public static void incrementStatistic(ServerStatHandler manager, Statistic statistic) {
        incrementStatistic(manager, statistic, 1);
    }

    public static void decrementStatistic(ServerStatHandler manager, Statistic statistic) {
        decrementStatistic(manager, statistic, 1);
    }

    public static int getStatistic(ServerStatHandler manager, Statistic statistic) {
        Preconditions.checkArgument(statistic != null, "Statistic cannot be null");
        Preconditions.checkArgument(statistic.getType() == Type.UNTYPED, "Must supply additional parameter for this statistic");
        return manager.getStat(CraftStatistic.getNMSStatistic(statistic));
    }

    public static void incrementStatistic(ServerStatHandler manager, Statistic statistic, int amount) {
        Preconditions.checkArgument(amount > 0, "Amount must be greater than 0");
        setStatistic(manager, statistic, getStatistic(manager, statistic) + amount);
    }

    public static void decrementStatistic(ServerStatHandler manager, Statistic statistic, int amount) {
        Preconditions.checkArgument(amount > 0, "Amount must be greater than 0");
        setStatistic(manager, statistic, getStatistic(manager, statistic) - amount);
    }

    public static void setStatistic(ServerStatHandler manager, Statistic statistic, int newValue) {
        Preconditions.checkArgument(statistic != null, "Statistic cannot be null");
        Preconditions.checkArgument(statistic.getType() == Type.UNTYPED, "Must supply additional parameter for this statistic");
        Preconditions.checkArgument(newValue >= 0, "Value must be greater than or equal to 0");
        net.minecraft.stat.Stat nmsStatistic = CraftStatistic.getNMSStatistic(statistic);
        manager.a(null, nmsStatistic, newValue);;
    }

    public static void incrementStatistic(ServerStatHandler manager, Statistic statistic, Material material) {
        incrementStatistic(manager, statistic, material, 1);
    }

    public static void decrementStatistic(ServerStatHandler manager, Statistic statistic, Material material) {
        decrementStatistic(manager, statistic, material, 1);
    }

    public static int getStatistic(ServerStatHandler manager, Statistic statistic, Material material) {
        Preconditions.checkArgument(statistic != null, "Statistic cannot be null");
        Preconditions.checkArgument(material != null, "Material cannot be null");
        Preconditions.checkArgument(statistic.getType() == Type.BLOCK || statistic.getType() == Type.ITEM, "This statistic does not take a Material parameter");
        net.minecraft.stat.Stat nmsStatistic = CraftStatistic.getMaterialStatistic(statistic, material);
        Preconditions.checkArgument(nmsStatistic != null, "The supplied Material %s does not have a corresponding statistic", material);
        return manager.getStat(nmsStatistic);
    }

    public static void incrementStatistic(ServerStatHandler manager, Statistic statistic, Material material, int amount) {
        Preconditions.checkArgument(amount > 0, "Amount must be greater than 0");
        setStatistic(manager, statistic, material, getStatistic(manager, statistic, material) + amount);
    }

    public static void decrementStatistic(ServerStatHandler manager, Statistic statistic, Material material, int amount) {
        Preconditions.checkArgument(amount > 0, "Amount must be greater than 0");
        setStatistic(manager, statistic, material, getStatistic(manager, statistic, material) - amount);
    }

    public static void setStatistic(ServerStatHandler manager, Statistic statistic, Material material, int newValue) {
        Preconditions.checkArgument(statistic != null, "Statistic cannot be null");
        Preconditions.checkArgument(material != null, "Material cannot be null");
        Preconditions.checkArgument(newValue >= 0, "Value must be greater than or equal to 0");
        Preconditions.checkArgument(statistic.getType() == Type.BLOCK || statistic.getType() == Type.ITEM, "This statistic does not take a Material parameter");
        net.minecraft.stat.Stat nmsStatistic = CraftStatistic.getMaterialStatistic(statistic, material);
        Preconditions.checkArgument(nmsStatistic != null, "The supplied Material %s does not have a corresponding statistic", material);
        manager.a(null, nmsStatistic, newValue);
    }

    public static void incrementStatistic(ServerStatHandler manager, Statistic statistic, EntityType entityType) {
        incrementStatistic(manager, statistic, entityType, 1);
    }

    public static void decrementStatistic(ServerStatHandler manager, Statistic statistic, EntityType entityType) {
        decrementStatistic(manager, statistic, entityType, 1);
    }

    public static int getStatistic(ServerStatHandler manager, Statistic statistic, EntityType entityType) {
        Preconditions.checkArgument(statistic != null, "Statistic cannot be null");
        Preconditions.checkArgument(entityType != null, "EntityType cannot be null");
        Preconditions.checkArgument(statistic.getType() == Type.ENTITY, "This statistic does not take an EntityType parameter");
        net.minecraft.stat.Stat nmsStatistic = CraftStatistic.getEntityStatistic(statistic, entityType);
        Preconditions.checkArgument(nmsStatistic != null, "The supplied EntityType %s does not have a corresponding statistic", entityType);
        return manager.getStat(nmsStatistic);
    }

    public static void incrementStatistic(ServerStatHandler manager, Statistic statistic, EntityType entityType, int amount) {
        Preconditions.checkArgument(amount > 0, "Amount must be greater than 0");
        setStatistic(manager, statistic, entityType, getStatistic(manager, statistic, entityType) + amount);
    }

    public static void decrementStatistic(ServerStatHandler manager, Statistic statistic, EntityType entityType, int amount) {
        Preconditions.checkArgument(amount > 0, "Amount must be greater than 0");
        setStatistic(manager, statistic, entityType, getStatistic(manager, statistic, entityType) - amount);
    }

    public static void setStatistic(ServerStatHandler manager, Statistic statistic, EntityType entityType, int newValue) {
        Preconditions.checkArgument(statistic != null, "Statistic cannot be null");
        Preconditions.checkArgument(entityType != null, "EntityType cannot be null");
        Preconditions.checkArgument(newValue >= 0, "Value must be greater than or equal to 0");
        Preconditions.checkArgument(statistic.getType() == Type.ENTITY, "This statistic does not take an EntityType parameter");
        net.minecraft.stat.Stat nmsStatistic = CraftStatistic.getEntityStatistic(statistic, entityType);
        Preconditions.checkArgument(nmsStatistic != null, "The supplied EntityType %s does not have a corresponding statistic", entityType);
        manager.a(null, nmsStatistic, newValue);
    }
}