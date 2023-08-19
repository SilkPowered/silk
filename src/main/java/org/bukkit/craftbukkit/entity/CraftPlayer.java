package org.bukkit.craftbukkit.entity;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.io.BaseEncoding;
import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Pair;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.shorts.ShortArraySet;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Note;
import org.bukkit.OfflinePlayer;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.WeatherType;
import org.bukkit.WorldBorder;
import org.bukkit.ban.IpBanList;
import org.bukkit.ban.ProfileBanList;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.TileState;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.sign.Side;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.ManuallyAbandonedConversationCanceller;
import org.bukkit.craftbukkit.CraftEffect;
import org.bukkit.craftbukkit.CraftEquipmentSlot;
import org.bukkit.craftbukkit.CraftOfflinePlayer;
import org.bukkit.craftbukkit.CraftParticle;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftSound;
import org.bukkit.craftbukkit.CraftStatistic;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.CraftWorldBorder;
import org.bukkit.craftbukkit.advancement.CraftAdvancement;
import org.bukkit.craftbukkit.advancement.CraftAdvancementProgress;
import org.bukkit.craftbukkit.block.CraftBlockEntityState;
import org.bukkit.craftbukkit.block.CraftBlockState;
import org.bukkit.craftbukkit.block.CraftBlockStates;
import org.bukkit.craftbukkit.block.CraftSign;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.bukkit.craftbukkit.conversations.ConversationTracker;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.craftbukkit.map.CraftMapView;
import org.bukkit.craftbukkit.map.RenderData;
import org.bukkit.craftbukkit.profile.CraftPlayerProfile;
import org.bukkit.craftbukkit.scoreboard.CraftScoreboard;
import org.bukkit.craftbukkit.util.CraftChatMessage;
import org.bukkit.craftbukkit.util.CraftLocation;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;
import org.bukkit.craftbukkit.util.CraftNamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerExpCooldownChangeEvent;
import org.bukkit.event.player.PlayerHideEntityEvent;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.event.player.PlayerShowEntityEvent;
import org.bukkit.event.player.PlayerSpawnChangeEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerUnregisterChannelEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.InventoryView.Property;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapCursor;
import org.bukkit.map.MapView;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.StandardMessenger;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.scoreboard.Scoreboard;
import org.jetbrains.annotations.NotNull;

import net.md_5.bungee.api.chat.BaseComponent; // Spigot
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.block.entity.SignText;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.map.MapIcon;
import net.minecraft.item.map.MapState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.network.packet.s2c.play.BlockBreakingProgressS2CPacket;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ChatSuggestionsS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkDeltaUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ClearTitleS2CPacket;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.network.packet.s2c.play.DamageTiltS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityAttributesS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityEquipmentUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExperienceBarUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.network.packet.s2c.play.HealthUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.MapUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundFromEntityS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerListHeaderS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRemoveS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerSpawnPositionS2CPacket;
import net.minecraft.network.packet.s2c.play.StopSoundS2CPacket;
import net.minecraft.network.packet.s2c.play.SubtitleS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleFadeS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldBorderCenterChangedS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldBorderInterpolateSizeS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldBorderSizeChangedS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldBorderWarningBlocksChangedS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldBorderWarningTimeChangedS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldEventS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.WhitelistEntry;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.border.WorldBorderListener;

@DelegateDeserialization(CraftOfflinePlayer.class)
public class CraftPlayer extends CraftHumanEntity implements Player {
    private long firstPlayed = 0;
    private long lastPlayed = 0;
    private boolean hasPlayedBefore = false;
    private final ConversationTracker conversationTracker = new ConversationTracker();
    private final Set<String> channels = new HashSet<String>();
    private final Map<UUID, Set<WeakReference<Plugin>>> invertedVisibilityEntities = new HashMap<>();
    private static final WeakHashMap<Plugin, WeakReference<Plugin>> pluginWeakReferences = new WeakHashMap<>();
    private int hash = 0;
    private double health = 20;
    private boolean scaledHealth = false;
    private double healthScale = 20;
    private CraftWorldBorder clientWorldBorder = null;
    private WorldBorderListener clientWorldBorderListener = createWorldBorderListener();

    public CraftPlayer(CraftServer server, ServerPlayerEntity entity) {
        super(server, entity);

        firstPlayed = System.currentTimeMillis();
    }

    public GameProfile getProfile() {
        return getHandle().getGameProfile();
    }

    @Override
    public boolean isOp() {
        return server.getHandle().isOperator(getProfile());
    }

    @Override
    public void setOp(boolean value) {
        if (value == isOp()) return;

        if (value) {
            server.getHandle().a(getProfile());
        } else {
            server.getHandle().b(getProfile());
        }

        perm.recalculatePermissions();
    }

    @Override
    public boolean isOnline() {
        return server.getPlayer(getUniqueId()) != null;
    }

    @Override
    public PlayerProfile getPlayerProfile() {
        return new CraftPlayerProfile(getProfile());
    }

    @Override
    public InetSocketAddress getAddress() {
        if (getHandle().connection == null) return null;

        SocketAddress addr = getHandle().connection.getConnectionAddress();
        if (addr instanceof InetSocketAddress) {
            return (InetSocketAddress) addr;
        } else {
            return null;
        }
    }

    @Override
    public double getEyeHeight(boolean ignorePose) {
        if (ignorePose) {
            return 1.62D;
        } else {
            return getEyeHeight();
        }
    }

    @Override
    public void sendRawMessage(String message) {
        this.sendRawMessage(null, message);
    }

    @Override
    public void sendRawMessage(UUID sender, String message) {
        Preconditions.checkArgument(message != null, "message cannot be null");

        if (getHandle().connection == null) return;

        for (Text component : CraftChatMessage.fromString(message)) {
            getHandle().a(component);
        }
    }

    @Override
    public void sendMessage(String message) {
        if (!conversationTracker.isConversingModaly()) {
            this.sendRawMessage(message);
        }
    }

    @Override
    public void sendMessage(String... messages) {
        for (String message : messages) {
            sendMessage(message);
        }
    }

    @Override
    public void sendMessage(UUID sender, String message) {
        if (!conversationTracker.isConversingModaly()) {
            this.sendRawMessage(sender, message);
        }
    }

    @Override
    public void sendMessage(UUID sender, String... messages) {
        for (String message : messages) {
            sendMessage(sender, message);
        }
    }

    @Override
    public String getDisplayName() {
        return getHandle().displayName;
    }

    @Override
    public void setDisplayName(final String name) {
        getHandle().displayName = name == null ? getName() : name;
    }

    @Override
    public String getPlayerListName() {
        return getHandle().listName == null ? getName() : CraftChatMessage.fromComponent(getHandle().listName);
    }

    @Override
    public void setPlayerListName(String name) {
        if (name == null) {
            name = getName();
        }
        getHandle().listName = name.equals(getName()) ? null : CraftChatMessage.fromStringOrNull(name);
        for (ServerPlayerEntity player : (List<ServerPlayerEntity>) server.getHandle().players) {
            if (player.getBukkitEntity().canSee(this)) {
                player.connection.a(new PlayerListS2CPacket(PlayerListS2CPacket.a.UPDATE_DISPLAY_NAME, getHandle()));
            }
        }
    }

    private Text playerListHeader;
    private Text playerListFooter;

    @Override
    public String getPlayerListHeader() {
        return (playerListHeader == null) ? null : CraftChatMessage.fromComponent(playerListHeader);
    }

    @Override
    public String getPlayerListFooter() {
        return (playerListFooter == null) ? null : CraftChatMessage.fromComponent(playerListFooter);
    }

    @Override
    public void setPlayerListHeader(String header) {
        this.playerListHeader = CraftChatMessage.fromStringOrNull(header, true);
        updatePlayerListHeaderFooter();
    }

    @Override
    public void setPlayerListFooter(String footer) {
        this.playerListFooter = CraftChatMessage.fromStringOrNull(footer, true);
        updatePlayerListHeaderFooter();
    }

    @Override
    public void setPlayerListHeaderFooter(String header, String footer) {
        this.playerListHeader = CraftChatMessage.fromStringOrNull(header, true);
        this.playerListFooter = CraftChatMessage.fromStringOrNull(footer, true);
        updatePlayerListHeaderFooter();
    }

