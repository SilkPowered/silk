package org.bukkit.unrealized.craftbukkit.metadata;

import org.bukkit.unrealized.OfflinePlayer;
import org.bukkit.unrealized.entity.Player;
import org.bukkit.unrealized.metadata.MetadataStore;
import org.bukkit.unrealized.metadata.MetadataStoreBase;

/**
 * A PlayerMetadataStore stores metadata for {@link Player} and {@link OfflinePlayer} objects.
 */
public class PlayerMetadataStore extends MetadataStoreBase<OfflinePlayer> implements MetadataStore<OfflinePlayer> {
    /**
     * Generates a unique metadata key for {@link Player} and {@link OfflinePlayer} using the player
     * UUID.
     * @param player the player
     * @param metadataKey The name identifying the metadata value
     * @return a unique metadata key
     * @see MetadataStoreBase#disambiguate(Object, String)
     */
    @Override
    protected String disambiguate(OfflinePlayer player, String metadataKey) {
        return player.getUniqueId() + ":" + metadataKey;
    }
}
