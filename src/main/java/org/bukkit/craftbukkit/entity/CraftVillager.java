package org.bukkit.craftbukkit.entity;

import com.google.common.base.Preconditions;
import java.util.Locale;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.VillagerProfession;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.util.CraftLocation;
import org.bukkit.craftbukkit.util.CraftNamespacedKey;
import org.bukkit.entity.Villager;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CraftVillager extends CraftAbstractVillager implements Villager {

    public CraftVillager(CraftServer server, VillagerEntity entity) {
        super(server, entity);
    }

    @Override
    public VillagerEntity getHandle() {
        return (VillagerEntity) entity;
    }

    @Override
    public String toString() {
        return "CraftVillager";
    }

    @Override
    public void remove() {
        getHandle().releaseAllTickets();

        super.remove();
    }

    @Override
    public Profession getProfession() {
        return CraftVillager.nmsToBukkitProfession(getHandle().gj().getProfession());
    }

    @Override
    public void setProfession(Profession profession) {
        Preconditions.checkArgument(profession != null, "Profession cannot be null");
        getHandle().a(getHandle().gj().withProfession(CraftVillager.bukkitToNmsProfession(profession)));
    }

    @Override
    public Type getVillagerType() {
        return Type.valueOf(Registries.VILLAGER_TYPE.b(getHandle().gj().getType()).getPath().toUpperCase(Locale.ROOT));
    }

    @Override
    public void setVillagerType(Type type) {
        Preconditions.checkArgument(type != null, "Type cannot be null");
        getHandle().a(getHandle().gj().withType(Registries.VILLAGER_TYPE.a(CraftNamespacedKey.toMinecraft(type.getKey()))));
    }

    @Override
    public int getVillagerLevel() {
        return getHandle().gj().getLevel();
    }

    @Override
    public void setVillagerLevel(int level) {
        Preconditions.checkArgument(1 <= level && level <= 5, "level (%s) must be between [1, 5]", level);

        getHandle().a(getHandle().gj().withLevel(level));
    }

    @Override
    public int getVillagerExperience() {
        return getHandle().r();
    }

    @Override
    public void setVillagerExperience(int experience) {
        Preconditions.checkArgument(experience >= 0, "Experience (%s) must be positive", experience);

        getHandle().setExperience(experience);
    }

    @Override
    public boolean sleep(Location location) {
        Preconditions.checkArgument(location != null, "Location cannot be null");
        Preconditions.checkArgument(location.getWorld() != null, "Location needs to be in a world");
        Preconditions.checkArgument(location.getWorld().equals(getWorld()), "Cannot sleep across worlds");
        Preconditions.checkState(!getHandle().generation, "Cannot sleep during world generation");

        BlockPos position = CraftLocation.toBlockPosition(location);
        BlockState iblockdata = getHandle().getWorld().a_(position);
        if (!(iblockdata.getBlock() instanceof BedBlock)) {
            return false;
        }

        getHandle().b(position);
        return true;
    }

    @Override
    public void wakeup() {
        Preconditions.checkState(isSleeping(), "Cannot wakeup if not sleeping");
        Preconditions.checkState(!getHandle().generation, "Cannot wakeup during world generation");

        getHandle().fz();
    }

    @Override
    public void shakeHead() {
        getHandle().sayNo();
    }

    @Override
    public ZombieVillager zombify() {
        ZombieVillagerEntity entityzombievillager = ZombieEntity.zombifyVillager(getHandle().getWorld().getMinecraftWorld(), getHandle(), getHandle().di(), isSilent(), CreatureSpawnEvent.SpawnReason.CUSTOM);
        return (entityzombievillager != null) ? (ZombieVillager) entityzombievillager.getBukkitEntity() : null;
    }

    public static Profession nmsToBukkitProfession(VillagerProfession nms) {
        return Profession.valueOf(Registries.VILLAGER_PROFESSION.b(nms).getPath().toUpperCase(Locale.ROOT));
    }

    public static VillagerProfession bukkitToNmsProfession(Profession bukkit) {
        return Registries.VILLAGER_PROFESSION.a(CraftNamespacedKey.toMinecraft(bukkit.getKey()));
    }
}
