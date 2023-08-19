package org.bukkit.craftbukkit.projectiles;

import com.google.common.base.Preconditions;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.entity.projectile.SpectralArrowEntity;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.entity.projectile.thrown.ExperienceBottleEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPointerImpl;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.random.Random;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.craftbukkit.potion.CraftPotionUtil;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LingeringPotion;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.TippedArrow;
import org.bukkit.entity.WitherSkull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.projectiles.BlockProjectileSource;
import org.bukkit.util.Vector;

public class CraftBlockProjectileSource implements BlockProjectileSource {
    private final DispenserBlockEntity dispenserBlock;

    public CraftBlockProjectileSource(DispenserBlockEntity dispenserBlock) {
        this.dispenserBlock = dispenserBlock;
    }

    @Override
    public Block getBlock() {
        return dispenserBlock.getWorld().getWorld().getBlockAt(dispenserBlock.getPos().getX(), dispenserBlock.getPos().getY(), dispenserBlock.getPos().getZ());
    }

    @Override
    public <T extends Projectile> T launchProjectile(Class<? extends T> projectile) {
        return launchProjectile(projectile, null);
    }

    @Override
    public <T extends Projectile> T launchProjectile(Class<? extends T> projectile, Vector velocity) {
        Preconditions.checkArgument(getBlock().getType() == Material.DISPENSER, "Block is no longer dispenser");
        // Copied from BlockDispenser.dispense()
        BlockPointerImpl isourceblock = new BlockPointerImpl((ServerWorld) dispenserBlock.getWorld(), dispenserBlock.getPos());
        // Copied from DispenseBehaviorProjectile
        Position iposition = DispenserBlock.getOutputLocation(isourceblock);
        Direction enumdirection = (Direction) isourceblock.e().get(DispenserBlock.FACING);
        net.minecraft.world.World world = dispenserBlock.getWorld();
        net.minecraft.entity.Entity launch = null;

        if (Snowball.class.isAssignableFrom(projectile)) {
            launch = new SnowballEntity(world, iposition.getX(), iposition.getY(), iposition.getZ());
        } else if (Egg.class.isAssignableFrom(projectile)) {
            launch = new EggEntity(world, iposition.getX(), iposition.getY(), iposition.getZ());
        } else if (EnderPearl.class.isAssignableFrom(projectile)) {
            launch = new EnderPearlEntity(world, null);
            launch.setPosition(iposition.getX(), iposition.getY(), iposition.getZ());
        } else if (ThrownExpBottle.class.isAssignableFrom(projectile)) {
            launch = new ExperienceBottleEntity(world, iposition.getX(), iposition.getY(), iposition.getZ());
        } else if (ThrownPotion.class.isAssignableFrom(projectile)) {
            if (LingeringPotion.class.isAssignableFrom(projectile)) {
                launch = new PotionEntity(world, iposition.getX(), iposition.getY(), iposition.getZ());
                ((PotionEntity) launch).setItem(CraftItemStack.asNMSCopy(new ItemStack(org.bukkit.Material.LINGERING_POTION, 1)));
            } else {
                launch = new PotionEntity(world, iposition.getX(), iposition.getY(), iposition.getZ());
                ((PotionEntity) launch).setItem(CraftItemStack.asNMSCopy(new ItemStack(org.bukkit.Material.SPLASH_POTION, 1)));
            }
        } else if (AbstractArrow.class.isAssignableFrom(projectile)) {
            if (TippedArrow.class.isAssignableFrom(projectile)) {
                launch = new ArrowEntity(world, iposition.getX(), iposition.getY(), iposition.getZ());
                ((Arrow) launch.getBukkitEntity()).setBasePotionData(new PotionData(PotionType.WATER, false, false));
            } else if (SpectralArrow.class.isAssignableFrom(projectile)) {
                launch = new SpectralArrowEntity(world, iposition.getX(), iposition.getY(), iposition.getZ());
            } else {
                launch = new ArrowEntity(world, iposition.getX(), iposition.getY(), iposition.getZ());
            }
            ((PersistentProjectileEntity) launch).pickup = PersistentProjectileEntity.PickupPermission.ALLOWED;
            ((PersistentProjectileEntity) launch).projectileSource = this;
        } else if (Fireball.class.isAssignableFrom(projectile)) {
            double d0 = iposition.getX() + (double) ((float) enumdirection.getOffsetX() * 0.3F);
            double d1 = iposition.getY() + (double) ((float) enumdirection.getOffsetY() * 0.3F);
            double d2 = iposition.getZ() + (double) ((float) enumdirection.getOffsetZ() * 0.3F);
            Random random = world.random;
            double d3 = random.nextGaussian() * 0.05D + (double) enumdirection.getOffsetX();
            double d4 = random.nextGaussian() * 0.05D + (double) enumdirection.getOffsetY();
            double d5 = random.nextGaussian() * 0.05D + (double) enumdirection.getOffsetZ();

            if (SmallFireball.class.isAssignableFrom(projectile)) {
                launch = new SmallFireballEntity(world, null, d0, d1, d2);
            } else if (WitherSkull.class.isAssignableFrom(projectile)) {
                launch = EntityType.WITHER_SKULL.create(world);
                launch.setPosition(d0, d1, d2);
                double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5);

                ((ExplosiveProjectileEntity) launch).xPower = d3 / d6 * 0.1D;
                ((ExplosiveProjectileEntity) launch).yPower = d4 / d6 * 0.1D;
                ((ExplosiveProjectileEntity) launch).zPower = d5 / d6 * 0.1D;
            } else {
                launch = EntityType.FIREBALL.create(world);
                launch.setPosition(d0, d1, d2);
                double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5);

                ((ExplosiveProjectileEntity) launch).xPower = d3 / d6 * 0.1D;
                ((ExplosiveProjectileEntity) launch).yPower = d4 / d6 * 0.1D;
                ((ExplosiveProjectileEntity) launch).zPower = d5 / d6 * 0.1D;
            }

            ((ExplosiveProjectileEntity) launch).projectileSource = this;
        }

        Preconditions.checkArgument(launch != null, "Projectile not supported");

        if (launch instanceof ProjectileEntity) {
            if (launch instanceof ThrownEntity) {
                ((ThrownEntity) launch).projectileSource = this;
            }
            // Values from DispenseBehaviorProjectile
            float a = 6.0F;
            float b = 1.1F;
            if (launch instanceof PotionEntity || launch instanceof ThrownExpBottle) {
                // Values from respective DispenseBehavior classes
                a *= 0.5F;
                b *= 1.25F;
            }
            // Copied from DispenseBehaviorProjectile
            ((ProjectileEntity) launch).setVelocity((double) enumdirection.getOffsetX(), (double) ((float) enumdirection.getOffsetY() + 0.1F), (double) enumdirection.getOffsetZ(), b, a);
        }

        if (velocity != null) {
            ((T) launch.getBukkitEntity()).setVelocity(velocity);
        }

        world.spawnEntity(launch);
        return (T) launch.getBukkitEntity();
    }
}
