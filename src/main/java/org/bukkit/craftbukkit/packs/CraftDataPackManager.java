package org.bukkit.craftbukkit.packs;

import com.google.common.base.Preconditions;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import net.minecraft.registry.Registries;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.util.Identifier;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;
import org.bukkit.entity.EntityType;
import org.bukkit.packs.DataPack;
import org.bukkit.packs.DataPackManager;

public class CraftDataPackManager implements DataPackManager {

    private final ResourcePackManager handle;

    public CraftDataPackManager(ResourcePackManager resourcePackRepository) {
        this.handle = resourcePackRepository;
    }

    public ResourcePackManager getHandle() {
        return this.handle;
    }

    @Override
    public Collection<DataPack> getDataPacks() {
        // Based in the command for datapacks need reload for get the updated list of datapacks
        this.getHandle().scanPacks();

        Collection<ResourcePackProfile> availablePacks = this.getHandle().getProfiles();
        return availablePacks.stream().map(CraftDataPack::new).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public DataPack getDataPack(NamespacedKey namespacedKey) {
        Preconditions.checkArgument(namespacedKey != null, "namespacedKey cannot be null");

        return new CraftDataPack(this.getHandle().getProfile(namespacedKey.getKey()));
    }

    @Override
    public Collection<DataPack> getEnabledDataPacks(World world) {
        Preconditions.checkArgument(world != null, "world cannot be null");

        CraftWorld craftWorld = ((CraftWorld) world);
        return craftWorld.getHandle().serverLevelData.F().comp_1010().getEnabled().stream().map(packName -> {
            ResourcePackProfile resourcePackLoader = this.getHandle().getProfile(packName);
            if (resourcePackLoader != null) {
                return new CraftDataPack(resourcePackLoader);
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Collection<DataPack> getDisabledDataPacks(World world) {
        Preconditions.checkArgument(world != null, "world cannot be null");

        CraftWorld craftWorld = ((CraftWorld) world);
        return craftWorld.getHandle().serverLevelData.F().comp_1010().getDisabled().stream().map(packName -> {
            ResourcePackProfile resourcePackLoader = this.getHandle().getProfile(packName);
            if (resourcePackLoader != null) {
                return new CraftDataPack(resourcePackLoader);
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean isEnabledByFeature(Material material, World world) {
        Preconditions.checkArgument(material != null, "material cannot be null");
        Preconditions.checkArgument(material.isItem() || material.isBlock(), "material need to be a item or block");
        Preconditions.checkArgument(world != null, "world cannot be null");

        CraftWorld craftWorld = ((CraftWorld) world);
        if (material.isItem()) {
            return CraftMagicNumbers.getItem(material).isEnabled(craftWorld.getHandle().G());
        } else if (material.isBlock()) {
            return CraftMagicNumbers.getBlock(material).isEnabled(craftWorld.getHandle().G());
        }
        return false;
    }

    @Override
    public boolean isEnabledByFeature(EntityType entityType, World world) {
        Preconditions.checkArgument(entityType != null, "entityType cannot be null");
        Preconditions.checkArgument(world != null, "world cannot be null");
        Preconditions.checkArgument(entityType != EntityType.UNKNOWN, "EntityType.UNKNOWN its not allowed here");

        CraftWorld craftWorld = ((CraftWorld) world);
        net.minecraft.entity.EntityType<?> nmsEntity = Registries.ENTITY_TYPE.a(new Identifier(entityType.getKey().getKey()));
        return nmsEntity.isEnabled(craftWorld.getHandle().G());
    }
}
