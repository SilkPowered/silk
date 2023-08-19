package org.bukkit.craftbukkit.entity;

import com.google.common.base.Preconditions;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.math.BlockPos;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.projectiles.ProjectileSource;

public class CraftArrow extends AbstractProjectile implements AbstractArrow {

    public CraftArrow(CraftServer server, PersistentProjectileEntity entity) {
        super(server, entity);
    }

    @Override
    public void setKnockbackStrength(int knockbackStrength) {
        Preconditions.checkArgument(knockbackStrength >= 0, "Knockback value (%s) cannot be negative", knockbackStrength);
        getHandle().setPunch(knockbackStrength);
    }

    @Override
    public int getKnockbackStrength() {
        return getHandle().knockback;
    }

    @Override
    public double getDamage() {
        return getHandle().getDamage();
    }

    @Override
    public void setDamage(double damage) {
        Preconditions.checkArgument(damage >= 0, "Damage value (%s) must be positive", damage);
        getHandle().setDamage(damage);
    }

    @Override
    public int getPierceLevel() {
        return getHandle().getPierceLevel();
    }

    @Override
    public void setPierceLevel(int pierceLevel) {
        Preconditions.checkArgument(0 <= pierceLevel && pierceLevel <= Byte.MAX_VALUE, "Pierce level (%s) out of range, expected 0 < level < 127", pierceLevel);

        getHandle().setPierceLevel((byte) pierceLevel);
    }

    @Override
    public boolean isCritical() {
        return getHandle().isCritical();
    }

    @Override
    public void setCritical(boolean critical) {
        getHandle().setCritical(critical);
    }

    @Override
    public ProjectileSource getShooter() {
        return getHandle().projectileSource;
    }

    @Override
    public void setShooter(ProjectileSource shooter) {
        if (shooter instanceof Entity) {
            getHandle().b(((CraftEntity) shooter).getHandle());
        } else {
            getHandle().b(null);
        }
        getHandle().projectileSource = shooter;
    }

    @Override
    public boolean isInBlock() {
        return getHandle().inGround;
    }

    @Override
    public Block getAttachedBlock() {
        if (!isInBlock()) {
            return null;
        }

        BlockPos pos = getHandle().di();
        return getWorld().getBlockAt(pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public PickupStatus getPickupStatus() {
        return PickupStatus.values()[getHandle().pickup.ordinal()];
    }

    @Override
    public void setPickupStatus(PickupStatus status) {
        Preconditions.checkArgument(status != null, "PickupStatus cannot be null");
        getHandle().pickup = PersistentProjectileEntity.PickupPermission.fromOrdinal(status.ordinal());
    }

    @Override
    public void setTicksLived(int value) {
        super.setTicksLived(value);

        // Second field for EntityArrow
        getHandle().life = value;
    }

    @Override
    public boolean isShotFromCrossbow() {
        return getHandle().isShotFromCrossbow();
    }

    @Override
    public void setShotFromCrossbow(boolean shotFromCrossbow) {
        getHandle().setShotFromCrossbow(shotFromCrossbow);
    }

    @Override
    public PersistentProjectileEntity getHandle() {
        return (PersistentProjectileEntity) entity;
    }

    @Override
    public String toString() {
        return "CraftArrow";
    }
}
