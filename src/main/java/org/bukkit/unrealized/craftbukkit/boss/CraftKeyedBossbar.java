package org.bukkit.unrealized.craftbukkit.boss;

import net.minecraft.server.bossevents.BossBattleCustom;
import org.bukkit.unrealized.NamespacedKey;
import org.bukkit.unrealized.boss.KeyedBossBar;
import org.bukkit.unrealized.craftbukkit.util.CraftNamespacedKey;

public class CraftKeyedBossbar extends CraftBossBar implements KeyedBossBar {

    public CraftKeyedBossbar(BossBattleCustom bossBattleCustom) {
        super(bossBattleCustom);
    }

    @Override
    public NamespacedKey getKey() {
        return CraftNamespacedKey.fromMinecraft(getHandle().getTextId());
    }

    @Override
    public BossBattleCustom getHandle() {
        return (BossBattleCustom) super.getHandle();
    }
}
