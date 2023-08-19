package org.bukkit.craftbukkit.block;

import com.google.common.base.Preconditions;
import java.util.Optional;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.MobSpawnerEntry;
import org.bukkit.World;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;

public class CraftCreatureSpawner extends CraftBlockEntityState<MobSpawnerBlockEntity> implements CreatureSpawner {

    public CraftCreatureSpawner(World world, MobSpawnerBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    @Override
    public EntityType getSpawnedType() {
        MobSpawnerEntry spawnData = this.getSnapshot().getLogic().nextSpawnData;
        if (spawnData == null) {
            return null;
        }

        Optional<net.minecraft.entity.EntityType<?>> type = net.minecraft.entity.EntityType.fromNbt(spawnData.getNbt());
        return type.map(entityTypes -> EntityType.fromName(net.minecraft.entity.EntityType.getId(entityTypes).getPath())).orElse(null);
    }

    @Override
    public void setSpawnedType(EntityType entityType) {
        if (entityType == null) {
            this.getSnapshot().getLogic().spawnPotentials = DataPool.empty(); // need clear the spawnPotentials to avoid nextSpawnData being replaced later
            this.getSnapshot().getLogic().nextSpawnData = new MobSpawnerEntry();
            return;
        }
        Preconditions.checkArgument(entityType != EntityType.UNKNOWN, "Can't spawn EntityType %s from mob spawners!", entityType);

        Random rand = (this.isPlaced()) ? this.getWorldHandle().getRandom() : Random.create();
        this.getSnapshot().setEntityType(net.minecraft.entity.EntityType.get(entityType.getName()).get(), rand);
    }

    @Override
    public String getCreatureTypeName() {
        MobSpawnerEntry spawnData = this.getSnapshot().getLogic().nextSpawnData;
        if (spawnData == null) {
            return null;
        }

        Optional<net.minecraft.entity.EntityType<?>> type = net.minecraft.entity.EntityType.fromNbt(spawnData.getNbt());
        return type.map(entityTypes -> net.minecraft.entity.EntityType.getId(entityTypes).getPath()).orElse(null);
    }

    @Override
    public void setCreatureTypeByName(String creatureType) {
        // Verify input
        EntityType type = EntityType.fromName(creatureType);
        if (type == null) {
            setSpawnedType(null);
            return;
        }
        setSpawnedType(type);
    }

    @Override
    public int getDelay() {
        return this.getSnapshot().getLogic().spawnDelay;
    }

    @Override
    public void setDelay(int delay) {
        this.getSnapshot().getLogic().spawnDelay = delay;
    }

    @Override
    public int getMinSpawnDelay() {
        return this.getSnapshot().getLogic().minSpawnDelay;
    }

    @Override
    public void setMinSpawnDelay(int spawnDelay) {
        Preconditions.checkArgument(spawnDelay <= getMaxSpawnDelay(), "Minimum Spawn Delay must be less than or equal to Maximum Spawn Delay");
        this.getSnapshot().getLogic().minSpawnDelay = spawnDelay;
    }

    @Override
    public int getMaxSpawnDelay() {
        return this.getSnapshot().getLogic().maxSpawnDelay;
    }

    @Override
    public void setMaxSpawnDelay(int spawnDelay) {
        Preconditions.checkArgument(spawnDelay > 0, "Maximum Spawn Delay must be greater than 0.");
        Preconditions.checkArgument(spawnDelay >= getMinSpawnDelay(), "Maximum Spawn Delay must be greater than or equal to Minimum Spawn Delay");
        this.getSnapshot().getLogic().maxSpawnDelay = spawnDelay;
    }

    @Override
    public int getMaxNearbyEntities() {
        return this.getSnapshot().getLogic().maxNearbyEntities;
    }

    @Override
    public void setMaxNearbyEntities(int maxNearbyEntities) {
        this.getSnapshot().getLogic().maxNearbyEntities = maxNearbyEntities;
    }

    @Override
    public int getSpawnCount() {
        return this.getSnapshot().getLogic().spawnCount;
    }

    @Override
    public void setSpawnCount(int count) {
        this.getSnapshot().getLogic().spawnCount = count;
    }

    @Override
    public int getRequiredPlayerRange() {
        return this.getSnapshot().getLogic().requiredPlayerRange;
    }

    @Override
    public void setRequiredPlayerRange(int requiredPlayerRange) {
        this.getSnapshot().getLogic().requiredPlayerRange = requiredPlayerRange;
    }

    @Override
    public int getSpawnRange() {
        return this.getSnapshot().getLogic().spawnRange;
    }

    @Override
    public void setSpawnRange(int spawnRange) {
        this.getSnapshot().getLogic().spawnRange = spawnRange;
    }
}
