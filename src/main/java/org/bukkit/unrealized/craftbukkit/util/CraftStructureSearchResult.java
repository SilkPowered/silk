package org.bukkit.unrealized.craftbukkit.util;

import org.bukkit.unrealized.Location;
import org.bukkit.unrealized.generator.structure.Structure;
import org.bukkit.unrealized.util.StructureSearchResult;

public class CraftStructureSearchResult implements StructureSearchResult {

    private final Structure structure;
    private final Location location;

    public CraftStructureSearchResult(Structure structure, Location location) {
        this.structure = structure;
        this.location = location;
    }

    public Structure getStructure() {
        return structure;
    }

    public Location getLocation() {
        return location;
    }
}
