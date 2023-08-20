package cx.rain.silk.patched;

import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.DoubleInventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;

public class ChestBlockDoubleInventory implements NamedScreenHandlerFactory {
    private final ChestBlockEntity tileentitychest;
    private final ChestBlockEntity tileentitychest1;
    public final DoubleInventory inventorylargechest;

    public ChestBlockDoubleInventory(ChestBlockEntity tileentitychest, ChestBlockEntity tileentitychest1,
                                     DoubleInventory inventorylargechest) {
        this.tileentitychest = tileentitychest;
        this.tileentitychest1 = tileentitychest1;
        this.inventorylargechest = inventorylargechest;
    }

    @Override
    public Text getDisplayName() {
        return tileentitychest.hasCustomName() ? tileentitychest.getDisplayName() : (tileentitychest1.hasCustomName() ? tileentitychest1.getDisplayName() : Text.translatable("container.chestDouble"));
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        if (tileentitychest.checkUnlocked(player) && tileentitychest1.checkUnlocked(player)) {
            tileentitychest.checkLootInteraction(playerInventory.player);
            tileentitychest1.checkLootInteraction(playerInventory.player);
            return GenericContainerScreenHandler.createGeneric9x6(syncId, playerInventory, inventorylargechest);
        } else {
            return null;
        }
    }
}
