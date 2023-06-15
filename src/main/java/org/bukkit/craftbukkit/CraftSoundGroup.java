package org.bukkit.craftbukkit;

import net.minecraft.sound.BlockSoundGroup;
import org.bukkit.Sound;
import org.bukkit.SoundGroup;

import java.util.HashMap;

public class CraftSoundGroup implements SoundGroup {

    private final BlockSoundGroup handle;
    private static final HashMap<BlockSoundGroup, CraftSoundGroup> SOUND_GROUPS = new HashMap<>();

    public static SoundGroup getSoundGroup(BlockSoundGroup soundEffectType) {
        return SOUND_GROUPS.computeIfAbsent(soundEffectType, CraftSoundGroup::new);
    }

    private CraftSoundGroup(BlockSoundGroup soundEffectType) {
        this.handle = soundEffectType;
    }

    public BlockSoundGroup getHandle() {
        return handle;
    }

    @Override
    public float getVolume() {
        return getHandle().getVolume();
    }

    @Override
    public float getPitch() {
        return getHandle().getPitch();
    }

    @Override
    public Sound getBreakSound() {
        return CraftSound.getBukkit(getHandle().getBreakSound());
    }

    @Override
    public Sound getStepSound() {
        return CraftSound.getBukkit(getHandle().getStepSound());
    }

    @Override
    public Sound getPlaceSound() {
        return CraftSound.getBukkit(getHandle().getPlaceSound());
    }

    @Override
    public Sound getHitSound() {
        return CraftSound.getBukkit(getHandle().getHitSound());
    }

    @Override
    public Sound getFallSound() {
        return CraftSound.getBukkit(getHandle().getFallSound());
    }
}
