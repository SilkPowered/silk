package org.bukkit.craftbukkit.block;

import com.google.common.base.Preconditions;
import cx.rain.silk.mixins.bridge.world.IWorldBridge;
import cx.rain.silk.mixins.interfaces.world.IWorldAccessMixin;
import cx.rain.silk.mixins.interfaces.world.IWorldMixin;
import cx.rain.silk.mixins.statics.block.ISaplingBlockStatics;
import cx.rain.silk.mixins.statics.item.BoneMealItemStatics;
import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.LightType;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.WorldAccess;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.CraftFluidCollisionMode;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.craftbukkit.util.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockFertilizeEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BlockVector;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CraftBlock implements Block {
    private final WorldAccess world;
    private final BlockPos position;

    public CraftBlock(WorldAccess world, BlockPos position) {
        this.world = world;
        this.position = position.toImmutable();
    }

    public static CraftBlock at(WorldAccess world, BlockPos position) {
        return new CraftBlock(world, position);
    }

    public net.minecraft.block.BlockState getNMS() {
        return world.getBlockState(position);
    }

    public BlockPos getPosition() {
        return position;
    }

    public WorldAccess getHandle() {
        return world;
    }

    @Override
    public World getWorld() {
        return ((IWorldMixin) ((IWorldAccessMixin) world).getMinecraftWorld()).getWorld();
    }

    public CraftWorld getCraftWorld() {
        return (CraftWorld) getWorld();
    }

    @Override
    public Location getLocation() {
        return CraftLocation.toBukkit(position, getWorld());
    }

    @Override
    public Location getLocation(Location loc) {
        if (loc != null) {
            loc.setWorld(getWorld());
            loc.setX(position.getX());
            loc.setY(position.getY());
            loc.setZ(position.getZ());
            loc.setYaw(0);
            loc.setPitch(0);
        }

        return loc;
    }

    public BlockVector getVector() {
        return new BlockVector(getX(), getY(), getZ());
    }

    @Override
    public int getX() {
        return position.getX();
    }

    @Override
    public int getY() {
        return position.getY();
    }

    @Override
    public int getZ() {
        return position.getZ();
    }

    @Override
    public Chunk getChunk() {
        return getWorld().getChunkAt(this);
    }

    public void setData(final byte data) {
        setData(data, 3);
    }

    public void setData(final byte data, boolean applyPhysics) {
        if (applyPhysics) {
            setData(data, 3);
        } else {
            setData(data, 2);
        }
    }

    private void setData(final byte data, int flag) {
        world.setBlockState(position, CraftMagicNumbers.getBlock(getType(), data), flag);
    }

    @Override
    public byte getData() {
        net.minecraft.block.BlockState blockData = world.getBlockState(position);
        return CraftMagicNumbers.toLegacyData(blockData);
    }

    @Override
    public BlockData getBlockData() {
        return CraftBlockData.fromData(getNMS());
    }

    @Override
    public void setType(final Material type) {
        setType(type, true);
    }

    @Override
    public void setType(Material type, boolean applyPhysics) {
        Preconditions.checkArgument(type != null, "Material cannot be null");
        setBlockData(type.createBlockData(), applyPhysics);
    }

    @Override
    public void setBlockData(BlockData data) {
        setBlockData(data, true);
    }

    @Override
    public void setBlockData(BlockData data, boolean applyPhysics) {
        Preconditions.checkArgument(data != null, "BlockData cannot be null");
        setTypeAndData(((CraftBlockData) data).getState(), applyPhysics);
    }

    boolean setTypeAndData(final net.minecraft.block.BlockState blockData, final boolean applyPhysics) {
        return setTypeAndData(world, position, getNMS(), blockData, applyPhysics);
    }

    public static boolean setTypeAndData(WorldAccess world, BlockPos position, net.minecraft.block.BlockState old,
                                         net.minecraft.block.BlockState blockData, boolean applyPhysics) {
        // SPIGOT-611: need to do this to prevent glitchiness. Easier to handle this here (like /setblock) than to fix weirdness in tile entity cleanup
        if (old.hasBlockEntity() && blockData.getBlock() != old.getBlock()) { // SPIGOT-3725 remove old tile entity if block changes
            // SPIGOT-4612: faster - just clear tile
            if (world instanceof net.minecraft.world.World) {
                ((net.minecraft.world.World) world).removeBlockEntity(position);
            } else {
                world.setBlockState(position, Blocks.AIR.getDefaultState(), 0);
            }
        }

        if (applyPhysics) {
            return world.setBlockState(position, blockData, 3);
        } else {
            boolean success = world.setBlockState(position, blockData, 2 | 16 | 1024); // NOTIFY | NO_OBSERVER | NO_PLACE (custom)
            if (success && world instanceof net.minecraft.world.World) {
                ((IWorldAccessMixin) world).getMinecraftWorld().updateListeners(position, old, blockData, 3);
            }
            return success;
        }
    }

    @Override
    public Material getType() {
        return CraftMagicNumbers.getMaterial(world.getBlockState(position).getBlock());
    }

    @Override
    public byte getLightLevel() {
        return (byte) ((IWorldAccessMixin) world).getMinecraftWorld().getLightLevel(position);
    }

    @Override
    public byte getLightFromSky() {
        return (byte) world.getLightLevel(LightType.SKY, position);
    }

    @Override
    public byte getLightFromBlocks() {
        return (byte) world.getLightLevel(LightType.BLOCK, position);
    }

    public Block getFace(final BlockFace face) {
        return getRelative(face, 1);
    }

    public Block getFace(final BlockFace face, final int distance) {
        return getRelative(face, distance);
    }

    @Override
    public Block getRelative(final int modX, final int modY, final int modZ) {
        return getWorld().getBlockAt(getX() + modX, getY() + modY, getZ() + modZ);
    }

    @Override
    public Block getRelative(BlockFace face) {
        return getRelative(face, 1);
    }

    @Override
    public Block getRelative(BlockFace face, int distance) {
        return getRelative(face.getModX() * distance, face.getModY() * distance, face.getModZ() * distance);
    }

    @Override
    public BlockFace getFace(final Block block) {
        BlockFace[] values = BlockFace.values();

        for (BlockFace face : values) {
            if ((this.getX() + face.getModX() == block.getX()) && (this.getY() + face.getModY() == block.getY()) && (this.getZ() + face.getModZ() == block.getZ())) {
                return face;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return "CraftBlock{pos=" + position + ",type=" + getType() + ",data=" + getNMS() + ",fluid=" + world.getFluidState(position) + '}';
    }

    public static BlockFace notchToBlockFace(Direction notch) {
        if (notch == null) {
            return BlockFace.SELF;
        }
        switch (notch) {
            case DOWN:
                return BlockFace.DOWN;
            case UP:
                return BlockFace.UP;
            case NORTH:
                return BlockFace.NORTH;
            case SOUTH:
                return BlockFace.SOUTH;
            case WEST:
                return BlockFace.WEST;
            case EAST:
                return BlockFace.EAST;
            default:
                return BlockFace.SELF;
        }
    }

    public static Direction blockFaceToNotch(BlockFace face) {
        if (face == null) {
            return null;
        }
        switch (face) {
            case DOWN:
                return Direction.DOWN;
            case UP:
                return Direction.UP;
            case NORTH:
                return Direction.NORTH;
            case SOUTH:
                return Direction.SOUTH;
            case WEST:
                return Direction.WEST;
            case EAST:
                return Direction.EAST;
            default:
                return null;
        }
    }

    @Override
    public BlockState getState() {
        return CraftBlockStates.getBlockState(this);
    }

    @Override
    public Biome getBiome() {
        return getWorld().getBiome(getX(), getY(), getZ());
    }

    @Override
    public void setBiome(Biome bio) {
        getWorld().setBiome(getX(), getY(), getZ(), bio);
    }

    public static Biome biomeBaseToBiome(net.minecraft.registry.Registry<net.minecraft.world.biome.Biome> registry, RegistryEntry<net.minecraft.world.biome.Biome> base) {
        return biomeBaseToBiome(registry, base.value());
    }

    public static Biome biomeBaseToBiome(net.minecraft.registry.Registry<net.minecraft.world.biome.Biome> registry, net.minecraft.world.biome.Biome base) {
        if (base == null) {
            return null;
        }

        Biome biome = Registry.BIOME.get(CraftNamespacedKey.fromMinecraft(registry.getId(base)));
        return (biome == null) ? Biome.CUSTOM : biome;
    }

    public static RegistryEntry<net.minecraft.world.biome.Biome> biomeToBiomeBase(net.minecraft.registry.Registry<net.minecraft.world.biome.Biome> registry, Biome bio) {
        if (bio == null || bio == Biome.CUSTOM) {
            return null;
        }

        return registry.entryOf(RegistryKey.of(RegistryKeys.BIOME, CraftNamespacedKey.toMinecraft(bio.getKey())));
    }

    @Override
    public double getTemperature() {
        return world.getBiome(position).value().getTemperature(position);
    }

    @Override
    public double getHumidity() {
        return getWorld().getHumidity(getX(), getY(), getZ());
    }

    @Override
    public boolean isBlockPowered() {
        return ((IWorldAccessMixin) world).getMinecraftWorld().getReceivedRedstonePower(position) > 0;
    }

    @Override
    public boolean isBlockIndirectlyPowered() {
        return ((IWorldAccessMixin) world).getMinecraftWorld().isReceivingRedstonePower(position);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CraftBlock other)) {
            return false;
        }

        return this.position.equals(other.position) && this.getWorld().equals(other.getWorld());
    }

    @Override
    public int hashCode() {
        return this.position.hashCode() ^ this.getWorld().hashCode();
    }

    @Override
    public boolean isBlockFacePowered(BlockFace face) {
        return ((IWorldAccessMixin) world).getMinecraftWorld().isEmittingRedstonePower(position, blockFaceToNotch(face));
    }

    @Override
    public boolean isBlockFaceIndirectlyPowered(BlockFace face) {
        int power = ((IWorldAccessMixin) world).getMinecraftWorld().getEmittedRedstonePower(position, blockFaceToNotch(face));

        Block relative = getRelative(face);
        if (relative.getType() == Material.REDSTONE_WIRE) {
            return Math.max(power, relative.getData()) > 0;
        }

        return power > 0;
    }

    @Override
    public int getBlockPower(BlockFace face) {
        int power = 0;
        net.minecraft.world.World world = ((IWorldAccessMixin) this.world).getMinecraftWorld();
        int x = getX();
        int y = getY();
        int z = getZ();
        if ((face == BlockFace.DOWN || face == BlockFace.SELF) && world.isEmittingRedstonePower(new BlockPos(x, y - 1, z), Direction.DOWN)) power = getPower(power, world.getBlockState(new BlockPos(x, y - 1, z)));
        if ((face == BlockFace.UP || face == BlockFace.SELF) && world.isEmittingRedstonePower(new BlockPos(x, y + 1, z), Direction.UP)) power = getPower(power, world.getBlockState(new BlockPos(x, y + 1, z)));
        if ((face == BlockFace.EAST || face == BlockFace.SELF) && world.isEmittingRedstonePower(new BlockPos(x + 1, y, z), Direction.EAST)) power = getPower(power, world.getBlockState(new BlockPos(x + 1, y, z)));
        if ((face == BlockFace.WEST || face == BlockFace.SELF) && world.isEmittingRedstonePower(new BlockPos(x - 1, y, z), Direction.WEST)) power = getPower(power, world.getBlockState(new BlockPos(x - 1, y, z)));
        if ((face == BlockFace.NORTH || face == BlockFace.SELF) && world.isEmittingRedstonePower(new BlockPos(x, y, z - 1), Direction.NORTH)) power = getPower(power, world.getBlockState(new BlockPos(x, y, z - 1)));
        if ((face == BlockFace.SOUTH || face == BlockFace.SELF) && world.isEmittingRedstonePower(new BlockPos(x, y, z + 1), Direction.SOUTH)) power = getPower(power, world.getBlockState(new BlockPos(x, y, z + 1)));
        return power > 0 ? power : (face == BlockFace.SELF ? isBlockIndirectlyPowered() : isBlockFaceIndirectlyPowered(face)) ? 15 : 0;
    }

    private static int getPower(int i, net.minecraft.block.BlockState iblockdata) {
        if (!iblockdata.isOf(Blocks.REDSTONE_WIRE)) {
            return i;
        } else {
            int j = iblockdata.get(RedstoneWireBlock.POWER);

            return Math.max(j, i);
        }
    }

    @Override
    public int getBlockPower() {
        return getBlockPower(BlockFace.SELF);
    }

    @Override
    public boolean isEmpty() {
        return getNMS().isAir();
    }

    @Override
    public boolean isLiquid() {
        return getNMS().isLiquid();
    }

    @Override
    public PistonMoveReaction getPistonMoveReaction() {
        return PistonMoveReaction.getById(getNMS().getPistonBehavior().ordinal());
    }

    @Override
    public boolean breakNaturally() {
        return breakNaturally(null);
    }

    @Override
    public boolean breakNaturally(ItemStack item) {
        // Order matters here, need to drop before setting to air so skulls can get their data
        net.minecraft.block.BlockState iblockdata = this.getNMS();
        net.minecraft.block.Block block = iblockdata.getBlock();
        net.minecraft.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        boolean result = false;

        // Modelled off EntityHuman#hasBlock
        if (block != Blocks.AIR && (item == null || !iblockdata.isToolRequired() || nmsItem.isSuitableFor(iblockdata))) {
            net.minecraft.block.Block.getDroppedStacks(iblockdata, ((IWorldAccessMixin) world).getMinecraftWorld(), position, world.getBlockEntity(position), null, nmsItem);
            result = true;
        }

        // SPIGOT-6778: Directly call setBlock instead of setTypeAndData, so that the tile entiy is not removed and custom remove logic is run.
        return world.setBlockState(position, Blocks.AIR.getDefaultState(), 3) && result;
    }

    @Override
    public boolean applyBoneMeal(BlockFace face) {
        Direction direction = blockFaceToNotch(face);
        BlockFertilizeEvent event = null;
        ServerWorld world = getCraftWorld().getHandle();
        ItemUsageContext context = new ItemUsageContext(world, null, Hand.MAIN_HAND, Items.BONE_MEAL.getDefaultStack(), new BlockHitResult(Vec3d.ZERO, direction, getPosition(), false));

        // SPIGOT-6895: Call StructureGrowEvent and BlockFertilizeEvent
        ((IWorldBridge) world).silk$setCaptureTreeGeneration(true);
        ActionResult result = BoneMealItemStatics.applyBonemeal(context);
        ((IWorldBridge) world).silk$setCaptureTreeGeneration(false);

        if (((IWorldBridge) world).silk$getCapturedBlockStates().size() > 0) {
            TreeType treeType = ISaplingBlockStatics.treeType;
            ISaplingBlockStatics.treeType = null;
            List<BlockState> blocks = new ArrayList<>(((IWorldBridge) world).silk$getCapturedBlockStates().values());
            ((IWorldBridge) world).silk$getCapturedBlockStates().clear();
            StructureGrowEvent structureEvent = null;

            if (treeType != null) {
                structureEvent = new StructureGrowEvent(getLocation(), treeType, true, null, blocks);
                Bukkit.getPluginManager().callEvent(structureEvent);
            }

            event = new BlockFertilizeEvent(CraftBlock.at(world, getPosition()), null, blocks);
            event.setCancelled(structureEvent != null && structureEvent.isCancelled());
            Bukkit.getPluginManager().callEvent(event);

            if (!event.isCancelled()) {
                for (BlockState blockstate : blocks) {
                    blockstate.update(true);
                }
            }
        }

        return result == ActionResult.SUCCESS && (event == null || !event.isCancelled());
    }

    @Override
    public Collection<ItemStack> getDrops() {
        return getDrops(null);
    }

    @Override
    public Collection<ItemStack> getDrops(ItemStack item) {
        return getDrops(item, null);
    }

    @Override
    public Collection<ItemStack> getDrops(ItemStack item, Entity entity) {
        net.minecraft.block.BlockState iblockdata = getNMS();
        net.minecraft.item.ItemStack nms = CraftItemStack.asNMSCopy(item);

        // Modelled off EntityHuman#hasBlock
        if (item == null || CraftBlockData.isPreferredTool(iblockdata, nms)) {
            return net.minecraft.block.Block.getDroppedStacks(iblockdata, ((IWorldAccessMixin) world).getMinecraftWorld(), position, world.getBlockEntity(position), entity == null ? null : ((CraftEntity) entity).getHandle(), nms)
                    .stream().map(CraftItemStack::asBukkitCopy).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public boolean isPreferredTool(ItemStack item) {
        net.minecraft.block.BlockState iblockdata = getNMS();
        net.minecraft.item.ItemStack nms = CraftItemStack.asNMSCopy(item);
        return CraftBlockData.isPreferredTool(iblockdata, nms);
    }

    @Override
    public float getBreakSpeed(Player player) {
        Preconditions.checkArgument(player != null, "player cannot be null");
        return getNMS().calcBlockBreakingDelta(((CraftPlayer) player).getHandle(), world, position);
    }

    @Override
    public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
        getCraftWorld().getBlockMetadata().setMetadata(this, metadataKey, newMetadataValue);
    }

    @Override
    public List<MetadataValue> getMetadata(String metadataKey) {
        return getCraftWorld().getBlockMetadata().getMetadata(this, metadataKey);
    }

    @Override
    public boolean hasMetadata(String metadataKey) {
        return getCraftWorld().getBlockMetadata().hasMetadata(this, metadataKey);
    }

    @Override
    public void removeMetadata(String metadataKey, Plugin owningPlugin) {
        getCraftWorld().getBlockMetadata().removeMetadata(this, metadataKey, owningPlugin);
    }

    @Override
    public boolean isPassable() {
        return this.getNMS().getCollisionShape(world, position).isEmpty();
    }

    @Override
    public RayTraceResult rayTrace(Location start, Vector direction, double maxDistance, FluidCollisionMode fluidCollisionMode) {
        Preconditions.checkArgument(start != null, "Location start cannot be null");
        Preconditions.checkArgument(this.getWorld().equals(start.getWorld()), "Location start cannot be a different world");
        start.checkFinite();

        Preconditions.checkArgument(direction != null, "Vector direction cannot be null");
        direction.checkFinite();
        Preconditions.checkArgument(direction.lengthSquared() > 0, "Direction's magnitude (%s) must be greater than 0", direction.lengthSquared());

        Preconditions.checkArgument(fluidCollisionMode != null, "FluidCollisionMode cannot be null");
        if (maxDistance < 0.0D) {
            return null;
        }

        Vector dir = direction.clone().normalize().multiply(maxDistance);
        Vec3d startPos = CraftLocation.toVec3D(start);
        Vec3d endPos = startPos.add(dir.getX(), dir.getY(), dir.getZ());

        HitResult nmsHitResult = ((IWorldAccessMixin) world).raycast(new RaycastContext(startPos, endPos, RaycastContext.ShapeType.OUTLINE, CraftFluidCollisionMode.toNMS(fluidCollisionMode), null), position);
        return CraftRayTraceResult.fromNMS(this.getWorld(), nmsHitResult);
    }

    @Override
    public BoundingBox getBoundingBox() {
        VoxelShape shape = getNMS().getCollisionShape(world, position);

        if (shape.isEmpty()) {
            return new BoundingBox(); // Return an empty bounding box if the block has no dimension
        }

        Box aabb = shape.getBoundingBox();
        return new BoundingBox(getX() + aabb.minX, getY() + aabb.minY, getZ() + aabb.minZ, getX() + aabb.maxX, getY() + aabb.maxY, getZ() + aabb.maxZ);
    }

    @Override
    public org.bukkit.util.VoxelShape getCollisionShape() {
        VoxelShape shape = getNMS().getCollisionShape(world, position);
        return new CraftVoxelShape(shape);
    }

    @Override
    public boolean canPlace(BlockData data) {
        Preconditions.checkArgument(data != null, "BlockData cannot be null");
        net.minecraft.block.BlockState iblockdata = ((CraftBlockData) data).getState();
        net.minecraft.world.World world = ((IWorldAccessMixin) this.world).getMinecraftWorld();

        return iblockdata.canPlaceAt(world, this.position);
    }

    @Override
    public String getTranslationKey() {
        return getNMS().getBlock().getTranslationKey();
    }
}
