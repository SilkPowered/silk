package cx.rain.silk.mixins_legacy.statics.item;

import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;

public class BoneMealItemStatics {
    public static ActionResult applyBonemeal(ItemUsageContext context) {
        var item = context.getStack().getItem();
        if (item instanceof BoneMealItem boneMealItem) {
            return boneMealItem.useOnBlock(context);
        }
        return ActionResult.PASS;
    }
}
