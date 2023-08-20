package cx.rain.silk.mixins_legacy.mixin.entity;

import cx.rain.silk.mixins_legacy.bridge.entity.IEntityBridge;
import cx.rain.silk.mixins_legacy.bridge.server.world.IServerWorldBridge;
import cx.rain.silk.mixins_legacy.interfaces.entity.IEntityMixin;
import cx.rain.silk.mixins_legacy.interfaces.world.IWorldMixin;
import cx.rain.silk.mixins_legacy.statics.server.world.ServerWorldStatics;
import cx.rain.silk.mixins_legacy.wrappers.world.TeleportTargetWrapper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.PositionImpl;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Deprecated
@Mixin(net.minecraft.entity.Entity.class)
public abstract class EntityMixin implements IEntityMixin, IEntityBridge {
    @Shadow private World world;

    @Shadow public abstract World getWorld();

    @Shadow public abstract boolean isRemoved();

    @Shadow protected abstract @Nullable TeleportTarget getTeleportTarget(ServerWorld destination);

    @Shadow private float yaw;
    @Shadow private float pitch;

    @Shadow public abstract void setPos(double x, double y, double z);

    @Shadow public abstract void setYaw(float yaw);

    @Shadow public abstract void setPitch(float pitch);

    @Shadow public abstract void resetPosition();

    @Shadow protected abstract void refreshPosition();

    @Shadow public abstract void setVelocity(Vec3d velocity);

    @Shadow public abstract void detach();

    @Shadow public abstract EntityType<?> getType();

    @Shadow protected abstract void removeFromDimension();

    private CraftEntity silk$bukkitEntity;

    @Override
    public CraftEntity getBukkitEntity() {
        if (silk$bukkitEntity == null) {
            silk$bukkitEntity = CraftEntity.getEntity(((IWorldMixin) world).getCraftServer(), (net.minecraft.entity.Entity) (Object) this);
        }

        return silk$bukkitEntity;
    }

    private boolean silk$generation;

    @Override
    public boolean silk$getGeneration() {
        return silk$generation;
    }

    @Override
    public void silk$setGeneration(boolean value) {
        silk$generation = value;
    }

    @Override
    public void silk$setBukkitEntity(CraftEntity value) {
        silk$bukkitEntity = value;
    }

    @Override
    public void moveTo(double x, double y, double z, float yaw, float pitch) {
        this.setPos(x, y, z);
        this.setYaw(yaw);
        this.setPitch(pitch);
        this.resetPosition();
        this.refreshPosition();
    }

    @Override
    public net.minecraft.entity.Entity teleportTo(ServerWorld world, PositionImpl location) {
        // CraftBukkit end
        if (this.getWorld() instanceof ServerWorld && !this.isRemoved()) {
            this.getWorld().getProfiler().push("changeDimension");
            // CraftBukkit start
            // this.unRide();
            if (world == null) {
                return null;
            }
            // CraftBukkit end
            this.getWorld().getProfiler().push("reposition");
            TeleportTarget shapedetectorshape = (location == null) ? this.getTeleportTarget(world) : new TeleportTargetWrapper(new Vec3d(location.getX(), location.getY(), location.getZ()), Vec3d.ZERO, this.yaw, this.pitch, world, null); // CraftBukkit

            if (shapedetectorshape == null) {
                return null;
            } else {
                // CraftBukkit start
                if (shapedetectorshape instanceof TeleportTargetWrapper wrapper) {
                    world = wrapper.world;
                }

                if (world == this.world) {
                    // SPIGOT-6782: Just move the entity if a plugin changed the world to the one the entity is already in
                    moveTo(shapedetectorshape.position.x, shapedetectorshape.position.y, shapedetectorshape.position.z, shapedetectorshape.yaw, shapedetectorshape.pitch);
                    setVelocity(shapedetectorshape.velocity);
                    return (net.minecraft.entity.Entity) (Object) this;
                }
                this.detach();
                // CraftBukkit end

                this.world.getProfiler().swap("reloading");
                net.minecraft.entity.Entity entity = this.getType().create(world);

                if (entity != null) {
                    entity.copyFrom((Entity) (Object) this);
                    ((IEntityMixin) entity).moveTo(shapedetectorshape.position.x, shapedetectorshape.position.y, shapedetectorshape.position.z, shapedetectorshape.yaw, entity.getPitch());
                    entity.setVelocity(shapedetectorshape.velocity);
                    world.onDimensionChanged(entity);
                    if (((IServerWorldBridge) world).silk$getTypeKey() == DimensionOptions.END) { // CraftBukkit
                        ServerWorldStatics.makeObsidianPlatform(world, (Entity) (Object) this); // CraftBukkit
                    }
                    // CraftBukkit start - Forward the CraftEntity to the new entity
                    this.getBukkitEntity().setHandle(entity);
                    ((IEntityBridge) entity).silk$setBukkitEntity(this.getBukkitEntity());

                    if ((Object) this instanceof MobEntity) {
                        ((MobEntity) (Object) this).detachLeash(true, false); // Unleash to prevent duping of leads.
                    }
                    // CraftBukkit end
                }

                this.removeFromDimension();
                this.world.getProfiler().pop();
                ((ServerWorld) this.world).resetIdleTimeout();
                world.resetIdleTimeout();
                this.world.getProfiler().pop();
                return entity;
            }
        } else {
            return null;
        }
    }
}
