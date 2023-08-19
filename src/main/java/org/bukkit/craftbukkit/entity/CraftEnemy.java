package org.bukkit.craftbukkit.entity;

import net.minecraft.entity.mob.Monster;
import org.bukkit.entity.Enemy;

public interface CraftEnemy extends Enemy {

    Monster getHandle();
}
