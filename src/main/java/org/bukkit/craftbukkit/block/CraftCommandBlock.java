package org.bukkit.craftbukkit.block;

import net.minecraft.block.entity.CommandBlockBlockEntity;
import org.bukkit.World;
import org.bukkit.block.CommandBlock;
import org.bukkit.craftbukkit.util.CraftChatMessage;

public class CraftCommandBlock extends CraftBlockEntityState<CommandBlockBlockEntity> implements CommandBlock {

    public CraftCommandBlock(World world, CommandBlockBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    @Override
    public String getCommand() {
        return getSnapshot().getCommandExecutor().getCommand();
    }

    @Override
    public void setCommand(String command) {
        getSnapshot().getCommandExecutor().setCommand(command != null ? command : "");
    }

    @Override
    public String getName() {
        return CraftChatMessage.fromComponent(getSnapshot().getCommandExecutor().getCustomName());
    }

    @Override
    public void setName(String name) {
        getSnapshot().getCommandExecutor().setCustomName(CraftChatMessage.fromStringOrNull(name != null ? name : "@"));
    }
}
