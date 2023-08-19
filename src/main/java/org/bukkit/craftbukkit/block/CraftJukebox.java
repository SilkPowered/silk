package org.bukkit.craftbukkit.block;

import net.minecraft.block.JukeboxBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.item.ItemStack;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Jukebox;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.inventory.CraftInventoryJukebox;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;
import org.bukkit.inventory.JukeboxInventory;

public class CraftJukebox extends CraftBlockEntityState<JukeboxBlockEntity> implements Jukebox {

    public CraftJukebox(World world, JukeboxBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    @Override
    public JukeboxInventory getSnapshotInventory() {
        return new CraftInventoryJukebox(this.getSnapshot());
    }

    @Override
    public JukeboxInventory getInventory() {
        if (!this.isPlaced()) {
            return this.getSnapshotInventory();
        }

        return new CraftInventoryJukebox(this.getTileEntity());
    }

    @Override
    public boolean update(boolean force, boolean applyPhysics) {
        boolean result = super.update(force, applyPhysics);

        if (result && this.isPlaced() && this.getType() == Material.JUKEBOX) {
            Material record = this.getPlaying();
            getWorldHandle().setBlockState(this.getPosition(), data, 3);

            BlockEntity tileEntity = this.getTileEntityFromWorld();
            if (tileEntity instanceof JukeboxBlockEntity jukebox) {
                CraftWorld world = (CraftWorld) this.getWorld();
                if (record.isAir()) {
                    jukebox.setDisc(ItemStack.EMPTY);
                    world.playEffect(this.getLocation(), Effect.IRON_DOOR_CLOSE, 0); // TODO: Fix this enum constant. This stops jukeboxes
                } else {
                    world.playEffect(this.getLocation(), Effect.RECORD_PLAY, record);
                }
            }
        }

        return result;
    }

    @Override
    public Material getPlaying() {
        return getRecord().getType();
    }

    @Override
    public void setPlaying(Material record) {
        if (record == null || CraftMagicNumbers.getItem(record) == null) {
            record = Material.AIR;
        }

        setRecord(new org.bukkit.inventory.ItemStack(record));
    }

    @Override
    public boolean hasRecord() {
        return getHandle().get(JukeboxBlock.HAS_RECORD) && !getPlaying().isAir();
    }

    @Override
    public org.bukkit.inventory.ItemStack getRecord() {
        ItemStack record = this.getSnapshot().getStack();
        return CraftItemStack.asBukkitCopy(record);
    }

    @Override
    public void setRecord(org.bukkit.inventory.ItemStack record) {
        ItemStack nms = CraftItemStack.asNMSCopy(record);

        JukeboxBlockEntity snapshot = this.getSnapshot();
        snapshot.setDisc(nms);
        snapshot.recordStartedTick = snapshot.tickCount;
        snapshot.isPlaying = !nms.isEmpty();

        this.data = this.data.with(JukeboxBlock.HAS_RECORD, !nms.isEmpty());
    }

    @Override
    public boolean isPlaying() {
        requirePlaced();

        BlockEntity tileEntity = this.getTileEntityFromWorld();
        return tileEntity instanceof JukeboxBlockEntity jukebox && jukebox.isPlayingRecord();
    }

    @Override
    public boolean startPlaying() {
        requirePlaced();

        BlockEntity tileEntity = this.getTileEntityFromWorld();
        if (!(tileEntity instanceof JukeboxBlockEntity jukebox)) {
            return false;
        }

        ItemStack record = jukebox.getStack();
        if (record.isEmpty() || isPlaying()) {
            return false;
        }

        jukebox.isPlaying = true;
        jukebox.recordStartedTick = jukebox.tickCount;
        getWorld().playEffect(getLocation(), Effect.RECORD_PLAY, CraftMagicNumbers.getMaterial(record.getItem()));
        return true;
    }

    @Override
    public void stopPlaying() {
        requirePlaced();

        BlockEntity tileEntity = this.getTileEntityFromWorld();
        if (!(tileEntity instanceof JukeboxBlockEntity jukebox)) {
            return;
        }

        jukebox.isPlaying = false;
        getWorld().playEffect(getLocation(), Effect.IRON_DOOR_CLOSE, 0); // TODO: Fix this enum constant. This stops jukeboxes
    }

    @Override
    public boolean eject() {
        ensureNoWorldGeneration();

        BlockEntity tileEntity = this.getTileEntityFromWorld();
        if (!(tileEntity instanceof JukeboxBlockEntity)) return false;

        JukeboxBlockEntity jukebox = (JukeboxBlockEntity) tileEntity;
        boolean result = !jukebox.getStack().isEmpty();
        jukebox.dropRecord();
        return result;
    }
}
