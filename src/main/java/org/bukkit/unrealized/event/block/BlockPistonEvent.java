package org.bukkit.unrealized.event.block;

import org.bukkit.unrealized.Material;
import org.bukkit.unrealized.block.Block;
import org.bukkit.unrealized.block.BlockFace;
import org.bukkit.unrealized.event.Cancellable;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a piston block is triggered
 */
public abstract class BlockPistonEvent extends BlockEvent implements Cancellable {
    private boolean cancelled;
    private final BlockFace direction;

    public BlockPistonEvent(@NotNull final Block block, @NotNull final BlockFace direction) {
        super(block);
        this.direction = direction;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * Returns true if the Piston in the event is sticky.
     *
     * @return stickiness of the piston
     */
    public boolean isSticky() {
        return block.getType() == Material.STICKY_PISTON || block.getType() == Material.MOVING_PISTON;
    }

    /**
     * Return the direction in which the piston will operate.
     *
     * @return direction of the piston
     */
    @NotNull
    public BlockFace getDirection() {
        // Both are meh!
        // return ((PistonBaseMaterial) block.getType().getNewData(block.getData())).getFacing();
        // return ((PistonBaseMaterial) block.getState().getData()).getFacing();
        return direction;
    }
}
