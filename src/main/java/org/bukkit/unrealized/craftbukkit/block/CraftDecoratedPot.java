package org.bukkit.unrealized.craftbukkit.block;

import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import org.bukkit.unrealized.Material;
import org.bukkit.World;
import org.bukkit.unrealized.block.DecoratedPot;
import org.bukkit.unrealized.craftbukkit.util.CraftMagicNumbers;

public class CraftDecoratedPot extends CraftBlockEntityState<DecoratedPotBlockEntity> implements DecoratedPot {

    public CraftDecoratedPot(World world, DecoratedPotBlockEntity tileEntity) {
        super(world, tileEntity);
    }

    @Override
    public List<Material> getShards() {
        return getSnapshot().getDecorations().sorted().map(CraftMagicNumbers::getMaterial).collect(Collectors.toUnmodifiableList());
    }
}
