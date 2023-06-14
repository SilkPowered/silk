package org.bukkit.unrealized.craftbukkit.block.data.type;

import org.bukkit.unrealized.block.data.type.Bell;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public abstract class CraftBell extends CraftBlockData implements Bell {

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> ATTACHMENT = getEnum("attachment");

    @Override
    public Attachment getAttachment() {
        return get(ATTACHMENT, Attachment.class);
    }

    @Override
    public void setAttachment(Attachment leaves) {
        set(ATTACHMENT, leaves);
    }
}
