package cx.rain.silk.mixins.interfaces.entity.player;

import com.mojang.datafixers.util.Either;
import cx.rain.silk.mixins.interfaces.entity.IEntityMixin;
import cx.rain.silk.mixins.interfaces.entity.ILivingEntityMixin;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityExhaustionEvent;

public interface IPlayerEntityMixin extends ILivingEntityMixin {
    @Override
    CraftHumanEntity getBukkitEntity();

    ItemEntity drop(ItemStack itemstack, boolean flag, boolean flag1, boolean callEvent);

    Either<PlayerEntity.SleepFailureReason, Unit> trySleep(BlockPos blockposition, boolean force);

    void addExhaustion(float f, EntityExhaustionEvent.ExhaustionReason reason);

    boolean dropShoulderEntity(NbtCompound compound);
}
