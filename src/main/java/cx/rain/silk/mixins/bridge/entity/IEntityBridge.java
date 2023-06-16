package cx.rain.silk.mixins.bridge.entity;

import org.bukkit.craftbukkit.entity.CraftEntity;

@Deprecated
public interface IEntityBridge {
    boolean silk$getGeneration();
    void silk$setGeneration(boolean value);

    void silk$setBukkitEntity(CraftEntity value);
}
