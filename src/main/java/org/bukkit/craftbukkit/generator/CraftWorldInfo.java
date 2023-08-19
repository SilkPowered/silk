package org.bukkit.craftbukkit.generator;

import java.util.UUID;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.level.LevelProperties;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.storage.LevelStorage;
import org.bukkit.World;
import org.bukkit.craftbukkit.util.WorldUUID;
import org.bukkit.generator.WorldInfo;

public class CraftWorldInfo implements WorldInfo {

    private final String name;
    private final UUID uuid;
    private final World.Environment environment;
    private final long seed;
    private final int minHeight;
    private final int maxHeight;

    public CraftWorldInfo(ServerWorldProperties worldDataServer, LevelStorage.Session session, World.Environment environment, DimensionType dimensionManager) {
        this.name = worldDataServer.getLevelName();
        this.uuid = WorldUUID.getUUID(session.levelDirectory.path().toFile());
        this.environment = environment;
        this.seed = ((LevelProperties) worldDataServer).A().getSeed();
        this.minHeight = dimensionManager.comp_651();
        this.maxHeight = dimensionManager.comp_651() + dimensionManager.comp_652();
    }

    public CraftWorldInfo(String name, UUID uuid, World.Environment environment, long seed, int minHeight, int maxHeight) {
        this.name = name;
        this.uuid = uuid;
        this.environment = environment;
        this.seed = seed;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public UUID getUID() {
        return uuid;
    }

    @Override
    public World.Environment getEnvironment() {
        return environment;
    }

    @Override
    public long getSeed() {
        return seed;
    }

    @Override
    public int getMinHeight() {
        return minHeight;
    }

    @Override
    public int getMaxHeight() {
        return maxHeight;
    }
}