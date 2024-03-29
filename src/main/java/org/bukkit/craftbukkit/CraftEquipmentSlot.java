package org.bukkit.craftbukkit;

import net.minecraft.util.Hand;
import org.bukkit.inventory.EquipmentSlot;

public class CraftEquipmentSlot {

    private static final net.minecraft.entity.EquipmentSlot[] slots = new net.minecraft.entity.EquipmentSlot[EquipmentSlot.values().length];
    private static final EquipmentSlot[] enums = new EquipmentSlot[net.minecraft.entity.EquipmentSlot.values().length];

    static {
        set(EquipmentSlot.HAND, net.minecraft.entity.EquipmentSlot.MAINHAND);
        set(EquipmentSlot.OFF_HAND, net.minecraft.entity.EquipmentSlot.OFFHAND);
        set(EquipmentSlot.FEET, net.minecraft.entity.EquipmentSlot.FEET);
        set(EquipmentSlot.LEGS, net.minecraft.entity.EquipmentSlot.LEGS);
        set(EquipmentSlot.CHEST, net.minecraft.entity.EquipmentSlot.CHEST);
        set(EquipmentSlot.HEAD, net.minecraft.entity.EquipmentSlot.HEAD);
    }

    private static void set(EquipmentSlot type, net.minecraft.entity.EquipmentSlot value) {
        slots[type.ordinal()] = value;
        enums[value.ordinal()] = type;
    }

    public static EquipmentSlot getSlot(net.minecraft.entity.EquipmentSlot nms) {
        return enums[nms.ordinal()];
    }

    public static net.minecraft.entity.EquipmentSlot getNMS(EquipmentSlot slot) {
        return slots[slot.ordinal()];
    }

    public static EquipmentSlot getHand(Hand enumhand) {
        return (enumhand == Hand.MAIN_HAND) ? EquipmentSlot.HAND : EquipmentSlot.OFF_HAND;
    }

    public static Hand getHand(EquipmentSlot hand) {
        if (hand == EquipmentSlot.HAND) {
            return Hand.MAIN_HAND;
        } else if (hand == EquipmentSlot.OFF_HAND) {
            return Hand.OFF_HAND;
        }

        throw new IllegalArgumentException("EquipmentSlot." + hand + " is not a hand");
    }
}
