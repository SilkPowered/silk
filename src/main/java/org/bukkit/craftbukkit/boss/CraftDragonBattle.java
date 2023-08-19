package org.bukkit.craftbukkit.boss;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.dragon.EnderDragonFight;
import net.minecraft.entity.boss.dragon.EnderDragonSpawnState;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.DragonBattle;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftEnderCrystal;
import org.bukkit.craftbukkit.util.CraftLocation;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EnderDragon;

public class CraftDragonBattle implements DragonBattle {

    private final EnderDragonFight handle;

    public CraftDragonBattle(EnderDragonFight handle) {
        this.handle = handle;
    }

    @Override
    public EnderDragon getEnderDragon() {
        Entity entity = handle.level.getEntity(handle.dragonUUID);
        return (entity != null) ? (EnderDragon) entity.getBukkitEntity() : null;
    }

    @Override
    public BossBar getBossBar() {
        return new CraftBossBar(handle.dragonEvent);
    }

    @Override
    public Location getEndPortalLocation() {
        if (handle.portalLocation == null) {
            return null;
        }

        return CraftLocation.toBukkit(this.handle.portalLocation, this.handle.level.getWorld());
    }

    @Override
    public boolean generateEndPortal(boolean withPortals) {
        if (handle.portalLocation != null || handle.findEndPortal() != null) {
            return false;
        }

        this.handle.generateEndPortal(withPortals);
        return true;
    }

    @Override
    public boolean hasBeenPreviouslyKilled() {
        return handle.hasPreviouslyKilled();
    }

    @Override
    public void initiateRespawn() {
        this.handle.tryRespawn();
    }

    @Override
    public boolean initiateRespawn(Collection<EnderCrystal> list) {
        if (hasBeenPreviouslyKilled() && getRespawnPhase() == RespawnPhase.NONE) {
            // Copy from EnderDragonBattle#tryRespawn for generate exit portal if not exists
            if (this.handle.portalLocation == null) {
                BlockPattern.Result shapedetector_shapedetectorcollection = this.handle.findEndPortal();
                if (shapedetector_shapedetectorcollection == null) {
                    this.handle.generateEndPortal(true);
                }
            }

            list = (list != null) ? new ArrayList<>(list) : Collections.emptyList();
            list.removeIf(enderCrystal -> {
                if (enderCrystal == null) {
                    return true;
                }

                World world = enderCrystal.getWorld();
                return !((CraftWorld) world).getHandle().equals(handle.level);
            });

            return this.handle.respawnDragon(list.stream().map(enderCrystal -> ((CraftEnderCrystal) enderCrystal).getHandle()).collect(Collectors.toList()));
        }
        return false;
    }

    @Override
    public RespawnPhase getRespawnPhase() {
        return toBukkitRespawnPhase(handle.respawnStage);
    }

    @Override
    public boolean setRespawnPhase(RespawnPhase phase) {
        Preconditions.checkArgument(phase != null && phase != RespawnPhase.NONE, "Invalid respawn phase provided: %s", phase);

        if (handle.respawnStage == null) {
            return false;
        }

        this.handle.setSpawnState(toNMSRespawnPhase(phase));
        return true;
    }

    @Override
    public void resetCrystals() {
        this.handle.resetEndCrystals();
    }

    @Override
    public int hashCode() {
        return handle.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CraftDragonBattle && ((CraftDragonBattle) obj).handle == this.handle;
    }

    private RespawnPhase toBukkitRespawnPhase(EnderDragonSpawnState phase) {
        return (phase != null) ? RespawnPhase.values()[phase.ordinal()] : RespawnPhase.NONE;
    }

    private EnderDragonSpawnState toNMSRespawnPhase(RespawnPhase phase) {
        return (phase != RespawnPhase.NONE) ? EnderDragonSpawnState.values()[phase.ordinal()] : null;
    }
}
