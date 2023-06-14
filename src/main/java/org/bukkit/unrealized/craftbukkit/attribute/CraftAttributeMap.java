package org.bukkit.unrealized.craftbukkit.attribute;

import com.google.common.base.Preconditions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.AttributeBase;
import net.minecraft.world.entity.ai.attributes.AttributeMapBase;
import org.bukkit.unrealized.Registry;
import org.bukkit.unrealized.attribute.Attributable;
import org.bukkit.unrealized.attribute.Attribute;
import org.bukkit.unrealized.attribute.AttributeInstance;
import org.bukkit.unrealized.craftbukkit.util.CraftNamespacedKey;

public class CraftAttributeMap implements Attributable {

    private final AttributeMapBase handle;

    public CraftAttributeMap(AttributeMapBase handle) {
        this.handle = handle;
    }

    @Override
    public AttributeInstance getAttribute(Attribute attribute) {
        Preconditions.checkArgument(attribute != null, "attribute");
        net.minecraft.world.entity.ai.attributes.AttributeModifiable nms = handle.getInstance(toMinecraft(attribute));

        return (nms == null) ? null : new CraftAttributeInstance(nms, attribute);
    }

    public static AttributeBase toMinecraft(Attribute attribute) {
        return BuiltInRegistries.ATTRIBUTE.get(CraftNamespacedKey.toMinecraft(attribute.getKey()));
    }

    public static Attribute fromMinecraft(String nms) {
        return Registry.ATTRIBUTE.get(CraftNamespacedKey.fromString(nms));
    }
}
