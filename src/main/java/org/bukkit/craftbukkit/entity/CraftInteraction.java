package org.bukkit.craftbukkit.entity;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Interaction;

public class CraftInteraction extends CraftEntity implements Interaction {

    public CraftInteraction(CraftServer server, net.minecraft.entity.decoration.InteractionEntity entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.entity.decoration.InteractionEntity getHandle() {
        return (net.minecraft.entity.decoration.InteractionEntity) super.getHandle();
    }

    @Override
    public String toString() {
        return "CraftInteraction";
    }

    @Override
    public float getInteractionWidth() {
        return getHandle().getInteractionWidth();
    }

    @Override
    public void setInteractionWidth(float width) {
        getHandle().setInteractionWidth(width);
    }

    @Override
    public float getInteractionHeight() {
        return getHandle().getInteractionHeight();
    }

    @Override
    public void setInteractionHeight(float height) {
        getHandle().setInteractionHeight(height);
    }

    @Override
    public boolean isResponsive() {
        return getHandle().shouldRespond();
    }

    @Override
    public void setResponsive(boolean response) {
        getHandle().setResponse(response);
    }

    @Override
    public PreviousInteraction getLastAttack() {
        net.minecraft.entity.decoration.InteractionEntity.Interaction last = getHandle().attack;

        return (last != null) ? new CraftPreviousInteraction(last.comp_1284(), last.comp_1285()) : null;
    }

    @Override
    public PreviousInteraction getLastInteraction() {
        net.minecraft.entity.decoration.InteractionEntity.Interaction last = getHandle().interaction;

        return (last != null) ? new CraftPreviousInteraction(last.comp_1284(), last.comp_1285()) : null;
    }

    private static class CraftPreviousInteraction implements PreviousInteraction {

        private final UUID uuid;
        private final long timestamp;

        public CraftPreviousInteraction(UUID uuid, long timestamp) {
            this.uuid = uuid;
            this.timestamp = timestamp;
        }

        @Override
        public OfflinePlayer getPlayer() {
            return Bukkit.getOfflinePlayer(uuid);
        }

        @Override
        public long getTimestamp() {
            return timestamp;
        }
    }
}
