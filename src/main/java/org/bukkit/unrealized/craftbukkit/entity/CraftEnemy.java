package org.bukkit.unrealized.craftbukkit.entity;

import net.minecraft.world.entity.monster.IMonster;
import org.bukkit.unrealized.entity.Enemy;

public interface CraftEnemy extends Enemy {

    IMonster getHandle();
}
