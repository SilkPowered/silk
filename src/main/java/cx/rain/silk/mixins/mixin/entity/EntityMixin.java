package cx.rain.silk.mixins.mixin.entity;

import cx.rain.silk.mixins.interfaces.entity.IEntityMixin;
import cx.rain.silk.mixins.interfaces.world.IWorldMixin;
import net.minecraft.world.World;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(net.minecraft.entity.Entity.class)
public abstract class EntityMixin implements IEntityMixin {
    @Shadow private World world;
    private CraftEntity bukkitEntity;

    @Override
    public Entity getBukkitEntity() {
        if (bukkitEntity == null) {
            bukkitEntity = CraftEntity.getEntity(((IWorldMixin) world).getCraftServer(), (net.minecraft.entity.Entity) (Object) this);
        }

        return bukkitEntity;
    }
}
