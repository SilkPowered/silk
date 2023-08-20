package cx.rain.silk.mixins_legacy.mixin.item;

import com.mojang.serialization.Dynamic;
import cx.rain.silk.mixins_legacy.interfaces.item.IItemStackMixin;
import cx.rain.silk.mixins_legacy.interfaces.server.IMinecraftServerMixin;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements IItemStackMixin {
    @Mutable
    @Shadow @Final @Deprecated private @Nullable Item item;

    @Shadow public abstract NbtCompound writeNbt(NbtCompound nbt);

    @Shadow private int count;

    @Shadow private @Nullable NbtCompound nbt;

    @Shadow public abstract void setDamage(int damage);

    @Shadow public abstract int getDamage();

    @Shadow public abstract Item getItem();

    @Override
    public void setItem(@Nullable Item item) {
        this.item = item;
    }

    @Override
    public void convertStack(int version) {
        if (0 < version && version < CraftMagicNumbers.INSTANCE.getDataVersion()) {
            NbtCompound savedStack = new NbtCompound();
            this.writeNbt(savedStack);
            savedStack = (NbtCompound) IMinecraftServerMixin.getServer().getDataFixer().update(TypeReferences.ITEM_STACK, new Dynamic(NbtOps.INSTANCE, savedStack), version, CraftMagicNumbers.INSTANCE.getDataVersion()).getValue();
            this.read(savedStack);
        }
    }

    private void read(NbtCompound nbttagcompound) {
        this.item = Registries.ITEM.get(new Identifier(nbttagcompound.getString("id")));
        this.count = nbttagcompound.getByte("Count");
        if (nbttagcompound.contains("tag", 10)) {
            // CraftBukkit start - make defensive copy as this data may be coming from the save thread
            this.nbt = nbttagcompound.getCompound("tag").copy();
            // CraftBukkit end
            this.getItem().postProcessNbt(this.nbt);
        }

        if (this.getItem().isDamageable()) {
            this.setDamage(this.getDamage());
        }

    }
}
