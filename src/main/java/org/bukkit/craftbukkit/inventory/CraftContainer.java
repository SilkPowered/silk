package org.bukkit.craftbukkit.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.BeaconScreenHandler;
import net.minecraft.screen.BlastFurnaceScreenHandler;
import net.minecraft.screen.BrewingStandScreenHandler;
import net.minecraft.screen.CartographyTableScreenHandler;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.screen.FurnaceScreenHandler;
import net.minecraft.screen.Generic3x3ContainerScreenHandler;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.GrindstoneScreenHandler;
import net.minecraft.screen.HopperScreenHandler;
import net.minecraft.screen.LecternScreenHandler;
import net.minecraft.screen.LoomScreenHandler;
import net.minecraft.screen.MerchantScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.screen.SmokerScreenHandler;
import net.minecraft.screen.StonecutterScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class CraftContainer extends ScreenHandler {

    private final InventoryView view;
    private InventoryType cachedType;
    private ScreenHandler delegate;

    public CraftContainer(InventoryView view, PlayerEntity player, int id) {
        super(getNotchInventoryType(view.getTopInventory()), id);
        this.view = view;
        // TODO: Do we need to check that it really is a CraftInventory?
        net.minecraft.inventory.Inventory top = ((CraftInventory) view.getTopInventory()).getInventory();
        PlayerInventory bottom = (PlayerInventory) ((CraftInventory) view.getBottomInventory()).getInventory();
        cachedType = view.getType();
        setupSlots(top, bottom, player);
    }

    public CraftContainer(final Inventory inventory, final PlayerEntity player, int id) {
        this(new InventoryView() {

            private final String originalTitle = (inventory instanceof CraftInventoryCustom) ? ((CraftInventoryCustom.MinecraftInventory) ((CraftInventory) inventory).getInventory()).getTitle() : inventory.getType().getDefaultTitle();
            private String title = originalTitle;

            @Override
            public Inventory getTopInventory() {
                return inventory;
            }

            @Override
            public Inventory getBottomInventory() {
                return getPlayer().getInventory();
            }

            @Override
            public HumanEntity getPlayer() {
                return player.getBukkitEntity();
            }

            @Override
            public InventoryType getType() {
                return inventory.getType();
            }

            @Override
            public String getTitle() {
                return title;
            }

            @Override
            public String getOriginalTitle() {
                return originalTitle;
            }

            @Override
            public void setTitle(String title) {
                CraftInventoryView.sendInventoryTitleChange(this, title);
                this.title = title;
            }

        }, player, id);
    }

    @Override
    public InventoryView getBukkitView() {
        return view;
    }

    public static ScreenHandlerType getNotchInventoryType(Inventory inventory) {
        switch (inventory.getType()) {
            case PLAYER:
            case CHEST:
            case ENDER_CHEST:
            case BARREL:
                switch (inventory.getSize()) {
                    case 9:
                        return ScreenHandlerType.GENERIC_9x1;
                    case 18:
                        return ScreenHandlerType.GENERIC_9x2;
                    case 27:
                        return ScreenHandlerType.GENERIC_9x3;
                    case 36:
                    case 41: // PLAYER
                        return ScreenHandlerType.GENERIC_9x4;
                    case 45:
                        return ScreenHandlerType.GENERIC_9x5;
                    case 54:
                        return ScreenHandlerType.GENERIC_9x6;
                    default:
                        throw new IllegalArgumentException("Unsupported custom inventory size " + inventory.getSize());
                }
            case WORKBENCH:
                return ScreenHandlerType.CRAFTING;
            case FURNACE:
                return ScreenHandlerType.FURNACE;
            case DISPENSER:
                return ScreenHandlerType.GENERIC_3x3;
            case ENCHANTING:
                return ScreenHandlerType.ENCHANTMENT;
            case BREWING:
                return ScreenHandlerType.BREWING_STAND;
            case BEACON:
                return ScreenHandlerType.BEACON;
            case ANVIL:
                return ScreenHandlerType.ANVIL;
            case HOPPER:
                return ScreenHandlerType.HOPPER;
            case DROPPER:
                return ScreenHandlerType.GENERIC_3x3;
            case SHULKER_BOX:
                return ScreenHandlerType.SHULKER_BOX;
            case BLAST_FURNACE:
                return ScreenHandlerType.BLAST_FURNACE;
            case LECTERN:
                return ScreenHandlerType.LECTERN;
            case SMOKER:
                return ScreenHandlerType.SMOKER;
            case LOOM:
                return ScreenHandlerType.LOOM;
            case CARTOGRAPHY:
                return ScreenHandlerType.CARTOGRAPHY_TABLE;
            case GRINDSTONE:
                return ScreenHandlerType.GRINDSTONE;
            case STONECUTTER:
                return ScreenHandlerType.STONECUTTER;
            case SMITHING:
            case SMITHING_NEW:
                return ScreenHandlerType.SMITHING;
            case CREATIVE:
            case CRAFTING:
            case MERCHANT:
                throw new IllegalArgumentException("Can't open a " + inventory.getType() + " inventory!");
            default:
                // TODO: If it reaches the default case, should we throw an error?
                return ScreenHandlerType.GENERIC_9x3;
        }
    }

    private void setupSlots(net.minecraft.inventory.Inventory top, PlayerInventory bottom, PlayerEntity entityhuman) {
        int windowId = -1;
        switch (cachedType) {
            case CREATIVE:
                break; // TODO: This should be an error?
            case PLAYER:
            case CHEST:
            case ENDER_CHEST:
            case BARREL:
                delegate = new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9x3, windowId, bottom, top, top.getContainerSize() / 9);
                break;
            case DISPENSER:
            case DROPPER:
                delegate = new Generic3x3ContainerScreenHandler(windowId, bottom, top);
                break;
            case FURNACE:
                delegate = new FurnaceScreenHandler(windowId, bottom, top, new ArrayPropertyDelegate(4));
                break;
            case CRAFTING: // TODO: This should be an error?
            case WORKBENCH:
                setupWorkbench(top, bottom); // SPIGOT-3812 - manually set up slots so we can use the delegated inventory and not the automatically created one
                break;
            case ENCHANTING:
                delegate = new EnchantmentScreenHandler(windowId, bottom);
                break;
            case BREWING:
                delegate = new BrewingStandScreenHandler(windowId, bottom, top, new ArrayPropertyDelegate(2));
                break;
            case HOPPER:
                delegate = new HopperScreenHandler(windowId, bottom, top);
                break;
            case ANVIL:
                setupAnvil(top, bottom); // SPIGOT-6783 - manually set up slots so we can use the delegated inventory and not the automatically created one
                break;
            case BEACON:
                delegate = new BeaconScreenHandler(windowId, bottom);
                break;
            case SHULKER_BOX:
                delegate = new ShulkerBoxScreenHandler(windowId, bottom, top);
                break;
            case BLAST_FURNACE:
                delegate = new BlastFurnaceScreenHandler(windowId, bottom, top, new ArrayPropertyDelegate(4));
                break;
            case LECTERN:
                delegate = new LecternScreenHandler(windowId, top, new ArrayPropertyDelegate(1), bottom);
                break;
            case SMOKER:
                delegate = new SmokerScreenHandler(windowId, bottom, top, new ArrayPropertyDelegate(4));
                break;
            case LOOM:
                delegate = new LoomScreenHandler(windowId, bottom);
                break;
            case CARTOGRAPHY:
                delegate = new CartographyTableScreenHandler(windowId, bottom);
                break;
            case GRINDSTONE:
                delegate = new GrindstoneScreenHandler(windowId, bottom);
                break;
            case STONECUTTER:
                delegate = new StonecutterScreenHandler(windowId, bottom);
                break;
            case MERCHANT:
                delegate = new MerchantScreenHandler(windowId, bottom);
                break;
            case SMITHING:
            case SMITHING_NEW:
                setupSmithing(top, bottom); // SPIGOT-6783 - manually set up slots so we can use the delegated inventory and not the automatically created one
                break;
        }

        if (delegate != null) {
            this.lastSlots = delegate.lastSlots;
            this.slots = delegate.slots;
            this.remoteSlots = delegate.remoteSlots;
        }

        // SPIGOT-4598 - we should still delegate the shift click handler
        switch (cachedType) {
            case WORKBENCH:
                delegate = new CraftingScreenHandler(windowId, bottom);
                break;
            case ANVIL:
                delegate = new AnvilScreenHandler(windowId, bottom);
                break;
        }
    }

    private void setupWorkbench(net.minecraft.inventory.Inventory top, net.minecraft.inventory.Inventory bottom) {
        // This code copied from ContainerWorkbench
        this.addSlot(new Slot(top, 0, 124, 35));

        int row;
        int col;

        for (row = 0; row < 3; ++row) {
            for (col = 0; col < 3; ++col) {
                this.addSlot(new Slot(top, 1 + col + row * 3, 30 + col * 18, 17 + row * 18));
            }
        }

        for (row = 0; row < 3; ++row) {
            for (col = 0; col < 9; ++col) {
                this.addSlot(new Slot(bottom, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        for (col = 0; col < 9; ++col) {
            this.addSlot(new Slot(bottom, col, 8 + col * 18, 142));
        }
        // End copy from ContainerWorkbench
    }

    private void setupAnvil(net.minecraft.inventory.Inventory top, net.minecraft.inventory.Inventory bottom) {
        // This code copied from ContainerAnvilAbstract
        this.addSlot(new Slot(top, 0, 27, 47));
        this.addSlot(new Slot(top, 1, 76, 47));
        this.addSlot(new Slot(top, 2, 134, 47));

        int row;
        int col;

        for (row = 0; row < 3; ++row) {
            for (col = 0; col < 9; ++col) {
                this.addSlot(new Slot(bottom, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        for (row = 0; row < 9; ++row) {
            this.addSlot(new Slot(bottom, row, 8 + row * 18, 142));
        }
        // End copy from ContainerAnvilAbstract
    }

    private void setupSmithing(net.minecraft.inventory.Inventory top, net.minecraft.inventory.Inventory bottom) {
        // This code copied from ContainerSmithing
        this.addSlot(new Slot(top, 0, 8, 48));
        this.addSlot(new Slot(top, 1, 26, 48));
        this.addSlot(new Slot(top, 2, 44, 48));
        this.addSlot(new Slot(top, 3, 98, 48));

        int row;
        int col;

        for (row = 0; row < 3; ++row) {
            for (col = 0; col < 9; ++col) {
                this.addSlot(new Slot(bottom, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        for (row = 0; row < 9; ++row) {
            this.addSlot(new Slot(bottom, row, 8 + row * 18, 142));
        }
        // End copy from ContainerSmithing
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity entityhuman, int i) {
        return (delegate != null) ? delegate.quickMoveStack(entityhuman, i) : ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(PlayerEntity entity) {
        return true;
    }

    @Override
    public ScreenHandlerType<?> getType() {
        return getNotchInventoryType(view.getTopInventory());
    }
}
