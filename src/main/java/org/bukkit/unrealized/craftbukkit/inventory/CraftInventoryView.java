package org.bukkit.unrealized.craftbukkit.inventory;

import com.google.common.base.Preconditions;
import net.minecraft.network.protocol.game.PacketPlayOutOpenWindow;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.inventory.Containers;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.unrealized.GameMode;
import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.unrealized.craftbukkit.util.CraftChatMessage;
import org.bukkit.unrealized.entity.HumanEntity;
import org.bukkit.unrealized.entity.Player;
import org.bukkit.unrealized.event.inventory.InventoryType;
import org.bukkit.unrealized.inventory.Inventory;
import org.bukkit.unrealized.inventory.InventoryView;
import org.bukkit.unrealized.inventory.ItemStack;

public class CraftInventoryView extends InventoryView {
    private final Container container;
    private final CraftHumanEntity player;
    private final CraftInventory viewing;
    private final String originalTitle;
    private String title;

    public CraftInventoryView(HumanEntity player, Inventory viewing, Container container) {
        // TODO: Should we make sure it really IS a CraftHumanEntity first? And a CraftInventory?
        this.player = (CraftHumanEntity) player;
        this.viewing = (CraftInventory) viewing;
        this.container = container;
        this.originalTitle = CraftChatMessage.fromComponent(container.getTitle());
        this.title = originalTitle;
    }

    @Override
    public Inventory getTopInventory() {
        return viewing;
    }

    @Override
    public Inventory getBottomInventory() {
        return player.getInventory();
    }

    @Override
    public HumanEntity getPlayer() {
        return player;
    }

    @Override
    public InventoryType getType() {
        InventoryType type = viewing.getType();
        if (type == InventoryType.CRAFTING && player.getGameMode() == GameMode.CREATIVE) {
            return InventoryType.CREATIVE;
        }
        return type;
    }

    @Override
    public void setItem(int slot, ItemStack item) {
        net.minecraft.world.item.ItemStack stack = CraftItemStack.asNMSCopy(item);
        if (slot >= 0) {
            container.getSlot(slot).set(stack);
        } else {
            player.getHandle().drop(stack, false);
        }
    }

    @Override
    public ItemStack getItem(int slot) {
        if (slot < 0) {
            return null;
        }
        return CraftItemStack.asCraftMirror(container.getSlot(slot).getItem());
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
        sendInventoryTitleChange(this, title);
        this.title = title;
    }

    public boolean isInTop(int rawSlot) {
        return rawSlot < viewing.getSize();
    }

    public Container getHandle() {
        return container;
    }

    public static void sendInventoryTitleChange(InventoryView view, String title) {
        Preconditions.checkArgument(view != null, "InventoryView cannot be null");
        Preconditions.checkArgument(title != null, "Title cannot be null");
        Preconditions.checkArgument(view.getPlayer() instanceof Player, "NPCs are not currently supported for this function");
        Preconditions.checkArgument(view.getTopInventory().getType().isCreatable(), "Only creatable inventories can have their title changed");

        final EntityPlayer entityPlayer = (EntityPlayer) ((CraftHumanEntity) view.getPlayer()).getHandle();
        final int containerId = entityPlayer.containerMenu.containerId;
        final Containers<?> windowType = CraftContainer.getNotchInventoryType(view.getTopInventory());
        entityPlayer.connection.send(new PacketPlayOutOpenWindow(containerId, windowType, CraftChatMessage.fromString(title)[0]));
        ((Player) view.getPlayer()).updateInventory();
    }
}