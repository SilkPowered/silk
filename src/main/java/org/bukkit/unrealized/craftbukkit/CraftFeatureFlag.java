package org.bukkit.unrealized.craftbukkit;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import org.bukkit.unrealized.FeatureFlag;
import org.bukkit.unrealized.NamespacedKey;
import org.bukkit.unrealized.craftbukkit.util.CraftNamespacedKey;
import org.jetbrains.annotations.NotNull;

public class CraftFeatureFlag implements FeatureFlag {

    private final NamespacedKey namespacedKey;
    private final net.minecraft.world.flag.FeatureFlag featureFlag;

    public CraftFeatureFlag(MinecraftKey minecraftKey, net.minecraft.world.flag.FeatureFlag featureFlag) {
        this.namespacedKey = CraftNamespacedKey.fromMinecraft(minecraftKey);
        this.featureFlag = featureFlag;
    }

    public net.minecraft.world.flag.FeatureFlag getHandle() {
        return this.featureFlag;
    }

    @NotNull
    @Override
    public NamespacedKey getKey() {
        return this.namespacedKey;
    }

    @Override
    public String toString() {
        return "CraftDataPack{key=" + this.getKey() + ",keyUniverse=" + this.getHandle().universe.toString() + "}";
    }

    public static Set<CraftFeatureFlag> getFromNMS(FeatureFlagSet featureFlagSet) {
        Set<CraftFeatureFlag> set = new HashSet<>();
        FeatureFlags.REGISTRY.names.forEach((minecraftkey, featureflag) -> {
            if (featureFlagSet.contains(featureflag)) {
                set.add(new CraftFeatureFlag(minecraftkey, featureflag));
            }
        });
        return set;
    }

    public static CraftFeatureFlag getFromNMS(NamespacedKey namespacedKey) {
        return FeatureFlags.REGISTRY.names.entrySet().stream().filter(entry -> CraftNamespacedKey.fromMinecraft(entry.getKey()).equals(namespacedKey)).findFirst().map(entry -> new CraftFeatureFlag(entry.getKey(), entry.getValue())).orElse(null);
    }
}
