package org.bukkit.craftbukkit.entity;

import com.google.common.base.Preconditions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.WitherEntity;
import org.bukkit.boss.BossBar;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.boss.CraftBossBar;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Wither;

public class CraftWither extends CraftMonster implements Wither {

    private BossBar bossBar;

    public CraftWither(CraftServer server, WitherEntity entity) {
        super(server, entity);

        if (entity.bossEvent != null) {
            this.bossBar = new CraftBossBar(entity.bossEvent);
        }
    }

    @Override
    public WitherEntity getHandle() {
        return (WitherEntity) entity;
    }

    @Override
    public String toString() {
        return "CraftWither";
    }

    @Override
    public BossBar getBossBar() {
        return bossBar;
    }

    @Override
    public void setTarget(Head head, LivingEntity livingEntity) {
        Preconditions.checkArgument(head != null, "head cannot be null");

        int entityId = (livingEntity != null) ? livingEntity.getEntityId() : 0;
        getHandle().setTrackedEntityId(head.ordinal(), entityId);
    }

    @Override
    public LivingEntity getTarget(Head head) {
        Preconditions.checkArgument(head != null, "head cannot be null");

        int entityId = getHandle().getTrackedEntityId(head.ordinal());
        if (entityId == 0) {
            return null;
        }
        Entity target = getHandle().getWorld().getEntityById(entityId);
        return (target != null) ? (LivingEntity) target.getBukkitEntity() : null;
    }

    @Override
    public int getInvulnerabilityTicks() {
        return getHandle().getInvulnerableTimer();
    }

    @Override
    public void setInvulnerabilityTicks(int ticks) {
        Preconditions.checkArgument(ticks >= 0, "ticks must be >=0");

        getHandle().setInvulTimer(ticks);
    }
}
