package cx.rain.silk.mixins.mixin.entity;

import cx.rain.silk.mixins.interfaces.entity.ILivingEntityMixin;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.bukkit.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends net.minecraft.entity.LivingEntity implements ILivingEntityMixin {
    protected LivingEntityMixin(EntityType<? extends net.minecraft.entity.LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void setItemSlot(EquipmentSlot slot, ItemStack stack, boolean isSilent) {
        equipStack(slot, stack);
    }
}
