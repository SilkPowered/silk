package org.bukkit.craftbukkit.entity;

import com.google.common.base.Preconditions;
import java.util.Collection;
import java.util.stream.Collectors;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.util.math.BlockPos;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.util.CraftLocation;
import org.bukkit.entity.Sniffer;

public class CraftSniffer extends CraftAnimals implements Sniffer {

    public CraftSniffer(CraftServer server, net.minecraft.entity.passive.SnifferEntity entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.entity.passive.SnifferEntity getHandle() {
        return (net.minecraft.entity.passive.SnifferEntity) super.getHandle();
    }

    @Override
    public String toString() {
        return "CraftSniffer";
    }

    @Override
    public Collection<Location> getExploredLocations() {
        return this.getHandle().getExploredPositions().map(blockPosition -> CraftLocation.toBukkit(blockPosition.getPos(), this.server.getServer().getWorld(blockPosition.getDimension()))).collect(Collectors.toList());
    }

    @Override
    public void removeExploredLocation(Location location) {
        Preconditions.checkArgument(location != null, "location cannot be null");
        if (location.getWorld() != getWorld()) {
            return;
        }

        BlockPos blockPosition = CraftLocation.toBlockPosition(location);
        this.getHandle().dK().remember(MemoryModuleType.SNIFFER_EXPLORED_POSITIONS, this.getHandle().getExploredPositions().filter(blockPositionExplored -> !blockPositionExplored.equals(blockPosition)).collect(Collectors.toList()));
    }

    @Override
    public void addExploredLocation(Location location) {
        Preconditions.checkArgument(location != null, "location cannot be null");
        if (location.getWorld() != getWorld()) {
            return;
        }

        this.getHandle().addExploredPosition(CraftLocation.toBlockPosition(location));
    }

    @Override
    public Sniffer.State getState() {
        return this.stateToBukkit(this.getHandle().getState());
    }

    @Override
    public void setState(Sniffer.State state) {
        Preconditions.checkArgument(state != null, "state cannot be null");
        this.getHandle().startState(this.stateToNMS(state));
    }

    @Override
    public Location findPossibleDigLocation() {
        return this.getHandle().findSniffingTargetPos().map(blockPosition -> CraftLocation.toBukkit(blockPosition, this.getLocation().getWorld())).orElse(null);
    }

    @Override
    public boolean canDig() {
        return this.getHandle().canDig();
    }

    private net.minecraft.entity.passive.SnifferEntity.State stateToNMS(Sniffer.State state) {
        return switch (state) {
            case IDLING -> net.minecraft.entity.passive.SnifferEntity.State.IDLING;
            case FEELING_HAPPY -> net.minecraft.entity.passive.SnifferEntity.State.FEELING_HAPPY;
            case SCENTING -> net.minecraft.entity.passive.SnifferEntity.State.SCENTING;
            case SNIFFING -> net.minecraft.entity.passive.SnifferEntity.State.SNIFFING;
            case SEARCHING -> net.minecraft.entity.passive.SnifferEntity.State.SEARCHING;
            case DIGGING -> net.minecraft.entity.passive.SnifferEntity.State.DIGGING;
            case RISING -> net.minecraft.entity.passive.SnifferEntity.State.RISING;
        };
    }

    private Sniffer.State stateToBukkit(net.minecraft.entity.passive.SnifferEntity.State state) {
        return switch (state) {
            case IDLING -> Sniffer.State.IDLING;
            case FEELING_HAPPY -> Sniffer.State.FEELING_HAPPY;
            case SCENTING -> Sniffer.State.SCENTING;
            case SNIFFING -> Sniffer.State.SNIFFING;
            case SEARCHING -> Sniffer.State.SEARCHING;
            case DIGGING -> Sniffer.State.DIGGING;
            case RISING -> Sniffer.State.RISING;
        };
    }
}
