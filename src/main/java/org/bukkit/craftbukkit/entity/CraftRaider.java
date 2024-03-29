package org.bukkit.craftbukkit.entity;

import com.google.common.base.Preconditions;
import net.minecraft.entity.raid.RaiderEntity;
import org.bukkit.Raid;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.CraftRaid;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftSound;
import org.bukkit.craftbukkit.block.CraftBlock;
import org.bukkit.entity.Raider;

public abstract class CraftRaider extends CraftMonster implements Raider {

    public CraftRaider(CraftServer server, RaiderEntity entity) {
        super(server, entity);
    }

    @Override
    public RaiderEntity getHandle() {
        return (RaiderEntity) super.getHandle();
    }

    @Override
    public String toString() {
        return "CraftRaider";
    }

    @Override
    public void setRaid(Raid raid) {
        getHandle().setRaid(raid != null ? ((CraftRaid) raid).getHandle() : null);
    }

    @Override
    public Raid getRaid() {
        return getHandle().getRaid() == null ? null : new CraftRaid(getHandle().getRaid());
    }

    @Override
    public void setWave(int wave) {
        Preconditions.checkArgument(wave >= 0, "wave must be >= 0");
        getHandle().setWave(wave);
    }

    @Override
    public int getWave() {
        return getHandle().getWave();
    }

    @Override
    public Block getPatrolTarget() {
        return getHandle().getPatrolTarget() == null ? null : CraftBlock.at(getHandle().getWorld(), getHandle().getPatrolTarget());
    }

    @Override
    public void setPatrolTarget(Block block) {
        if (block == null) {
            getHandle().setPatrolTarget(null);
        } else {
            Preconditions.checkArgument(block.getWorld().equals(this.getWorld()), "Block must be in same world");
            getHandle().setPatrolTarget(((CraftBlock) block).getPosition());
        }
    }

    @Override
    public boolean isPatrolLeader() {
        return getHandle().isPatrolLeader();
    }

    @Override
    public void setPatrolLeader(boolean leader) {
        getHandle().setPatrolLeader(leader);
    }

    @Override
    public boolean isCanJoinRaid() {
        return getHandle().canJoinRaid();
    }

    @Override
    public void setCanJoinRaid(boolean join) {
        getHandle().setAbleToJoinRaid(join);
    }

    @Override
    public boolean isCelebrating() {
        return getHandle().isCelebrating();
    }

    @Override
    public void setCelebrating(boolean celebrating) {
        getHandle().setCelebrating(celebrating);
    }

    @Override
    public int getTicksOutsideRaid() {
        return getHandle().getOutOfRaidCounter();
    }

    @Override
    public void setTicksOutsideRaid(int ticks) {
        Preconditions.checkArgument(ticks >= 0, "ticks must be >= 0");
        getHandle().setOutOfRaidCounter(ticks);
    }

    @Override
    public Sound getCelebrationSound() {
        return CraftSound.getBukkit(getHandle().getCelebratingSound());
    }
}
