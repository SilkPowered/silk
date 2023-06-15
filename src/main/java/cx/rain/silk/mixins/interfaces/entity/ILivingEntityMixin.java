package cx.rain.silk.mixins.interfaces.entity;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public interface ILivingEntityMixin {
    void setItemSlot(EquipmentSlot slot, ItemStack stack, boolean isSilent);
}
