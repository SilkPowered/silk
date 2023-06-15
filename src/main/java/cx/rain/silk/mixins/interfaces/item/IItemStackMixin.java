package cx.rain.silk.mixins.interfaces.item;

import net.minecraft.item.Item;

public interface IItemStackMixin {
    void setItem(Item item);

    void convertStack(int version);
}
