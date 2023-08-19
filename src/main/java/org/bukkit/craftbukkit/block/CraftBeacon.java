package org.bukkit.craftbukkit.block;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ContainerLock;
import org.bukkit.World;
import org.bukkit.block.Beacon;
import org.bukkit.craftbukkit.util.CraftChatMessage;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CraftBeacon extends CraftBlockEntityState<BeaconBlockEntity> implements Beacon {

    public CraftBeacon(World world, BeaconBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    @Override
    public Collection<LivingEntity> getEntitiesInRange() {
        ensureNoWorldGeneration();

        BlockEntity tileEntity = this.getTileEntityFromWorld();
        if (tileEntity instanceof BeaconBlockEntity) {
            BeaconBlockEntity beacon = (BeaconBlockEntity) tileEntity;

            Collection<PlayerEntity> nms = BeaconBlockEntity.getHumansInRange(beacon.getLevel(), beacon.getBlockPos(), beacon.levels);
            Collection<LivingEntity> bukkit = new ArrayList<LivingEntity>(nms.size());

            for (PlayerEntity human : nms) {
                bukkit.add(human.getBukkitEntity());
            }

            return bukkit;
        }

        // block is no longer a beacon
        return new ArrayList<LivingEntity>();
    }

    @Override
    public int getTier() {
        return this.getSnapshot().levels;
    }

    @Override
    public PotionEffect getPrimaryEffect() {
        return this.getSnapshot().getPrimaryEffect();
    }

    @Override
    public void setPrimaryEffect(PotionEffectType effect) {
        this.getSnapshot().primaryPower = (effect != null) ? StatusEffect.byId(effect.getId()) : null;
    }

    @Override
    public PotionEffect getSecondaryEffect() {
        return this.getSnapshot().getSecondaryEffect();
    }

    @Override
    public void setSecondaryEffect(PotionEffectType effect) {
        this.getSnapshot().secondaryPower = (effect != null) ? StatusEffect.byId(effect.getId()) : null;
    }

    @Override
    public String getCustomName() {
        BeaconBlockEntity beacon = this.getSnapshot();
        return beacon.name != null ? CraftChatMessage.fromComponent(beacon.name) : null;
    }

    @Override
    public void setCustomName(String name) {
        this.getSnapshot().setCustomName(CraftChatMessage.fromStringOrNull(name));
    }

    @Override
    public boolean isLocked() {
        return !this.getSnapshot().lockKey.key.isEmpty();
    }

    @Override
    public String getLock() {
        return this.getSnapshot().lockKey.key;
    }

    @Override
    public void setLock(String key) {
        this.getSnapshot().lockKey = (key == null) ? ContainerLock.NO_LOCK : new ContainerLock(key);
    }
}
