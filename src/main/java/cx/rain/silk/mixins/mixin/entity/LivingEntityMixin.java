package cx.rain.silk.mixins.mixin.entity;

import cx.rain.silk.mixins.interfaces.entity.ILivingEntityMixin;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Deprecated
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements ILivingEntityMixin {

    @Shadow public abstract void equipStack(EquipmentSlot var1, ItemStack var2);

    //    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack, boolean isSilent) {
        equipStack(slot, stack);
    }
}
