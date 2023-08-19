package org.bukkit.craftbukkit.entity;

import com.google.common.base.Preconditions;
import java.util.stream.Collectors;
import net.minecraft.entity.vehicle.BoatEntity;
import org.bukkit.TreeSpecies;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;

public class CraftBoat extends CraftVehicle implements Boat {

    public CraftBoat(CraftServer server, BoatEntity entity) {
        super(server, entity);
    }

    @Override
    public TreeSpecies getWoodType() {
        return getTreeSpecies(getHandle().getVariant());
    }

    @Override
    public void setWoodType(TreeSpecies species) {
        getHandle().setVariant(getBoatType(species));
    }

    @Override
    public Type getBoatType() {
        return boatTypeFromNms(getHandle().getVariant());
    }

    @Override
    public void setBoatType(Type type) {
        Preconditions.checkArgument(type != null, "Boat.Type cannot be null");

        getHandle().setVariant(boatTypeToNms(type));
    }

    @Override
    public double getMaxSpeed() {
        return getHandle().maxSpeed;
    }

    @Override
    public void setMaxSpeed(double speed) {
        if (speed >= 0D) {
            getHandle().maxSpeed = speed;
        }
    }

    @Override
    public double getOccupiedDeceleration() {
        return getHandle().occupiedDeceleration;
    }

    @Override
    public void setOccupiedDeceleration(double speed) {
        if (speed >= 0D) {
            getHandle().occupiedDeceleration = speed;
        }
    }

    @Override
    public double getUnoccupiedDeceleration() {
        return getHandle().unoccupiedDeceleration;
    }

    @Override
    public void setUnoccupiedDeceleration(double speed) {
        getHandle().unoccupiedDeceleration = speed;
    }

    @Override
    public boolean getWorkOnLand() {
        return getHandle().landBoats;
    }

    @Override
    public void setWorkOnLand(boolean workOnLand) {
        getHandle().landBoats = workOnLand;
    }

    @Override
    public Status getStatus() {
        return boatStatusFromNms(getHandle().status);
    }

    @Override
    public BoatEntity getHandle() {
        return (BoatEntity) entity;
    }

    @Override
    public String toString() {
        return "CraftBoat{boatType=" + getBoatType() + ",status=" + getStatus() + ",passengers=" + getPassengers().stream().map(Entity::toString).collect(Collectors.joining("-", "{", "}")) + "}";
    }

    public static Boat.Type boatTypeFromNms(BoatEntity.Type boatType) {
        return switch (boatType) {
            default -> throw new EnumConstantNotPresentException(Type.class, boatType.name());
            case OAK -> Type.OAK;
            case BIRCH -> Type.BIRCH;
            case ACACIA -> Type.ACACIA;
            case CHERRY -> Type.CHERRY;
            case JUNGLE -> Type.JUNGLE;
            case SPRUCE -> Type.SPRUCE;
            case DARK_OAK -> Type.DARK_OAK;
            case MANGROVE -> Type.MANGROVE;
            case BAMBOO -> Type.BAMBOO;
        };
    }

    public static BoatEntity.Type boatTypeToNms(Boat.Type type) {
        return switch (type) {
            default -> throw new EnumConstantNotPresentException(BoatEntity.Type.class, type.name());
            case BAMBOO -> BoatEntity.Type.BAMBOO;
            case MANGROVE -> BoatEntity.Type.MANGROVE;
            case SPRUCE -> BoatEntity.Type.SPRUCE;
            case DARK_OAK -> BoatEntity.Type.DARK_OAK;
            case JUNGLE -> BoatEntity.Type.JUNGLE;
            case CHERRY -> BoatEntity.Type.CHERRY;
            case ACACIA -> BoatEntity.Type.ACACIA;
            case BIRCH -> BoatEntity.Type.BIRCH;
            case OAK -> BoatEntity.Type.OAK;
        };
    }

    public static Status boatStatusFromNms(BoatEntity.Location enumStatus) {
        return switch (enumStatus) {
            default -> throw new EnumConstantNotPresentException(Status.class, enumStatus.name());
            case IN_AIR -> Status.IN_AIR;
            case ON_LAND -> Status.ON_LAND;
            case UNDER_WATER -> Status.UNDER_WATER;
            case UNDER_FLOWING_WATER -> Status.UNDER_FLOWING_WATER;
            case IN_WATER -> Status.IN_WATER;
        };
    }

    @Deprecated
    public static TreeSpecies getTreeSpecies(BoatEntity.Type boatType) {
        switch (boatType) {
            case SPRUCE:
                return TreeSpecies.REDWOOD;
            case BIRCH:
                return TreeSpecies.BIRCH;
            case JUNGLE:
                return TreeSpecies.JUNGLE;
            case ACACIA:
                return TreeSpecies.ACACIA;
            case DARK_OAK:
                return TreeSpecies.DARK_OAK;
            case OAK:
            default:
                return TreeSpecies.GENERIC;
        }
    }

    @Deprecated
    public static BoatEntity.Type getBoatType(TreeSpecies species) {
        switch (species) {
            case REDWOOD:
                return BoatEntity.Type.SPRUCE;
            case BIRCH:
                return BoatEntity.Type.BIRCH;
            case JUNGLE:
                return BoatEntity.Type.JUNGLE;
            case ACACIA:
                return BoatEntity.Type.ACACIA;
            case DARK_OAK:
                return BoatEntity.Type.DARK_OAK;
            case GENERIC:
            default:
                return BoatEntity.Type.OAK;
        }
    }
}
