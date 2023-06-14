package cx.rain.silk.mixins.mixin.item;

import cx.rain.silk.mixins.interfaces.item.IItemStackMixin;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemStack.class)
public class ItemStackMixin implements IItemStackMixin {
    @Mutable
    @Shadow @Final @Deprecated private @Nullable Item item;

    @Override
    public void setItem(@Nullable Item item) {
        this.item = item;
    }
}
