package org.bukkit.unrealized.event.player;

import org.bukkit.unrealized.Material;
import org.bukkit.unrealized.block.Block;
import org.bukkit.unrealized.block.BlockFace;
import org.bukkit.unrealized.entity.Player;
import org.bukkit.unrealized.event.HandlerList;
import org.bukkit.unrealized.inventory.EquipmentSlot;
import org.bukkit.unrealized.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a player fills a bucket
 */
public class PlayerBucketFillEvent extends PlayerBucketEvent {
    private static final HandlerList handlers = new HandlerList();

    @Deprecated
    public PlayerBucketFillEvent(@NotNull final Player who, @NotNull final Block blockClicked, @NotNull final BlockFace blockFace, @NotNull final Material bucket, @NotNull final ItemStack itemInHand) {
        super(who, blockClicked, blockFace, bucket, itemInHand);
    }

    @Deprecated
    public PlayerBucketFillEvent(@NotNull final Player who, @NotNull final Block block, @NotNull final Block blockClicked, @NotNull final BlockFace blockFace, @NotNull final Material bucket, @NotNull final ItemStack itemInHand) {
        super(who, block, blockClicked, blockFace, bucket, itemInHand);
    }

    public PlayerBucketFillEvent(@NotNull final Player who, @NotNull final Block block, @NotNull final Block blockClicked, @NotNull final BlockFace blockFace, @NotNull final Material bucket, @NotNull final ItemStack itemInHand, @NotNull final EquipmentSlot hand) {
        super(who, block, blockClicked, blockFace, bucket, itemInHand, hand);
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
