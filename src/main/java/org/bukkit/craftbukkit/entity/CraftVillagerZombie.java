package org.bukkit.craftbukkit.entity;

import com.google.common.base.Preconditions;
import java.util.Locale;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.util.CraftNamespacedKey;
import org.bukkit.entity.Villager;
import org.bukkit.entity.ZombieVillager;

public class CraftVillagerZombie extends CraftZombie implements ZombieVillager {

    public CraftVillagerZombie(CraftServer server, ZombieVillagerEntity entity) {
        super(server, entity);
    }

    @Override
    public ZombieVillagerEntity getHandle() {
        return (ZombieVillagerEntity) super.getHandle();
    }

    @Override
    public String toString() {
        return "CraftVillagerZombie";
    }

    @Override
    public Villager.Profession getVillagerProfession() {
        return Villager.Profession.valueOf(Registries.VILLAGER_PROFESSION.b(getHandle().gj().getProfession()).getPath().toUpperCase(Locale.ROOT));
    }

    @Override
    public void setVillagerProfession(Villager.Profession profession) {
        Preconditions.checkArgument(profession != null, "Villager.Profession cannot be null");
        getHandle().a(getHandle().gj().withProfession(Registries.VILLAGER_PROFESSION.a(new Identifier(profession.name().toLowerCase(Locale.ROOT)))));
    }

    @Override
    public Villager.Type getVillagerType() {
        return Villager.Type.valueOf(Registries.VILLAGER_TYPE.b(getHandle().gj().getType()).getPath().toUpperCase(Locale.ROOT));
    }

    @Override
    public void setVillagerType(Villager.Type type) {
        Preconditions.checkArgument(type != null, "Villager.Type cannot be null");
        getHandle().a(getHandle().gj().withType(Registries.VILLAGER_TYPE.a(CraftNamespacedKey.toMinecraft(type.getKey()))));
    }

    @Override
    public boolean isConverting() {
        return getHandle().isConverting();
    }

    @Override
    public int getConversionTime() {
        Preconditions.checkState(isConverting(), "Entity not converting");

        return getHandle().villagerConversionTime;
    }

    @Override
    public void setConversionTime(int time) {
        if (time < 0) {
            getHandle().villagerConversionTime = -1;
            getHandle().getDataTracker().set(ZombieVillagerEntity.DATA_CONVERTING_ID, false);
            getHandle().conversionStarter = null;
            getHandle().removeEffect(StatusEffects.DAMAGE_BOOST, org.bukkit.event.entity.EntityPotionEffectEvent.Cause.CONVERSION);
        } else {
            getHandle().setConverting(null, time);
        }
    }

    @Override
    public OfflinePlayer getConversionPlayer() {
        return (getHandle().conversionStarter == null) ? null : Bukkit.getOfflinePlayer(getHandle().conversionStarter);
    }

    @Override
    public void setConversionPlayer(OfflinePlayer conversionPlayer) {
        if (!this.isConverting()) return;
        getHandle().conversionStarter = (conversionPlayer == null) ? null : conversionPlayer.getUniqueId();
    }
}
