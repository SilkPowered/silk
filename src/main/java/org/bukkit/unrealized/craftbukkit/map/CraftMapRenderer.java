package org.bukkit.unrealized.craftbukkit.map;

import net.minecraft.world.level.saveddata.maps.MapIcon;
import net.minecraft.world.level.saveddata.maps.WorldMap;
import org.bukkit.Bukkit;
import org.bukkit.unrealized.craftbukkit.util.CraftChatMessage;
import org.bukkit.unrealized.entity.Player;
import org.bukkit.unrealized.map.MapCanvas;
import org.bukkit.unrealized.map.MapCursorCollection;
import org.bukkit.unrealized.map.MapRenderer;
import org.bukkit.unrealized.map.MapView;

public class CraftMapRenderer extends MapRenderer {

    private final WorldMap worldMap;

    public CraftMapRenderer(CraftMapView mapView, WorldMap worldMap) {
        super(false);
        this.worldMap = worldMap;
    }

    @Override
    public void render(MapView map, MapCanvas canvas, Player player) {
        // Map
        for (int x = 0; x < 128; ++x) {
            for (int y = 0; y < 128; ++y) {
                canvas.setPixel(x, y, worldMap.colors[y * 128 + x]);
            }
        }

        // Cursors
        MapCursorCollection cursors = canvas.getCursors();
        while (cursors.size() > 0) {
            cursors.removeCursor(cursors.getCursor(0));
        }

        for (String key : worldMap.decorations.keySet()) {
            // If this cursor is for a player check visibility with vanish system
            Player other = Bukkit.getPlayerExact((String) key);
            if (other != null && !player.canSee(other)) {
                continue;
            }

            MapIcon decoration = worldMap.decorations.get(key);
            cursors.addCursor(decoration.getX(), decoration.getY(), (byte) (decoration.getRot() & 15), decoration.getType().getIcon(), true, CraftChatMessage.fromComponent(decoration.getName()));
        }
    }

}