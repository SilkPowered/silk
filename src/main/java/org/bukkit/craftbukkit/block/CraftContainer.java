package org.bukkit.craftbukkit.block;

import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.inventory.ContainerLock;
import org.bukkit.World;
import org.bukkit.block.Container;
import org.bukkit.craftbukkit.util.CraftChatMessage;

public abstract class CraftContainer<T extends LockableContainerBlockEntity> extends CraftBlockEntityState<T> implements Container {

    public CraftContainer(World world, T tileEntity) {
        super(world, tileEntity);
    }

    @Override
    public boolean isLocked() {
        return !this.getSnapshot().lockKey.key.isEmpty();
    }

    @Override
    public String getLock() {
        return this.getSnapshot().lockKey.key;
    }

    @Override
    public void setLock(String key) {
        this.getSnapshot().lockKey = (key == null) ? ContainerLock.NO_LOCK : new ContainerLock(key);
    }

    @Override
    public String getCustomName() {
        T container = this.getSnapshot();
        return container.name != null ? CraftChatMessage.fromComponent(container.ab()) : null;
    }

    @Override
    public void setCustomName(String name) {
        this.getSnapshot().setCustomName(CraftChatMessage.fromStringOrNull(name));
    }

    @Override
    public void applyTo(T container) {
        super.applyTo(container);

        if (this.getSnapshot().name == null) {
            container.setCustomName(null);
        }
    }
}
