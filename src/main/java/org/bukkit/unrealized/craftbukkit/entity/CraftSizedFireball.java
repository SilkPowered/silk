package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.projectile.EntityFireballFireball;
import org.bukkit.unrealized.Material;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.unrealized.entity.SizedFireball;
import org.bukkit.unrealized.inventory.ItemStack;

public class CraftSizedFireball extends CraftFireball implements SizedFireball {

    public CraftSizedFireball(CraftServer server, EntityFireballFireball entity) {
        super(server, entity);
    }

    @Override
    public ItemStack getDisplayItem() {
        if (getHandle().getItemRaw().isEmpty()) {
            return new ItemStack(Material.FIRE_CHARGE);
        } else {
            return CraftItemStack.asBukkitCopy(getHandle().getItemRaw());
        }
    }

    @Override
    public void setDisplayItem(ItemStack item) {
        getHandle().setItem(CraftItemStack.asNMSCopy(item));
    }

    @Override
    public EntityFireballFireball getHandle() {
        return (EntityFireballFireball) entity;
    }
}
