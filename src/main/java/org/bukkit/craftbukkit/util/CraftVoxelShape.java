package org.bukkit.craftbukkit.util;

import com.google.common.base.Preconditions;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;
import org.bukkit.util.BoundingBox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class CraftVoxelShape implements org.bukkit.util.VoxelShape {

    private final VoxelShape shape;

    public CraftVoxelShape(VoxelShape shape) {
        this.shape = shape;
    }

    @Override
    public Collection<BoundingBox> getBoundingBoxes() {
        List<Box> boxes = shape.getBoundingBoxes();
        List<BoundingBox> craftBoxes = new ArrayList<>(boxes.size());
        for (Box aabb : boxes) {
            craftBoxes.add(new BoundingBox(aabb.minX, aabb.minY, aabb.minZ, aabb.maxX, aabb.maxY, aabb.maxZ));
        }
        return craftBoxes;
    }

    @Override
    public boolean overlaps(BoundingBox other) {
        Preconditions.checkArgument(other != null, "Other cannot be null");

        for (BoundingBox box : getBoundingBoxes()) {
            if (box.overlaps(other)) {
                return true;
            }
        }

        return false;
    }
}