package org.bukkit.craftbukkit.entity;

import com.google.common.base.Preconditions;
import net.minecraft.entity.passive.PandaEntity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Panda;
import org.bukkit.entity.Panda.Gene;

public class CraftPanda extends CraftAnimals implements Panda {

    public CraftPanda(CraftServer server, PandaEntity entity) {
        super(server, entity);
    }

    @Override
    public PandaEntity getHandle() {
        return (PandaEntity) super.getHandle();
    }

    @Override
    public String toString() {
        return "CraftPanda";
    }

    @Override
    public Gene getMainGene() {
        return fromNms(getHandle().getMainGene());
    }

    @Override
    public void setMainGene(Gene gene) {
        getHandle().setMainGene(toNms(gene));
    }

    @Override
    public Gene getHiddenGene() {
        return fromNms(getHandle().getHiddenGene());
    }

    @Override
    public void setHiddenGene(Gene gene) {
        getHandle().setHiddenGene(toNms(gene));
    }

    @Override
    public boolean isRolling() {
        return getHandle().isPlaying();
    }

    @Override
    public void setRolling(boolean flag) {
        getHandle().setPlaying(flag);
    }

    @Override
    public boolean isSneezing() {
        return getHandle().isSneezing();
    }

    @Override
    public void setSneezing(boolean flag) {
        getHandle().setSneezing(flag);
    }

    @Override
    public boolean isSitting() {
        return getHandle().isSitting();
    }

    @Override
    public void setSitting(boolean flag) {
        getHandle().setSitting(flag);
    }

    @Override
    public boolean isOnBack() {
        return getHandle().isLyingOnBack();
    }

    @Override
    public void setOnBack(boolean flag) {
        getHandle().setLyingOnBack(flag);
    }

    @Override
    public boolean isEating() {
        return getHandle().isEating();
    }

    @Override
    public void setEating(boolean flag) {
        getHandle().setEating(flag);
    }

    @Override
    public boolean isScared() {
        return getHandle().isScaredByThunderstorm();
    }

    @Override
    public int getUnhappyTicks() {
        return getHandle().getAskForBambooTicks();
    }

    public static Gene fromNms(PandaEntity.Gene gene) {
        Preconditions.checkArgument(gene != null, "Gene may not be null");

        return Gene.values()[gene.ordinal()];
    }

    public static PandaEntity.Gene toNms(Gene gene) {
        Preconditions.checkArgument(gene != null, "Gene may not be null");

        return PandaEntity.Gene.values()[gene.ordinal()];
    }
}
