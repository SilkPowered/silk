package org.bukkit.craftbukkit.entity;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import java.util.Set;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.DragonBattle;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.boss.CraftDragonBattle;
import org.bukkit.entity.ComplexEntityPart;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EnderDragon.Phase;

public class CraftEnderDragon extends CraftMob implements EnderDragon, CraftEnemy {

    public CraftEnderDragon(CraftServer server, EnderDragonEntity entity) {
        super(server, entity);
    }

    @Override
    public Set<ComplexEntityPart> getParts() {
        Builder<ComplexEntityPart> builder = ImmutableSet.builder();

        for (EnderDragonPart part : getHandle().subEntities) {
            builder.add((ComplexEntityPart) part.getBukkitEntity());
        }

        return builder.build();
    }

    @Override
    public EnderDragonEntity getHandle() {
        return (EnderDragonEntity) entity;
    }

    @Override
    public String toString() {
        return "CraftEnderDragon";
    }

    @Override
    public Phase getPhase() {
        return Phase.values()[getHandle().getEntityData().get(EnderDragonEntity.DATA_PHASE)];
    }

    @Override
    public void setPhase(Phase phase) {
        getHandle().getPhaseManager().setPhase(getMinecraftPhase(phase));
    }

    public static Phase getBukkitPhase(PhaseType phase) {
        return Phase.values()[phase.getId()];
    }

    public static PhaseType getMinecraftPhase(Phase phase) {
        return PhaseType.getById(phase.ordinal());
    }

    @Override
    public BossBar getBossBar() {
        DragonBattle battle = getDragonBattle();
        return battle != null ? battle.getBossBar() : null;
    }

    @Override
    public DragonBattle getDragonBattle() {
        return getHandle().getDragonFight() != null ? new CraftDragonBattle(getHandle().getDragonFight()) : null;
    }

    @Override
    public int getDeathAnimationTicks() {
        return getHandle().dragonDeathTime;
    }
}
