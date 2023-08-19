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
        return getSnapshot().getCommandBlock().getCommand();
    }

    @Override
    public void setCommand(String command) {
        getSnapshot().getCommandBlock().setCommand(command != null ? command : "");
    }

    @Override
    public String getName() {
        return CraftChatMessage.fromComponent(getSnapshot().getCommandBlock().getName());
    }

    @Override
    public void setName(String name) {
        getSnapshot().getCommandBlock().setName(CraftChatMessage.fromStringOrNull(name != null ? name : "@"));
    }
}
