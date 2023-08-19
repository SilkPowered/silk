package org.bukkit.craftbukkit.entity;

import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.entity.mob.SpellcastingIllagerEntity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Evoker;

public class CraftEvoker extends CraftSpellcaster implements Evoker {

    public CraftEvoker(CraftServer server, EvokerEntity entity) {
        super(server, entity);
    }

    @Override
    public EvokerEntity getHandle() {
        return (EvokerEntity) super.getHandle();
    }

    @Override
    public String toString() {
        return "CraftEvoker";
    }

    @Override
    public Evoker.Spell getCurrentSpell() {
        return Evoker.Spell.values()[getHandle().getCurrentSpell().ordinal()];
    }

    @Override
    public void setCurrentSpell(Evoker.Spell spell) {
        getHandle().setIsCastingSpell(spell == null ? SpellcastingIllagerEntity.Spell.NONE : SpellcastingIllagerEntity.Spell.byId(spell.ordinal()));
    }
}
