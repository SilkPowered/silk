package org.bukkit.unrealized.craftbukkit.advancement;

import java.util.Collection;
import java.util.Collections;
import net.minecraft.advancements.Advancement;
import org.bukkit.unrealized.NamespacedKey;
import org.bukkit.unrealized.advancement.AdvancementDisplay;
import org.bukkit.unrealized.advancement.Advancement;
import org.bukkit.unrealized.craftbukkit.util.CraftNamespacedKey;

public class CraftAdvancement implements Advancement {

    private final Advancement handle;

    public CraftAdvancement(Advancement handle) {
        this.handle = handle;
    }

    public Advancement getHandle() {
        return handle;
    }

    @Override
    public NamespacedKey getKey() {
        return CraftNamespacedKey.fromMinecraft(handle.getId());
    }

    @Override
    public Collection<String> getCriteria() {
        return Collections.unmodifiableCollection(handle.getCriteria().keySet());
    }

    @Override
    public AdvancementDisplay getDisplay() {
        if (handle.getDisplay() == null) {
            return null;
        }

        return new CraftAdvancementDisplay(handle.getDisplay());
    }
}
