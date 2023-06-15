package org.bukkit.craftbukkit.block;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.TileState;
import org.bukkit.craftbukkit.util.CraftLocation;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CraftBlockEntityState<T extends BlockEntity> extends CraftBlockState implements TileState {

    private final T tileEntity;
    private final T snapshot;

    public CraftBlockEntityState(World world, T tileEntity) {
        super(world, tileEntity.getPos(), tileEntity.getCachedState());

        this.tileEntity = tileEntity;

        // copy tile entity data:
        this.snapshot = this.createSnapshot(tileEntity);
        this.load(snapshot);
    }

    public void refreshSnapshot() {
        this.load(tileEntity);
    }

    private T createSnapshot(T tileEntity) {
        if (tileEntity == null) {
            return null;
        }

        NbtCompound nbtTagCompound = tileEntity.createNbtWithId();
        T snapshot = (T) BlockEntity.loadStatic(getPosition(), getHandle(), nbtTagCompound);

        return snapshot;
    }

    // copies the TileEntity-specific data, retains the position
    private void copyData(T from, T to) {
        NbtCompound nbtTagCompound = from.createNbtWithId();
        to.load(nbtTagCompound);
    }

    // gets the wrapped TileEntity
    protected T getTileEntity() {
        return tileEntity;
    }

    // gets the cloned TileEntity which is used to store the captured data
    protected T getSnapshot() {
        return snapshot;
    }

    // gets the current TileEntity from the world at this position
    protected BlockEntity getTileEntityFromWorld() {
        requirePlaced();

        return getWorldHandle().getBlockEntity(this.getPosition());
    }

    // gets the NBT data of the TileEntity represented by this block state
    public NbtCompound getSnapshotNBT() {
        // update snapshot
        applyTo(snapshot);

        return snapshot.createNbtWithId();
    }

    // copies the data of the given tile entity to this block state
    protected void load(T tileEntity) {
        if (tileEntity != null && tileEntity != snapshot) {
            copyData(tileEntity, snapshot);
        }
    }

    // applies the TileEntity data of this block state to the given TileEntity
    protected void applyTo(T tileEntity) {
        if (tileEntity != null && tileEntity != snapshot) {
            copyData(snapshot, tileEntity);
        }
    }

    protected boolean isApplicable(BlockEntity tileEntity) {
        return tileEntity != null && this.tileEntity.getClass() == tileEntity.getClass();
    }

    @Override
    public boolean update(boolean force, boolean applyPhysics) {
        boolean result = super.update(force, applyPhysics);

        if (result && this.isPlaced()) {
            BlockEntity tile = getTileEntityFromWorld();

            if (isApplicable(tile)) {
                applyTo((T) tile);
                tile.markDirty();
            }
        }

        return result;
    }

    @Override
    public PersistentDataContainer getPersistentDataContainer() {
        return this.getSnapshot().persistentDataContainer;
    }

    @Nullable
    public Packet<ClientPlayPacketListener> getUpdatePacket(@NotNull Location location) {
        T vanillaTileEntitiy = (T) BlockEntity.loadStatic(CraftLocation.toBlockPosition(location), getHandle(), getSnapshotNBT());
        return BlockEntityUpdateS2CPacket.create(vanillaTileEntitiy);
    }
}