    private void updatePlayerListHeaderFooter() {
        if (getHandle().connection == null) return;

        PlayerListHeaderS2CPacket packet = new PlayerListHeaderS2CPacket((this.playerListHeader == null) ? Text.empty() : this.playerListHeader, (this.playerListFooter == null) ? Text.empty() : this.playerListFooter);
        getHandle().connection.a(packet);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof OfflinePlayer)) {
            return false;
        }
        OfflinePlayer other = (OfflinePlayer) obj;
        if ((this.getUniqueId() == null) || (other.getUniqueId() == null)) {
            return false;
        }

        boolean uuidEquals = this.getUniqueId().equals(other.getUniqueId());
        boolean idEquals = true;

        if (other instanceof CraftPlayer) {
            idEquals = this.getEntityId() == ((CraftPlayer) other).getEntityId();
        }

        return uuidEquals && idEquals;
    }

    @Override
    public void kickPlayer(String message) {
        org.spigotmc.AsyncCatcher.catchOp("player kick"); // Spigot
        if (getHandle().connection == null) return;

        getHandle().connection.disconnect(message == null ? "" : message);
    }

    @Override
    public void setCompassTarget(Location loc) {
        Preconditions.checkArgument(loc != null, "Location cannot be null");

        if (getHandle().connection == null) return;

        // Do not directly assign here, from the packethandler we'll assign it.
        getHandle().connection.a(new PlayerSpawnPositionS2CPacket(CraftLocation.toBlockPosition(loc), loc.getYaw()));
    }

    @Override
    public Location getCompassTarget() {
        return getHandle().compassTarget;
    }

    @Override
    public void chat(String msg) {
        Preconditions.checkArgument(msg != null, "msg cannot be null");

        if (getHandle().connection == null) return;

        getHandle().connection.chat(msg, SignedMessage.ofUnsigned(msg), false);
    }

    @Override
    public boolean performCommand(String command) {
        Preconditions.checkArgument(command != null, "command cannot be null");
        return server.dispatchCommand(this, command);
    }

    @Override
    public void playNote(Location loc, byte instrument, byte note) {
        playNote(loc, Instrument.getByType(instrument), new Note(note));
    }

    @Override
    public void playNote(Location loc, Instrument instrument, Note note) {
        Preconditions.checkArgument(loc != null, "Location cannot be null");
        Preconditions.checkArgument(instrument != null, "Instrument cannot be null");
        Preconditions.checkArgument(note != null, "Note cannot be null");

        if (getHandle().connection == null) return;

        String instrumentName = switch (instrument.ordinal()) {
            case 0 -> "harp";
            case 1 -> "basedrum";
            case 2 -> "snare";
            case 3 -> "hat";
            case 4 -> "bass";
            case 5 -> "flute";
            case 6 -> "bell";
            case 7 -> "guitar";
            case 8 -> "chime";
            case 9 -> "xylophone";
            case 10 -> "iron_xylophone";
            case 11 -> "cow_bell";
            case 12 -> "didgeridoo";
            case 13 -> "bit";
            case 14 -> "banjo";
            case 15 -> "pling";
            case 16 -> "xylophone";
            default -> null;
        };

        float f = (float) Math.pow(2.0D, (note.getId() - 12.0D) / 12.0D);
        getHandle().connection.a(new PlaySoundS2CPacket(Registries.SOUND_EVENT.getEntry(CraftSound.getSoundEffect("block.note_block." + instrumentName)), net.minecraft.sound.SoundCategory.RECORDS, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), 3.0f, f, getHandle().getRandom().nextLong()));
    }

    @Override
    public void playSound(Location loc, Sound sound, float volume, float pitch) {
        playSound(loc, sound, org.bukkit.SoundCategory.MASTER, volume, pitch);
    }

    @Override
    public void playSound(Location loc, String sound, float volume, float pitch) {
        playSound(loc, sound, org.bukkit.SoundCategory.MASTER, volume, pitch);
    }

    @Override
    public void playSound(Location loc, Sound sound, org.bukkit.SoundCategory category, float volume, float pitch) {
        if (loc == null || sound == null || category == null || getHandle().connection == null) return;

        playSound0(loc, Registries.SOUND_EVENT.getEntry(CraftSound.getSoundEffect(sound)), net.minecraft.sound.SoundCategory.valueOf(category.name()), volume, pitch);
    }

    @Override
    public void playSound(Location loc, String sound, org.bukkit.SoundCategory category, float volume, float pitch) {
        if (loc == null || sound == null || category == null || getHandle().connection == null) return;

        playSound0(loc, RegistryEntry.of(SoundEvent.of(new Identifier(sound))), net.minecraft.sound.SoundCategory.valueOf(category.name()), volume, pitch);
    }

    private void playSound0(Location loc, RegistryEntry<SoundEvent> soundEffectHolder, net.minecraft.sound.SoundCategory categoryNMS, float volume, float pitch) {
        Preconditions.checkArgument(loc != null, "Location cannot be null");

        if (getHandle().connection == null) return;

        PlaySoundS2CPacket packet = new PlaySoundS2CPacket(soundEffectHolder, categoryNMS, loc.getX(), loc.getY(), loc.getZ(), volume, pitch, getHandle().getRandom().nextLong());
        getHandle().connection.a(packet);
    }

    @Override
    public void playSound(org.bukkit.entity.Entity entity, Sound sound, float volume, float pitch) {
        playSound(entity, sound, org.bukkit.SoundCategory.MASTER, volume, pitch);
    }

    @Override
    public void playSound(org.bukkit.entity.Entity entity, String sound, float volume, float pitch) {
        playSound(entity, sound, org.bukkit.SoundCategory.MASTER, volume, pitch);
    }

    @Override
    public void playSound(org.bukkit.entity.Entity entity, Sound sound, org.bukkit.SoundCategory category, float volume, float pitch) {
        if (!(entity instanceof CraftEntity craftEntity) || sound == null || category == null || getHandle().connection == null) return;

        playSound0(entity, Registries.SOUND_EVENT.getEntry(CraftSound.getSoundEffect(sound)), net.minecraft.sound.SoundCategory.valueOf(category.name()), volume, pitch);
    }

    @Override
    public void playSound(org.bukkit.entity.Entity entity, String sound, org.bukkit.SoundCategory category, float volume, float pitch) {
        if (!(entity instanceof CraftEntity craftEntity) || sound == null || category == null || getHandle().connection == null) return;

        playSound0(entity, RegistryEntry.of(SoundEvent.of(new Identifier(sound))), net.minecraft.sound.SoundCategory.valueOf(category.name()), volume, pitch);
    }

    private void playSound0(org.bukkit.entity.Entity entity, RegistryEntry<SoundEvent> soundEffectHolder, net.minecraft.sound.SoundCategory categoryNMS, float volume, float pitch) {
        Preconditions.checkArgument(entity != null, "Entity cannot be null");
        Preconditions.checkArgument(soundEffectHolder != null, "Holder of SoundEffect cannot be null");
        Preconditions.checkArgument(categoryNMS != null, "SoundCategory cannot be null");

        if (getHandle().connection == null) return;
        if (!(entity instanceof CraftEntity craftEntity)) return;

        PlaySoundFromEntityS2CPacket packet = new PlaySoundFromEntityS2CPacket(soundEffectHolder, categoryNMS, craftEntity.getHandle(), volume, pitch, getHandle().getRandom().nextLong());
        getHandle().connection.a(packet);
    }

    @Override
    public void stopSound(Sound sound) {
        stopSound(sound, null);
    }

    @Override
    public void stopSound(String sound) {
        stopSound(sound, null);
    }

    @Override
    public void stopSound(Sound sound, org.bukkit.SoundCategory category) {
        stopSound(sound.getKey().getKey(), category);
    }

    @Override
    public void stopSound(String sound, org.bukkit.SoundCategory category) {
        if (getHandle().connection == null) return;

        getHandle().connection.a(new StopSoundS2CPacket(new Identifier(sound), category == null ? net.minecraft.sound.SoundCategory.MASTER : net.minecraft.sound.SoundCategory.valueOf(category.name())));
    }

    @Override
    public void stopSound(org.bukkit.SoundCategory category) {
        if (getHandle().connection == null) return;

        getHandle().connection.a(new StopSoundS2CPacket(null, net.minecraft.sound.SoundCategory.valueOf(category.name())));
    }

    @Override
    public void stopAllSounds() {
        if (getHandle().connection == null) return;

        getHandle().connection.a(new StopSoundS2CPacket(null, null));
    }

    @Override
    public void playEffect(Location loc, Effect effect, int data) {
        Preconditions.checkArgument(effect != null, "Effect cannot be null");
        Preconditions.checkArgument(loc != null, "Location cannot be null");

        if (getHandle().connection == null) return;

        int packetData = effect.getId();
        WorldEventS2CPacket packet = new WorldEventS2CPacket(packetData, CraftLocation.toBlockPosition(loc), data, false);
        getHandle().connection.a(packet);
    }

    @Override
    public <T> void playEffect(Location loc, Effect effect, T data) {
        Preconditions.checkArgument(effect != null, "Effect cannot be null");
        if (data != null) {
            Preconditions.checkArgument(effect.getData() != null, "Effect.%s does not have a valid Data", effect);
            Preconditions.checkArgument(effect.getData().isAssignableFrom(data.getClass()), "%s data cannot be used for the %s effect", data.getClass().getName(), effect);
        } else {
            // Special case: the axis is optional for ELECTRIC_SPARK
            Preconditions.checkArgument(effect.getData() == null || effect == Effect.ELECTRIC_SPARK, "Wrong kind of data for the %s effect", effect);
        }

        int datavalue = CraftEffect.getDataValue(effect, data);
        playEffect(loc, effect, datavalue);
    }

    @Override
    public boolean breakBlock(Block block) {
        Preconditions.checkArgument(block != null, "Block cannot be null");
        Preconditions.checkArgument(block.getWorld().equals(getWorld()), "Cannot break blocks across worlds");

        return getHandle().gameMode.tryBreakBlock(new BlockPos(block.getX(), block.getY(), block.getZ()));
    }

    @Override
    public void sendBlockChange(Location loc, Material material, byte data) {
        if (getHandle().connection == null) return;

        BlockUpdateS2CPacket packet = new BlockUpdateS2CPacket(CraftLocation.toBlockPosition(loc), CraftMagicNumbers.getBlock(material, data));
        getHandle().connection.a(packet);
    }

    @Override
    public void sendBlockChange(Location loc, BlockData block) {
        if (getHandle().connection == null) return;

        BlockUpdateS2CPacket packet = new BlockUpdateS2CPacket(CraftLocation.toBlockPosition(loc), ((CraftBlockData) block).getState());
        getHandle().connection.a(packet);
    }

    @Override
    public void sendBlockChanges(Collection<BlockState> blocks) {
        Preconditions.checkArgument(blocks != null, "blocks must not be null");

        if (getHandle().connection == null || blocks.isEmpty()) {
            return;
        }

        Map<ChunkSectionPos, ChunkSectionChanges> changes = new HashMap<>();
        for (BlockState state : blocks) {
            CraftBlockState cstate = (CraftBlockState) state;
            BlockPos blockPosition = cstate.getPosition();

            // The coordinates of the chunk section in which the block is located, aka chunk x, y, and z
            ChunkSectionPos sectionPosition = ChunkSectionPos.from(blockPosition);

            // Push the block change position and block data to the final change map
            ChunkSectionChanges sectionChanges = changes.computeIfAbsent(sectionPosition, (ignore) -> new ChunkSectionChanges());

            sectionChanges.positions().add(ChunkSectionPos.packLocal(blockPosition));
            sectionChanges.blockData().add(cstate.getHandle());
        }

        // Construct the packets using the data allocated above and send then to the players
        for (Map.Entry<ChunkSectionPos, ChunkSectionChanges> entry : changes.entrySet()) {
            ChunkSectionChanges chunkChanges = entry.getValue();
            ChunkDeltaUpdateS2CPacket packet = new ChunkDeltaUpdateS2CPacket(entry.getKey(), chunkChanges.positions(), chunkChanges.blockData().toArray(net.minecraft.block.BlockState[]::new));
            getHandle().connection.a(packet);
        }
    }

    @Override
    public void sendBlockChanges(Collection<BlockState> blocks, boolean suppressLightUpdates) {
        this.sendBlockChanges(blocks);
    }

    private record ChunkSectionChanges(ShortSet positions, List<net.minecraft.block.BlockState> blockData) {

        public ChunkSectionChanges() {
            this(new ShortArraySet(), new ArrayList<>());
        }
    }

    @Override
    public void sendBlockDamage(Location loc, float progress) {
        this.sendBlockDamage(loc, progress, getEntityId());
    }

    @Override
    public void sendBlockDamage(Location loc, float progress, org.bukkit.entity.Entity source) {
        Preconditions.checkArgument(source != null, "source must not be null");
        this.sendBlockDamage(loc, progress, source.getEntityId());
    }

    @Override
    public void sendBlockDamage(Location loc, float progress, int sourceId) {
        Preconditions.checkArgument(loc != null, "loc must not be null");
        Preconditions.checkArgument(progress >= 0.0 && progress <= 1.0, "progress must be between 0.0 and 1.0 (inclusive)");

        if (getHandle().connection == null) return;

        int stage = (int) (9 * progress); // There are 0 - 9 damage states
        if (progress == 0.0F) {
            stage = -1; // The protocol states that any other value will reset the damage, which this API promises
        }

        BlockBreakingProgressS2CPacket packet = new BlockBreakingProgressS2CPacket(sourceId, CraftLocation.toBlockPosition(loc), stage);
        getHandle().connection.a(packet);
    }

    @Override
    public void sendSignChange(Location loc, String[] lines) {
        sendSignChange(loc, lines, DyeColor.BLACK);
    }

    @Override
    public void sendSignChange(Location loc, String[] lines, DyeColor dyeColor) {
        sendSignChange(loc, lines, dyeColor, false);
    }

    @Override
    public void sendSignChange(Location loc, String[] lines, DyeColor dyeColor, boolean hasGlowingText) {
        Preconditions.checkArgument(loc != null, "Location cannot be null");
        Preconditions.checkArgument(dyeColor != null, "DyeColor cannot be null");

        if (lines == null) {
            lines = new String[4];
        }
        Preconditions.checkArgument(lines.length >= 4, "Must have at least 4 lines (%s)", lines.length);

        if (getHandle().connection == null) return;

        Text[] components = CraftSign.sanitizeLines(lines);
        SignBlockEntity sign = new SignBlockEntity(CraftLocation.toBlockPosition(loc), Blocks.OAK_SIGN.getDefaultState());
        SignText text = sign.getFrontText();
        text = text.withColor(net.minecraft.util.DyeColor.byId(dyeColor.getWoolData()));
        text = text.withGlowing(hasGlowingText);
        for (int i = 0; i < components.length; i++) {
            text = text.withMessage(i, components[i]);
        }
        sign.setText(text, true);

        getHandle().connection.a(sign.toUpdatePacket());
    }

    @Override
    public void sendBlockUpdate(@NotNull Location location, @NotNull TileState tileState) throws IllegalArgumentException {
        Preconditions.checkArgument(location != null, "Location can not be null");
        Preconditions.checkArgument(tileState != null, "TileState can not be null");

        if (getHandle().connection == null) return;

        CraftBlockEntityState<?> craftState = ((CraftBlockEntityState<?>) tileState);
        getHandle().connection.a(craftState.getUpdatePacket(location));
    }

    @Override
    public void sendEquipmentChange(LivingEntity entity, EquipmentSlot slot, ItemStack item) {
        this.sendEquipmentChange(entity, Map.of(slot, item));
    }

    @Override
    public void sendEquipmentChange(LivingEntity entity, Map<EquipmentSlot, ItemStack> items) {
        Preconditions.checkArgument(entity != null, "Entity cannot be null");
        Preconditions.checkArgument(items != null, "items cannot be null");

        if (getHandle().connection == null) {
            return;
        }

        List<Pair<net.minecraft.entity.EquipmentSlot, net.minecraft.item.ItemStack>> equipment = new ArrayList<>(items.size());
        for (Map.Entry<EquipmentSlot, ItemStack> entry : items.entrySet()) {
            EquipmentSlot slot = entry.getKey();
            Preconditions.checkArgument(slot != null, "Cannot set null EquipmentSlot");

            equipment.add(new Pair<>(CraftEquipmentSlot.getNMS(slot), CraftItemStack.asNMSCopy(entry.getValue())));
        }

        getHandle().connection.a(new EntityEquipmentUpdateS2CPacket(entity.getEntityId(), equipment));
    }

    @Override
    public WorldBorder getWorldBorder() {
        return clientWorldBorder;
    }

    @Override
    public void setWorldBorder(WorldBorder border) {
        CraftWorldBorder craftBorder = (CraftWorldBorder) border;

        if (border != null && !craftBorder.isVirtual() && !craftBorder.getWorld().equals(getWorld())) {
            throw new UnsupportedOperationException("Cannot set player world border to that of another world");
        }

        // Nullify the old client-sided world border listeners so that calls to it will not affect this player
        if (clientWorldBorder != null) {
            this.clientWorldBorder.getHandle().removeListener(clientWorldBorderListener);
        }

        net.minecraft.world.border.WorldBorder newWorldBorder;
        if (craftBorder == null || !craftBorder.isVirtual()) {
            this.clientWorldBorder = null;
            newWorldBorder = ((CraftWorldBorder) getWorld().getWorldBorder()).getHandle();
        } else {
            this.clientWorldBorder = craftBorder;
            this.clientWorldBorder.getHandle().addListener(clientWorldBorderListener);
            newWorldBorder = clientWorldBorder.getHandle();
        }

        // Send all world border update packets to the player
        ServerPlayNetworkHandler connection = getHandle().connection;
        connection.a(new WorldBorderSizeChangedS2CPacket(newWorldBorder));
        connection.a(new WorldBorderInterpolateSizeS2CPacket(newWorldBorder));
        connection.a(new WorldBorderCenterChangedS2CPacket(newWorldBorder));
        connection.a(new WorldBorderWarningTimeChangedS2CPacket(newWorldBorder));
        connection.a(new WorldBorderWarningBlocksChangedS2CPacket(newWorldBorder));
    }

    private WorldBorderListener createWorldBorderListener() {
        return new WorldBorderListener() {
            @Override
            public void onSizeChange(net.minecraft.world.border.WorldBorder border, double size) {
                getHandle().connection.a(new WorldBorderSizeChangedS2CPacket(border));
            }

            @Override
            public void onInterpolateSize(net.minecraft.world.border.WorldBorder border, double size, double newSize, long time) {
                getHandle().connection.a(new WorldBorderInterpolateSizeS2CPacket(border));
            }

            @Override
            public void onCenterChanged(net.minecraft.world.border.WorldBorder border, double x, double z) {
                getHandle().connection.a(new WorldBorderCenterChangedS2CPacket(border));
            }

            @Override
            public void onWarningTimeChanged(net.minecraft.world.border.WorldBorder border, int warningTime) {
                getHandle().connection.a(new WorldBorderWarningTimeChangedS2CPacket(border));
            }

            @Override
            public void onWarningBlocksChanged(net.minecraft.world.border.WorldBorder border, int warningBlocks) {
                getHandle().connection.a(new WorldBorderWarningBlocksChangedS2CPacket(border));
            }

            @Override
            public void onDamagePerBlockChanged(net.minecraft.world.border.WorldBorder border, double damage) {} // NO OP

            @Override
            public void onSafeZoneChanged(net.minecraft.world.border.WorldBorder border, double blocks) {} // NO OP
        };
    }

    public boolean hasClientWorldBorder() {
        return clientWorldBorder != null;
    }

    @Override
    public void sendMap(MapView map) {
        if (getHandle().connection == null) return;

        RenderData data = ((CraftMapView) map).render(this);
        Collection<MapIcon> icons = new ArrayList<MapIcon>();
        for (MapCursor cursor : data.cursors) {
            if (cursor.isVisible()) {
                icons.add(new MapIcon(MapIcon.Type.byId(cursor.getRawType()), cursor.getX(), cursor.getY(), cursor.getDirection(), CraftChatMessage.fromStringOrNull(cursor.getCaption())));
            }
        }

        MapUpdateS2CPacket packet = new MapUpdateS2CPacket(map.getId(), map.getScale().getValue(), map.isLocked(), icons, new MapState.b(0, 0, 128, 128, data.buffer));
        getHandle().connection.a(packet);
    }

    @Override
    public void sendHurtAnimation(float yaw) {
        if (getHandle().connection == null) {
            return;
        }

        /*
         * Vanilla degrees state that 0 = left, 90 = front, 180 = right, and 270 = behind.
         * This makes no sense. We'll add 90 to it so that 0 = front, clockwise from there.
         */
        float actualYaw = yaw + 90;
        getHandle().connection.a(new DamageTiltS2CPacket(getEntityId(), actualYaw));
    }

    @Override
    public void addCustomChatCompletions(Collection<String> completions) {
        this.sendCustomChatCompletionPacket(completions, ChatSuggestionsS2CPacket.Action.ADD);
    }

    @Override
    public void removeCustomChatCompletions(Collection<String> completions) {
        this.sendCustomChatCompletionPacket(completions, ChatSuggestionsS2CPacket.Action.REMOVE);
    }

    @Override
    public void setCustomChatCompletions(Collection<String> completions) {
        this.sendCustomChatCompletionPacket(completions, ChatSuggestionsS2CPacket.Action.SET);
    }

    private void sendCustomChatCompletionPacket(Collection<String> completions, ChatSuggestionsS2CPacket.Action action) {
        if (getHandle().connection == null) return;

        ChatSuggestionsS2CPacket packet = new ChatSuggestionsS2CPacket(action, new ArrayList<>(completions));
        getHandle().connection.a(packet);
    }

    @Override
    public void setRotation(float yaw, float pitch) {
        throw new UnsupportedOperationException("Cannot set rotation of players. Consider teleporting instead.");
    }

    @Override
    public boolean teleport(Location location, PlayerTeleportEvent.TeleportCause cause) {
        Preconditions.checkArgument(location != null, "location");
        Preconditions.checkArgument(location.getWorld() != null, "location.world");
        location.checkFinite();

        ServerPlayerEntity entity = getHandle();

        if (getHealth() == 0 || entity.isRemoved()) {
            return false;
        }

        if (entity.connection == null) {
           return false;
        }

        if (entity.hasPassengers()) {
            return false;
        }

        // From = Players current Location
        Location from = this.getLocation();
        // To = Players new Location if Teleport is Successful
        Location to = location;
        // Create & Call the Teleport Event.
        PlayerTeleportEvent event = new PlayerTeleportEvent(this, from, to, cause);
        server.getPluginManager().callEvent(event);

        // Return False to inform the Plugin that the Teleport was unsuccessful/cancelled.
        if (event.isCancelled()) {
            return false;
        }

        // If this player is riding another entity, we must dismount before teleporting.
        entity.Y();

        // SPIGOT-5509: Wakeup, similar to riding
        if (this.isSleeping()) {
            this.wakeup(false);
        }

        // Update the From Location
        from = event.getFrom();
        // Grab the new To Location dependent on whether the event was cancelled.
        to = event.getTo();
        // Grab the To and From World Handles.
        ServerWorld fromWorld = ((CraftWorld) from.getWorld()).getHandle();
        ServerWorld toWorld = ((CraftWorld) to.getWorld()).getHandle();

        // Close any foreign inventory
        if (getHandle().containerMenu != getHandle().inventoryMenu) {
            getHandle().q();
        }

        // Check if the fromWorld and toWorld are the same.
        if (fromWorld == toWorld) {
            entity.connection.teleport(to);
        } else {
            // The respawn reason should never be used if the passed location is non null.
            server.getHandle().respawn(entity, toWorld, true, to, true, null);
        }
        return true;
    }

    @Override
    public void setSneaking(boolean sneak) {
        getHandle().setSneaking(sneak);
    }

    @Override
    public boolean isSneaking() {
        return getHandle().isSneaking();
    }

    @Override
    public boolean isSprinting() {
        return getHandle().isSprinting();
    }

    @Override
    public void setSprinting(boolean sprinting) {
        getHandle().g(sprinting);
    }

    @Override
    public void loadData() {
        server.getHandle().playerIo.loadPlayerData(getHandle());
    }

    @Override
    public void saveData() {
        server.getHandle().playerIo.savePlayerData(getHandle());
    }

    @Deprecated
    @Override
    public void updateInventory() {
        getHandle().containerMenu.syncState();
    }

    @Override
    public void setSleepingIgnored(boolean isSleeping) {
        getHandle().fauxSleeping = isSleeping;
        ((CraftWorld) getWorld()).getHandle().updateSleepingPlayers();
    }

    @Override
    public boolean isSleepingIgnored() {
        return getHandle().fauxSleeping;
    }

    @Override
    public Location getBedSpawnLocation() {
        ServerWorld world = getHandle().server.getWorld(getHandle().getSpawnPointDimension());
        BlockPos bed = getHandle().getSpawnPointPosition();

        if (world != null && bed != null) {
            Optional<Vec3d> spawnLoc = PlayerEntity.findRespawnPosition(world, bed, getHandle().getSpawnAngle(), getHandle().isSpawnForced(), true);
            if (spawnLoc.isPresent()) {
                Vec3d vec = spawnLoc.get();
                return CraftLocation.toBukkit(vec, world.getWorld(), getHandle().getSpawnAngle(), 0);
            }
        }
        return null;
    }

    @Override
    public void setBedSpawnLocation(Location location) {
        setBedSpawnLocation(location, false);
    }

    @Override
    public void setBedSpawnLocation(Location location, boolean override) {
        if (location == null) {
            getHandle().setRespawnPosition(null, null, 0.0F, override, false, PlayerSpawnChangeEvent.Cause.PLUGIN);
        } else {
            getHandle().setRespawnPosition(((CraftWorld) location.getWorld()).getHandle().getRegistryKey(), CraftLocation.toBlockPosition(location), location.getYaw(), override, false, PlayerSpawnChangeEvent.Cause.PLUGIN);
        }
    }

    @Override
    public Location getBedLocation() {
        Preconditions.checkState(isSleeping(), "Not sleeping");

        BlockPos bed = getHandle().getSpawnPointPosition();
        return CraftLocation.toBukkit(bed, getWorld());
    }

    @Override
    public boolean hasDiscoveredRecipe(NamespacedKey recipe) {
        Preconditions.checkArgument(recipe != null, "recipe cannot be null");
        return getHandle().getRecipeBook().contains(CraftNamespacedKey.toMinecraft(recipe));
    }

    @Override
    public Set<NamespacedKey> getDiscoveredRecipes() {
        ImmutableSet.Builder<NamespacedKey> bukkitRecipeKeys = ImmutableSet.builder();
        getHandle().getRecipeBook().known.forEach(key -> bukkitRecipeKeys.add(CraftNamespacedKey.fromMinecraft(key)));
        return bukkitRecipeKeys.build();
    }

    @Override
    public void incrementStatistic(Statistic statistic) {
        CraftStatistic.incrementStatistic(getHandle().getStatHandler(), statistic);
    }

    @Override
    public void decrementStatistic(Statistic statistic) {
        CraftStatistic.decrementStatistic(getHandle().getStatHandler(), statistic);
    }

    @Override
    public int getStatistic(Statistic statistic) {
        return CraftStatistic.getStatistic(getHandle().getStatHandler(), statistic);
    }

    @Override
    public void incrementStatistic(Statistic statistic, int amount) {
        CraftStatistic.incrementStatistic(getHandle().getStatHandler(), statistic, amount);
    }

    @Override
    public void decrementStatistic(Statistic statistic, int amount) {
        CraftStatistic.decrementStatistic(getHandle().getStatHandler(), statistic, amount);
    }

    @Override
    public void setStatistic(Statistic statistic, int newValue) {
        CraftStatistic.setStatistic(getHandle().getStatHandler(), statistic, newValue);
    }

    @Override
    public void incrementStatistic(Statistic statistic, Material material) {
        CraftStatistic.incrementStatistic(getHandle().getStatHandler(), statistic, material);
    }

    @Override
    public void decrementStatistic(Statistic statistic, Material material) {
        CraftStatistic.decrementStatistic(getHandle().getStatHandler(), statistic, material);
    }

    @Override
    public int getStatistic(Statistic statistic, Material material) {
        return CraftStatistic.getStatistic(getHandle().getStatHandler(), statistic, material);
    }

    @Override
    public void incrementStatistic(Statistic statistic, Material material, int amount) {
        CraftStatistic.incrementStatistic(getHandle().getStatHandler(), statistic, material, amount);
    }

    @Override
    public void decrementStatistic(Statistic statistic, Material material, int amount) {
        CraftStatistic.decrementStatistic(getHandle().getStatHandler(), statistic, material, amount);
    }

    @Override
    public void setStatistic(Statistic statistic, Material material, int newValue) {
        CraftStatistic.setStatistic(getHandle().getStatHandler(), statistic, material, newValue);
    }

    @Override
    public void incrementStatistic(Statistic statistic, EntityType entityType) {
        CraftStatistic.incrementStatistic(getHandle().getStatHandler(), statistic, entityType);
    }

    @Override
    public void decrementStatistic(Statistic statistic, EntityType entityType) {
        CraftStatistic.decrementStatistic(getHandle().getStatHandler(), statistic, entityType);
    }

    @Override
    public int getStatistic(Statistic statistic, EntityType entityType) {
        return CraftStatistic.getStatistic(getHandle().getStatHandler(), statistic, entityType);
    }

    @Override
    public void incrementStatistic(Statistic statistic, EntityType entityType, int amount) {
        CraftStatistic.incrementStatistic(getHandle().getStatHandler(), statistic, entityType, amount);
    }

    @Override
    public void decrementStatistic(Statistic statistic, EntityType entityType, int amount) {
        CraftStatistic.decrementStatistic(getHandle().getStatHandler(), statistic, entityType, amount);
    }

    @Override
    public void setStatistic(Statistic statistic, EntityType entityType, int newValue) {
        CraftStatistic.setStatistic(getHandle().getStatHandler(), statistic, entityType, newValue);
    }

    @Override
    public void setPlayerTime(long time, boolean relative) {
        getHandle().timeOffset = time;
        getHandle().relativeTime = relative;
    }

    @Override
    public long getPlayerTimeOffset() {
        return getHandle().timeOffset;
    }

    @Override
    public long getPlayerTime() {
        return getHandle().getPlayerTime();
    }

    @Override
    public boolean isPlayerTimeRelative() {
        return getHandle().relativeTime;
    }

    @Override
    public void resetPlayerTime() {
        setPlayerTime(0, true);
    }

    @Override
    public void setPlayerWeather(WeatherType type) {
        getHandle().setPlayerWeather(type, true);
    }

    @Override
    public WeatherType getPlayerWeather() {
        return getHandle().getPlayerWeather();
    }

    @Override
    public int getExpCooldown() {
        return getHandle().takeXpDelay;
    }

    @Override
    public void setExpCooldown(int ticks) {
        getHandle().takeXpDelay = CraftEventFactory.callPlayerXpCooldownEvent(this.getHandle(), ticks, PlayerExpCooldownChangeEvent.ChangeReason.PLUGIN).getNewCooldown();
    }

    @Override
    public void resetPlayerWeather() {
        getHandle().resetPlayerWeather();
    }

    @Override
    public boolean isBanned() {
        return ((ProfileBanList) server.getBanList(BanList.Type.PROFILE)).isBanned(getPlayerProfile());
    }

    @Override
    public BanEntry<PlayerProfile> ban(String reason, Date expires, String source) {
        return this.ban(reason, expires, source, true);
    }

    @Override
    public BanEntry<PlayerProfile> ban(String reason, Instant expires, String source) {
        return ban(reason, expires != null ? Date.from(expires) : null, source);
    }

    @Override
    public BanEntry<PlayerProfile> ban(String reason, Duration duration, String source) {
        return ban(reason, duration != null ? Instant.now().plus(duration) : null, source);
    }

    @Override
    public BanEntry<PlayerProfile> ban(String reason, Date expires, String source, boolean kickPlayer) {
        BanEntry<PlayerProfile> banEntry = ((ProfileBanList) server.getBanList(BanList.Type.PROFILE)).addBan(getPlayerProfile(), reason, expires, source);
        if (kickPlayer) {
            this.kickPlayer(reason);
        }
        return banEntry;
    }

    @Override
    public BanEntry<PlayerProfile> ban(String reason, Instant instant, String source, boolean kickPlayer) {
        return ban(reason, instant != null ? Date.from(instant) : null, source, kickPlayer);
    }

    @Override
    public BanEntry<PlayerProfile> ban(String reason, Duration duration, String source, boolean kickPlayer) {
        return ban(reason, duration != null ? Instant.now().plus(duration) : null, source, kickPlayer);
    }

    @Override
    public BanEntry<InetAddress> banIp(String reason, Date expires, String source, boolean kickPlayer) {
        Preconditions.checkArgument(getAddress() != null, "The Address of this Player is null");
        BanEntry<InetAddress> banEntry = ((IpBanList) server.getBanList(BanList.Type.IP)).addBan(getAddress().getAddress(), reason, expires, source);
        if (kickPlayer) {
            this.kickPlayer(reason);
        }
        return banEntry;
    }

    @Override
    public BanEntry<InetAddress> banIp(String reason, Instant instant, String source, boolean kickPlayer) {
        return banIp(reason, instant != null ? Date.from(instant) : null, source, kickPlayer);
    }

    @Override
    public BanEntry<InetAddress> banIp(String reason, Duration duration, String source, boolean kickPlayer) {
        return banIp(reason, duration != null ? Instant.now().plus(duration) : null, source, kickPlayer);
    }

    @Override
    public boolean isWhitelisted() {
        return server.getHandle().getWhitelist().isAllowed(getProfile());
    }

    @Override
    public void setWhitelisted(boolean value) {
        if (value) {
            server.getHandle().getWhitelist().add(new WhitelistEntry(getProfile()));
        } else {
            server.getHandle().getWhitelist().remove(getProfile());
        }
    }

    @Override
    public void setGameMode(GameMode mode) {
        Preconditions.checkArgument(mode != null, "GameMode cannot be null");
        if (getHandle().connection == null) return;

        getHandle().changeGameMode(net.minecraft.world.GameMode.byId(mode.getValue()));
    }

    @Override
    public GameMode getGameMode() {
        return GameMode.getByValue(getHandle().gameMode.getGameMode().getId());
    }

    @Override
    public GameMode getPreviousGameMode() {
        net.minecraft.world.GameMode previousGameMode = getHandle().gameMode.getPreviousGameMode();

        return (previousGameMode == null) ? null : GameMode.getByValue(previousGameMode.getId());
    }

    @Override
    public void giveExp(int exp) {
        getHandle().d(exp);
    }

    @Override
    public void giveExpLevels(int levels) {
        getHandle().c(levels);
    }

    @Override
    public float getExp() {
        return getHandle().experienceProgress;
    }

    @Override
    public void setExp(float exp) {
        Preconditions.checkArgument(exp >= 0.0 && exp <= 1.0, "Experience progress must be between 0.0 and 1.0 (%s)", exp);
        getHandle().experienceProgress = exp;
        getHandle().lastSentExp = -1;
    }

    @Override
    public int getLevel() {
        return getHandle().experienceLevel;
    }

    @Override
    public void setLevel(int level) {
        Preconditions.checkArgument(level >= 0, "Experience level must not be negative (%s)", level);
        getHandle().experienceLevel = level;
        getHandle().lastSentExp = -1;
    }

    @Override
    public int getTotalExperience() {
        return getHandle().totalExperience;
    }

    @Override
    public void setTotalExperience(int exp) {
        Preconditions.checkArgument(exp >= 0, "Total experience points must not be negative (%s)", exp);
        getHandle().totalExperience = exp;
    }

    @Override
    public void sendExperienceChange(float progress) {
        sendExperienceChange(progress, getLevel());
    }

    @Override
    public void sendExperienceChange(float progress, int level) {
        Preconditions.checkArgument(progress >= 0.0 && progress <= 1.0, "Experience progress must be between 0.0 and 1.0 (%s)", progress);
        Preconditions.checkArgument(level >= 0, "Experience level must not be negative (%s)", level);

        if (getHandle().connection == null) {
            return;
        }

        ExperienceBarUpdateS2CPacket packet = new ExperienceBarUpdateS2CPacket(progress, getTotalExperience(), level);
        getHandle().connection.a(packet);
    }

    @Nullable
    private static WeakReference<Plugin> getPluginWeakReference(@Nullable Plugin plugin) {
        return (plugin == null) ? null : pluginWeakReferences.computeIfAbsent(plugin, WeakReference::new);
    }

    @Override
    @Deprecated
    public void hidePlayer(Player player) {
        hideEntity0(null, player);
    }

    @Override
    public void hidePlayer(Plugin plugin, Player player) {
        hideEntity(plugin, player);
    }

    @Override
    public void hideEntity(Plugin plugin, org.bukkit.entity.Entity entity) {
        Preconditions.checkArgument(plugin != null, "Plugin cannot be null");
        Preconditions.checkArgument(plugin.isEnabled(), "Plugin (%s) cannot be disabled", plugin.getName());

        hideEntity0(plugin, entity);
    }

    private void hideEntity0(@Nullable Plugin plugin, org.bukkit.entity.Entity entity) {
        Preconditions.checkArgument(entity != null, "Entity hidden cannot be null");
        if (getHandle().connection == null) return;
        if (equals(entity)) return;

        boolean shouldHide;
        if (entity.isVisibleByDefault()) {
            shouldHide = addInvertedVisibility(plugin, entity);
        } else {
            shouldHide = removeInvertedVisibility(plugin, entity);
        }

        if (shouldHide) {
            untrackAndHideEntity(entity);
        }
    }

    private boolean addInvertedVisibility(@Nullable Plugin plugin, org.bukkit.entity.Entity entity) {
        Set<WeakReference<Plugin>> invertedPlugins = invertedVisibilityEntities.get(entity.getUniqueId());
        if (invertedPlugins != null) {
            // Some plugins are already inverting the entity. Just mark that this
            // plugin wants the entity inverted too and end.
            invertedPlugins.add(getPluginWeakReference(plugin));
            return false;
        }
        invertedPlugins = new HashSet<>();
        invertedPlugins.add(getPluginWeakReference(plugin));
        invertedVisibilityEntities.put(entity.getUniqueId(), invertedPlugins);

        return true;
    }

    private void untrackAndHideEntity(org.bukkit.entity.Entity entity) {
        // Remove this entity from the hidden player's EntityTrackerEntry
        ThreadedAnvilChunkStorage tracker = ((ServerWorld) getHandle().getWorld()).getChunkManager().chunkMap;
        Entity other = ((CraftEntity) entity).getHandle();
        ThreadedAnvilChunkStorage.EntityTracker entry = tracker.entityMap.get(other.af());
        if (entry != null) {
            entry.stopTracking(getHandle());
        }

        // Remove the hidden entity from this player user list, if they're on it
        if (other instanceof ServerPlayerEntity) {
            ServerPlayerEntity otherPlayer = (ServerPlayerEntity) other;
            if (otherPlayer.sentListPacket) {
                getHandle().connection.a(new PlayerRemoveS2CPacket(List.of(otherPlayer.ct())));
            }
        }

        server.getPluginManager().callEvent(new PlayerHideEntityEvent(this, entity));
    }

    void resetAndHideEntity(org.bukkit.entity.Entity entity) {
        // SPIGOT-7312: Can't show/hide self
        if (equals(entity)) {
            return;
        }

        if (invertedVisibilityEntities.remove(entity.getUniqueId()) == null) {
            untrackAndHideEntity(entity);
        }
    }

    @Override
    @Deprecated
    public void showPlayer(Player player) {
        showEntity0(null, player);
    }

    @Override
    public void showPlayer(Plugin plugin, Player player) {
        showEntity(plugin, player);
    }

    @Override
    public void showEntity(Plugin plugin, org.bukkit.entity.Entity entity) {
        Preconditions.checkArgument(plugin != null, "Plugin cannot be null");
        // Don't require that plugin be enabled. A plugin must be allowed to call
        // showPlayer during its onDisable() method.
        showEntity0(plugin, entity);
    }

    private void showEntity0(@Nullable Plugin plugin, org.bukkit.entity.Entity entity) {
        Preconditions.checkArgument(entity != null, "Entity show cannot be null");
        if (getHandle().connection == null) return;
        if (equals(entity)) return;

        boolean shouldShow;
        if (entity.isVisibleByDefault()) {
            shouldShow = removeInvertedVisibility(plugin, entity);
        } else {
            shouldShow = addInvertedVisibility(plugin, entity);
        }

        if (shouldShow) {
            trackAndShowEntity(entity);
        }
    }

    private boolean removeInvertedVisibility(@Nullable Plugin plugin, org.bukkit.entity.Entity entity) {
        Set<WeakReference<Plugin>> invertedPlugins = invertedVisibilityEntities.get(entity.getUniqueId());
        if (invertedPlugins == null) {
            return false; // Entity isn't inverted
        }
        invertedPlugins.remove(getPluginWeakReference(plugin));
        if (!invertedPlugins.isEmpty()) {
            return false; // Some other plugins still want the entity inverted
        }
        invertedVisibilityEntities.remove(entity.getUniqueId());

        return true;
    }

    private void trackAndShowEntity(org.bukkit.entity.Entity entity) {
        ThreadedAnvilChunkStorage tracker = ((ServerWorld) getHandle().getWorld()).getChunkManager().chunkMap;
        Entity other = ((CraftEntity) entity).getHandle();

        if (other instanceof ServerPlayerEntity) {
            ServerPlayerEntity otherPlayer = (ServerPlayerEntity) other;
            getHandle().connection.a(PlayerListS2CPacket.entryFromPlayer(List.of(otherPlayer)));
        }

        ThreadedAnvilChunkStorage.EntityTracker entry = tracker.entityMap.get(other.af());
        if (entry != null && !entry.seenBy.contains(getHandle().connection)) {
            entry.updateTrackedStatus(getHandle());
        }

        server.getPluginManager().callEvent(new PlayerShowEntityEvent(this, entity));
    }

    void resetAndShowEntity(org.bukkit.entity.Entity entity) {
        // SPIGOT-7312: Can't show/hide self
        if (equals(entity)) {
            return;
        }

        if (invertedVisibilityEntities.remove(entity.getUniqueId()) == null) {
            trackAndShowEntity(entity);
        }
    }

    public void onEntityRemove(Entity entity) {
        invertedVisibilityEntities.remove(entity.ct());
    }

    @Override
    public boolean canSee(Player player) {
        return canSee((org.bukkit.entity.Entity) player);
    }

    @Override
    public boolean canSee(org.bukkit.entity.Entity entity) {
        return equals(entity) || entity.isVisibleByDefault() ^ invertedVisibilityEntities.containsKey(entity.getUniqueId()); // SPIGOT-7312: Can always see self
    }

    public boolean canSee(UUID uuid) {
        org.bukkit.entity.Entity entity = getServer().getPlayer(uuid);
        if (entity == null) {
            entity = getServer().getEntity(uuid); // Also includes players, but check players first for efficiency
        }

        return (entity != null) ? canSee(entity) : false; // If we can't find it, we can't see it
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new LinkedHashMap<String, Object>();

        result.put("name", getName());

        return result;
    }

    @Override
    public Player getPlayer() {
        return this;
    }

    @Override
    public ServerPlayerEntity getHandle() {
        return (ServerPlayerEntity) entity;
    }

    public void setHandle(final ServerPlayerEntity entity) {
        super.setHandle(entity);
    }

    @Override
    public String toString() {
        return "CraftPlayer{" + "name=" + getName() + '}';
    }

    @Override
    public int hashCode() {
        if (hash == 0 || hash == 485) {
            hash = 97 * 5 + (this.getUniqueId() != null ? this.getUniqueId().hashCode() : 0);
        }
        return hash;
    }

    @Override
    public long getFirstPlayed() {
        return firstPlayed;
    }

    @Override
    public long getLastPlayed() {
        return lastPlayed;
    }

    @Override
    public boolean hasPlayedBefore() {
        return hasPlayedBefore;
    }

    public void setFirstPlayed(long firstPlayed) {
        this.firstPlayed = firstPlayed;
    }

    public void readExtraData(NbtCompound nbttagcompound) {
        hasPlayedBefore = true;
        if (nbttagcompound.contains("bukkit")) {
            NbtCompound data = nbttagcompound.getCompound("bukkit");

            if (data.contains("firstPlayed")) {
                firstPlayed = data.getLong("firstPlayed");
                lastPlayed = data.getLong("lastPlayed");
            }

            if (data.contains("newExp")) {
                ServerPlayerEntity handle = getHandle();
                handle.newExp = data.getInt("newExp");
                handle.newTotalExp = data.getInt("newTotalExp");
                handle.newLevel = data.getInt("newLevel");
                handle.expToDrop = data.getInt("expToDrop");
                handle.keepLevel = data.getBoolean("keepLevel");
            }
        }
    }

    public void setExtraData(NbtCompound nbttagcompound) {
        if (!nbttagcompound.contains("bukkit")) {
            nbttagcompound.put("bukkit", new NbtCompound());
        }

        NbtCompound data = nbttagcompound.getCompound("bukkit");
        ServerPlayerEntity handle = getHandle();
        data.putInt("newExp", handle.newExp);
        data.putInt("newTotalExp", handle.newTotalExp);
        data.putInt("newLevel", handle.newLevel);
        data.putInt("expToDrop", handle.expToDrop);
        data.putBoolean("keepLevel", handle.keepLevel);
        data.putLong("firstPlayed", getFirstPlayed());
        data.putLong("lastPlayed", System.currentTimeMillis());
        data.putString("lastKnownName", handle.cv());
    }

    @Override
    public boolean beginConversation(Conversation conversation) {
        return conversationTracker.beginConversation(conversation);
    }

    @Override
    public void abandonConversation(Conversation conversation) {
        conversationTracker.abandonConversation(conversation, new ConversationAbandonedEvent(conversation, new ManuallyAbandonedConversationCanceller()));
    }

    @Override
    public void abandonConversation(Conversation conversation, ConversationAbandonedEvent details) {
        conversationTracker.abandonConversation(conversation, details);
    }

    @Override
    public void acceptConversationInput(String input) {
        conversationTracker.acceptConversationInput(input);
    }

    @Override
    public boolean isConversing() {
        return conversationTracker.isConversing();
    }

    @Override
    public void sendPluginMessage(Plugin source, String channel, byte[] message) {
        StandardMessenger.validatePluginMessage(server.getMessenger(), source, channel, message);
        if (getHandle().connection == null) return;

        if (channels.contains(channel)) {
            channel = StandardMessenger.validateAndCorrectChannel(channel);
            CustomPayloadS2CPacket packet = new CustomPayloadS2CPacket(new Identifier(channel), new PacketByteBuf(Unpooled.wrappedBuffer(message)));
            getHandle().connection.a(packet);
        }
    }

    @Override
    public void setTexturePack(String url) {
        setResourcePack(url);
    }

    @Override
    public void setResourcePack(String url) {
        setResourcePack(url, null);
    }

    @Override
    public void setResourcePack(String url, byte[] hash) {
        setResourcePack(url, hash, false);
    }

    @Override
    public void setResourcePack(String url, byte[] hash, String prompt) {
        setResourcePack(url, hash, prompt, false);
    }

    @Override
    public void setResourcePack(String url, byte[] hash, boolean force) {
        setResourcePack(url, hash, null, force);
    }

    @Override
    public void setResourcePack(String url, byte[] hash, String prompt, boolean force) {
        Preconditions.checkArgument(url != null, "Resource pack URL cannot be null");

        if (hash != null) {
            Preconditions.checkArgument(hash.length == 20, "Resource pack hash should be 20 bytes long but was %s", hash.length);

            getHandle().sendResourcePackUrl(url, BaseEncoding.base16().lowerCase().encode(hash), force, CraftChatMessage.fromStringOrNull(prompt, true));
        } else {
            getHandle().sendResourcePackUrl(url, "", force, CraftChatMessage.fromStringOrNull(prompt, true));
        }
    }

    public void addChannel(String channel) {
        Preconditions.checkState(channels.size() < 128, "Cannot register channel '%s'. Too many channels registered!", channel);
        channel = StandardMessenger.validateAndCorrectChannel(channel);
        if (channels.add(channel)) {
            server.getPluginManager().callEvent(new PlayerRegisterChannelEvent(this, channel));
        }
    }

    public void removeChannel(String channel) {
        channel = StandardMessenger.validateAndCorrectChannel(channel);
        if (channels.remove(channel)) {
            server.getPluginManager().callEvent(new PlayerUnregisterChannelEvent(this, channel));
        }
    }

    @Override
    public Set<String> getListeningPluginChannels() {
        return ImmutableSet.copyOf(channels);
    }

    public void sendSupportedChannels() {
        if (getHandle().connection == null) return;
        Set<String> listening = server.getMessenger().getIncomingChannels();

        if (!listening.isEmpty()) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            for (String channel : listening) {
                try {
                    stream.write(channel.getBytes("UTF8"));
                    stream.write((byte) 0);
                } catch (IOException ex) {
                    Logger.getLogger(CraftPlayer.class.getName()).log(Level.SEVERE, "Could not send Plugin Channel REGISTER to " + getName(), ex);
                }
            }

            getHandle().connection.a(new CustomPayloadS2CPacket(new Identifier("register"), new PacketByteBuf(Unpooled.wrappedBuffer(stream.toByteArray()))));
        }
    }

    @Override
    public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
        server.getPlayerMetadata().setMetadata(this, metadataKey, newMetadataValue);
    }

    @Override
    public List<MetadataValue> getMetadata(String metadataKey) {
        return server.getPlayerMetadata().getMetadata(this, metadataKey);
    }

    @Override
    public boolean hasMetadata(String metadataKey) {
        return server.getPlayerMetadata().hasMetadata(this, metadataKey);
    }

    @Override
    public void removeMetadata(String metadataKey, Plugin owningPlugin) {
        server.getPlayerMetadata().removeMetadata(this, metadataKey, owningPlugin);
    }

    @Override
    public boolean setWindowProperty(Property prop, int value) {
        ScreenHandler container = getHandle().containerMenu;
        if (container.getBukkitView().getType() != prop.getType()) {
            return false;
        }
        container.setProperty(prop.getId(), value);
        return true;
    }

    public void disconnect(String reason) {
        conversationTracker.abandonAllConversations();
        perm.clearPermissions();
    }

    @Override
    public boolean isFlying() {
        return getHandle().getAbilities().flying;
    }

    @Override
    public void setFlying(boolean value) {
        if (!getAllowFlight()) {
            Preconditions.checkArgument(!value, "Player is not allowed to fly (check #getAllowFlight())");
        }

        getHandle().getAbilities().flying = value;
        getHandle().w();
    }

    @Override
    public boolean getAllowFlight() {
        return getHandle().getAbilities().mayfly;
    }

    @Override
    public void setAllowFlight(boolean value) {
        if (isFlying() && !value) {
            getHandle().getAbilities().flying = false;
        }

        getHandle().getAbilities().mayfly = value;
        getHandle().w();
    }

    @Override
    public int getNoDamageTicks() {
        if (getHandle().spawnInvulnerableTime > 0) {
            return Math.max(getHandle().spawnInvulnerableTime, getHandle().invulnerableTime);
        } else {
            return getHandle().invulnerableTime;
        }
    }

    @Override
    public void setNoDamageTicks(int ticks) {
        super.setNoDamageTicks(ticks);
        getHandle().spawnInvulnerableTime = ticks; // SPIGOT-5921: Update both for players, like the getter above
    }

    @Override
    public void setFlySpeed(float value) {
        validateSpeed(value);
        ServerPlayerEntity player = getHandle();
        player.getAbilities().flyingSpeed = value / 2f;
        player.w();

    }

    @Override
    public void setWalkSpeed(float value) {
        validateSpeed(value);
        ServerPlayerEntity player = getHandle();
        player.getAbilities().walkingSpeed = value / 2f;
        player.w();
        getHandle().getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).setBaseValue(player.getAbilities().walkingSpeed); // SPIGOT-5833: combination of the two in 1.16+
    }

    @Override
    public float getFlySpeed() {
        return (float) getHandle().getAbilities().flyingSpeed * 2f;
    }

    @Override
    public float getWalkSpeed() {
        return getHandle().getAbilities().walkingSpeed * 2f;
    }

    private void validateSpeed(float value) {
        Preconditions.checkArgument(value <= 1f && value >= -1f, "Speed value (%s) need to be between -1f and 1f", value);
    }

    @Override
    public void setMaxHealth(double amount) {
        super.setMaxHealth(amount);
        this.health = Math.min(this.health, amount);
        getHandle().markHealthDirty();
    }

    @Override
    public void resetMaxHealth() {
        super.resetMaxHealth();
        getHandle().markHealthDirty();
    }

    @Override
    public CraftScoreboard getScoreboard() {
        return this.server.getScoreboardManager().getPlayerBoard(this);
    }

    @Override
    public void setScoreboard(Scoreboard scoreboard) {
        Preconditions.checkArgument(scoreboard != null, "Scoreboard cannot be null");
        Preconditions.checkState(getHandle().connection != null, "Cannot set scoreboard yet (invalid player connection)");
        // Preconditions.checkState(!getHandle().connection.isDisconnected(), "Cannot set scoreboard for invalid CraftPlayer (player is disconnected)");

        this.server.getScoreboardManager().setPlayerBoard(this, scoreboard);
    }

    @Override
    public void setHealthScale(double value) {
        Preconditions.checkArgument(value > 0F, "Health value (%s) must be greater than 0", value);
        healthScale = value;
        scaledHealth = true;
        updateScaledHealth();
    }

    @Override
    public double getHealthScale() {
        return healthScale;
    }

    @Override
    public void setHealthScaled(boolean scale) {
        if (scaledHealth != (scaledHealth = scale)) {
            updateScaledHealth();
        }
    }

    @Override
    public boolean isHealthScaled() {
        return scaledHealth;
    }

    public float getScaledHealth() {
        return (float) (isHealthScaled() ? getHealth() * getHealthScale() / getMaxHealth() : getHealth());
    }

    @Override
    public double getHealth() {
        return health;
    }

    public void setRealHealth(double health) {
        this.health = health;
    }

    public void updateScaledHealth() {
        updateScaledHealth(true);
    }

    public void updateScaledHealth(boolean sendHealth) {
        AttributeContainer attributemapserver = getHandle().getAttributes();
        Collection<EntityAttributeInstance> set = attributemapserver.getAttributesToSend();

        injectScaledMaxHealth(set, true);

        // SPIGOT-3813: Attributes before health
        if (getHandle().connection != null) {
            getHandle().connection.a(new EntityAttributesS2CPacket(getHandle().af(), set));
            if (sendHealth) {
                sendHealthUpdate();
            }
        }
        getHandle().getDataTracker().set(net.minecraft.entity.LivingEntity.DATA_HEALTH_ID, (float) getScaledHealth());

        getHandle().maxHealthCache = getMaxHealth();
    }

    @Override
    public void sendHealthUpdate(double health, int foodLevel, float saturation) {
        getHandle().connection.a(new HealthUpdateS2CPacket((float) health, foodLevel, saturation));
    }

    @Override
    public void sendHealthUpdate() {
        HungerManager foodData = getHandle().getHungerManager();
        sendHealthUpdate(getScaledHealth(), foodData.getFoodLevel(), foodData.getSaturationLevel());
    }

    public void injectScaledMaxHealth(Collection<EntityAttributeInstance> collection, boolean force) {
        if (!scaledHealth && !force) {
            return;
        }
        for (EntityAttributeInstance genericInstance : collection) {
            if (genericInstance.getAttribute() == EntityAttributes.MAX_HEALTH) {
                collection.remove(genericInstance);
                break;
            }
        }
        EntityAttributeInstance dummy = new EntityAttributeInstance(EntityAttributes.MAX_HEALTH, (attribute) -> { });
        // Spigot start
        double healthMod = scaledHealth ? healthScale : getMaxHealth();
        if ( healthMod >= Float.MAX_VALUE || healthMod <= 0 )
        {
            healthMod = 20; // Reset health
            getServer().getLogger().warning( getName() + " tried to crash the server with a large health attribute" );
        }
        dummy.setBaseValue(healthMod);
        // Spigot end
        collection.add(dummy);
    }

    @Override
    public org.bukkit.entity.Entity getSpectatorTarget() {
        Entity followed = getHandle().getCameraEntity();
        return followed == getHandle() ? null : followed.getBukkitEntity();
    }

    @Override
    public void setSpectatorTarget(org.bukkit.entity.Entity entity) {
        Preconditions.checkArgument(getGameMode() == GameMode.SPECTATOR, "Player must be in spectator mode");
        getHandle().setCameraEntity((entity == null) ? null : ((CraftEntity) entity).getHandle());
    }

    @Override
    public void sendTitle(String title, String subtitle) {
        sendTitle(title, subtitle, 10, 70, 20);
    }

    @Override
    public void sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        TitleFadeS2CPacket times = new TitleFadeS2CPacket(fadeIn, stay, fadeOut);
        getHandle().connection.a(times);

        if (title != null) {
            TitleS2CPacket packetTitle = new TitleS2CPacket(CraftChatMessage.fromString(title)[0]);
            getHandle().connection.a(packetTitle);
        }

        if (subtitle != null) {
            SubtitleS2CPacket packetSubtitle = new SubtitleS2CPacket(CraftChatMessage.fromString(subtitle)[0]);
            getHandle().connection.a(packetSubtitle);
        }
    }

    @Override
    public void resetTitle() {
        ClearTitleS2CPacket packetReset = new ClearTitleS2CPacket(true);
        getHandle().connection.a(packetReset);
    }

    @Override
    public void spawnParticle(Particle particle, Location location, int count) {
        spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count);
    }

    @Override
    public void spawnParticle(Particle particle, double x, double y, double z, int count) {
        spawnParticle(particle, x, y, z, count, null);
    }

    @Override
    public <T> void spawnParticle(Particle particle, Location location, int count, T data) {
        spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count, data);
    }

    @Override
    public <T> void spawnParticle(Particle particle, double x, double y, double z, int count, T data) {
        spawnParticle(particle, x, y, z, count, 0, 0, 0, data);
    }

    @Override
    public void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY, double offsetZ) {
        spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ);
    }

    @Override
    public void spawnParticle(Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ) {
        spawnParticle(particle, x, y, z, count, offsetX, offsetY, offsetZ, null);
    }

    @Override
    public <T> void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY, double offsetZ, T data) {
        spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ, data);
    }

    @Override
    public <T> void spawnParticle(Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, T data) {
        spawnParticle(particle, x, y, z, count, offsetX, offsetY, offsetZ, 1, data);
    }

    @Override
    public void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY, double offsetZ, double extra) {
        spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ, extra);
    }

    @Override
    public void spawnParticle(Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, double extra) {
        spawnParticle(particle, x, y, z, count, offsetX, offsetY, offsetZ, extra, null);
    }

    @Override
    public <T> void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY, double offsetZ, double extra, T data) {
        spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ, extra, data);
    }

    @Override
    public <T> void spawnParticle(Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, double extra, T data) {
        if (data != null) {
            Preconditions.checkArgument(particle.getDataType().isInstance(data), "data (%s) should be %s", data.getClass(), particle.getDataType());
        }
        ParticleS2CPacket packetplayoutworldparticles = new ParticleS2CPacket(CraftParticle.toNMS(particle, data), true, (float) x, (float) y, (float) z, (float) offsetX, (float) offsetY, (float) offsetZ, (float) extra, count);
        getHandle().connection.a(packetplayoutworldparticles);

    }

    @Override
    public org.bukkit.advancement.AdvancementProgress getAdvancementProgress(org.bukkit.advancement.Advancement advancement) {
        Preconditions.checkArgument(advancement != null, "advancement");

        CraftAdvancement craft = (CraftAdvancement) advancement;
        PlayerAdvancementTracker data = getHandle().getAdvancementTracker();
        AdvancementProgress progress = data.getProgress(craft.getHandle());

        return new CraftAdvancementProgress(craft, data, progress);
    }

    @Override
    public int getClientViewDistance() {
        return (getHandle().clientViewDistance == null) ? Bukkit.getViewDistance() : getHandle().clientViewDistance;
    }

    @Override
    public int getPing() {
        return getHandle().latency;
    }

    @Override
    public String getLocale() {
        return getHandle().locale;
    }

    @Override
    public void updateCommands() {
        if (getHandle().connection == null) return;

        getHandle().server.getCommandManager().sendCommandTree(getHandle());
    }

    @Override
    public void openBook(ItemStack book) {
        Preconditions.checkArgument(book != null, "ItemStack cannot be null");
        Preconditions.checkArgument(book.getType() == Material.WRITTEN_BOOK, "ItemStack Material (%s) must be Material.WRITTEN_BOOK", book.getType());

        ItemStack hand = getInventory().getItemInMainHand();
        getInventory().setItemInMainHand(book);
        getHandle().a(org.bukkit.craftbukkit.inventory.CraftItemStack.asNMSCopy(book), net.minecraft.util.Hand.MAIN_HAND);
        getInventory().setItemInMainHand(hand);
    }

    @Override
    public void openSign(Sign sign) {
        openSign(sign, Side.FRONT);
    }

    @Override
    public void openSign(@NotNull Sign sign, @NotNull Side side) {
        CraftSign.openSign(sign, this, side);
    }

    @Override
    public void showDemoScreen() {
        if (getHandle().connection == null) return;

        getHandle().connection.a(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.DEMO_EVENT, GameStateChangeS2CPacket.DEMO_PARAM_INTRO));
    }

    @Override
    public boolean isAllowingServerListings() {
        return getHandle().allowsServerListing();
    }

    // Spigot start
    private final Player.Spigot spigot = new Player.Spigot()
    {

        @Override
        public InetSocketAddress getRawAddress()
        {
            return (InetSocketAddress) getHandle().connection.getRawAddress();
        }

        @Override
        public void respawn()
        {
            if ( getHealth() <= 0 && isOnline() )
            {
                server.getServer().getPlayerManager().respawn( getHandle(), false, org.bukkit.event.player.PlayerRespawnEvent.RespawnReason.PLUGIN );
            }
        }

        @Override
        public Set<Player> getHiddenPlayers()
        {
            Set<Player> ret = new HashSet<>();
            for ( Player p : getServer().getOnlinePlayers() )
            {
                if ( !CraftPlayer.this.canSee(p) )
                {
                    ret.add( p );
                }
            }

            return java.util.Collections.unmodifiableSet( ret );
        }

        @Override
        public void sendMessage(BaseComponent component) {
          sendMessage( new BaseComponent[] { component } );
        }

        @Override
        public void sendMessage(BaseComponent... components) {
           this.sendMessage(net.md_5.bungee.api.ChatMessageType.SYSTEM, components);
        }

        @Override
        public void sendMessage(UUID sender, BaseComponent component) {
            this.sendMessage(net.md_5.bungee.api.ChatMessageType.CHAT, sender, component);
        }

        @Override
        public void sendMessage(UUID sender, BaseComponent... components) {
            this.sendMessage(net.md_5.bungee.api.ChatMessageType.CHAT, sender, components);
        }

        @Override
        public void sendMessage(net.md_5.bungee.api.ChatMessageType position, BaseComponent component) {
            sendMessage( position, new BaseComponent[] { component } );
        }

        @Override
        public void sendMessage(net.md_5.bungee.api.ChatMessageType position, BaseComponent... components) {
            this.sendMessage(position, null, components);
        }

        @Override
        public void sendMessage(net.md_5.bungee.api.ChatMessageType position, UUID sender, BaseComponent component) {
            sendMessage( position, sender, new BaseComponent[] { component } );
        }

        @Override
        public void sendMessage(net.md_5.bungee.api.ChatMessageType position, UUID sender, BaseComponent... components) {
            if ( getHandle().connection == null ) return;

            getHandle().connection.a(new net.minecraft.network.packet.s2c.play.GameMessageS2CPacket(components, position == net.md_5.bungee.api.ChatMessageType.ACTION_BAR));
        }
    };

    public Player.Spigot spigot()
    {
        return spigot;
    }
    // Spigot end
}
