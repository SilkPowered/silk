package org.bukkit.craftbukkit.entity;

import com.google.common.base.Preconditions;
import net.minecraft.entity.mob.GuardianEntity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.LivingEntity;

public class CraftGuardian extends CraftMonster implements Guardian {

    private static final int MINIMUM_ATTACK_TICKS = -10;

    public CraftGuardian(CraftServer server, GuardianEntity entity) {
        super(server, entity);
    }

    @Override
    public GuardianEntity getHandle() {
        return (GuardianEntity) super.getHandle();
    }

    @Override
    public String toString() {
        return "CraftGuardian";
    }

    @Override
    public void setTarget(LivingEntity target) {
        super.setTarget(target);

        // clean up laser target, when target is removed
        if (target == null) {
            getHandle().setBeamTarget(0);
        }
    }

    @Override
    public boolean setLaser(boolean activated) {
        if (activated) {
            LivingEntity target = getTarget();
            if (target == null) {
                return false;
            }

            getHandle().setBeamTarget(target.getEntityId());
        } else {
            getHandle().setBeamTarget(0);
        }

        return true;
    }

    @Override
    public boolean hasLaser() {
        return getHandle().hasBeamTarget();
    }

    @Override
    public int getLaserDuration() {
        return getHandle().getWarmupTime();
    }

    @Override
    public void setLaserTicks(int ticks) {
        Preconditions.checkArgument(ticks >= MINIMUM_ATTACK_TICKS, "ticks must be >= %s. Given %s", MINIMUM_ATTACK_TICKS, ticks);

        GuardianEntity.FireBeamGoal goal = getHandle().guardianAttackGoal;
        if (goal != null) {
            goal.attackTime = ticks;
        }
    }

    @Override
    public int getLaserTicks() {
        GuardianEntity.FireBeamGoal goal = getHandle().guardianAttackGoal;
        return (goal != null) ? goal.attackTime : MINIMUM_ATTACK_TICKS;
    }

    @Override
    public boolean isElder() {
        return false;
    }

    @Override
    public void setElder(boolean shouldBeElder) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public boolean isMoving() {
        return getHandle().areSpikesRetracted();
    }
}
