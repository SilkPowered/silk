package org.bukkit.unrealized.craftbukkit.entity;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import java.util.Set;
import net.minecraft.world.entity.boss.EntityComplexPart;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonControllerPhase;
import org.bukkit.unrealized.boss.BossBar;
import org.bukkit.unrealized.boss.DragonBattle;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.unrealized.craftbukkit.boss.CraftDragonBattle;
import org.bukkit.unrealized.entity.ComplexEntityPart;
import org.bukkit.unrealized.entity.EnderDragon;
import org.bukkit.unrealized.entity.EntityType;

public class CraftEnderDragon extends CraftMob implements EnderDragon, CraftEnemy {

    public CraftEnderDragon(CraftServer server, EntityEnderDragon entity) {
        super(server, entity);
    }

    @Override
    public Set<ComplexEntityPart> getParts() {
        Builder<ComplexEntityPart> builder = ImmutableSet.builder();

        for (EntityComplexPart part : getHandle().subEntities) {
            builder.add((ComplexEntityPart) part.getBukkitEntity());
        }

        return builder.build();
    }

    @Override
    public EntityEnderDragon getHandle() {
        return (EntityEnderDragon) entity;
    }

    @Override
    public String toString() {
        return "CraftEnderDragon";
    }

    @Override
    public EntityType getType() {
        return EntityType.ENDER_DRAGON;
    }

    @Override
    public Phase getPhase() {
        return Phase.values()[getHandle().getEntityData().get(EntityEnderDragon.DATA_PHASE)];
    }

    @Override
    public void setPhase(Phase phase) {
        getHandle().getPhaseManager().setPhase(getMinecraftPhase(phase));
    }

    public static Phase getBukkitPhase(DragonControllerPhase phase) {
        return Phase.values()[phase.getId()];
    }

    public static DragonControllerPhase getMinecraftPhase(Phase phase) {
        return DragonControllerPhase.getById(phase.ordinal());
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
