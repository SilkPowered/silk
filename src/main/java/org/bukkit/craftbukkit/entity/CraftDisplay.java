package org.bukkit.craftbukkit.entity;

import com.google.common.base.Preconditions;
import org.bukkit.Color;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Display;
import org.bukkit.util.Transformation;

public class CraftDisplay extends CraftEntity implements Display {

    public CraftDisplay(CraftServer server, net.minecraft.entity.decoration.DisplayEntity entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.entity.decoration.DisplayEntity getHandle() {
        return (net.minecraft.entity.decoration.DisplayEntity) super.getHandle();
    }

    @Override
    public String toString() {
        return "CraftDisplay";
    }

    @Override
    public Transformation getTransformation() {
        net.minecraft.util.math.AffineTransformation nms = net.minecraft.entity.decoration.DisplayEntity.getTransformation(getHandle().getDataTracker());

        return new Transformation(nms.getTranslation(), nms.getLeftRotation(), nms.getScale(), nms.getRightRotation());
    }

    @Override
    public void setTransformation(Transformation transformation) {
        Preconditions.checkArgument(transformation != null, "Transformation cannot be null");

        getHandle().setTransformation(new net.minecraft.util.math.AffineTransformation(transformation.getTranslation(), transformation.getLeftRotation(), transformation.getScale(), transformation.getRightRotation()));
    }

    @Override
    public void setTransformationMatrix(org.joml.Matrix4f transformationMatrix) {
        Preconditions.checkArgument(transformationMatrix != null, "Transformation matrix cannot be null");

        getHandle().setTransformation(new net.minecraft.util.math.AffineTransformation(transformationMatrix));
    }

    @Override
    public int getInterpolationDuration() {
        return getHandle().getInterpolationDuration();
    }

    @Override
    public void setInterpolationDuration(int duration) {
        getHandle().setInterpolationDuration(duration);
    }

    @Override
    public float getViewRange() {
        return getHandle().getViewRange();
    }

    @Override
    public void setViewRange(float range) {
        getHandle().setViewRange(range);
    }

    @Override
    public float getShadowRadius() {
        return getHandle().getShadowRadius();
    }

    @Override
    public void setShadowRadius(float radius) {
        getHandle().setShadowRadius(radius);
    }

    @Override
    public float getShadowStrength() {
        return getHandle().getShadowStrength();
    }

    @Override
    public void setShadowStrength(float strength) {
        getHandle().setShadowStrength(strength);
    }

    @Override
    public float getDisplayWidth() {
        return getHandle().getDisplayWidth();
    }

    @Override
    public void setDisplayWidth(float width) {
        getHandle().setDisplayWidth(width);
    }

    @Override
    public float getDisplayHeight() {
        return getHandle().getDisplayHeight();
    }

    @Override
    public void setDisplayHeight(float height) {
        getHandle().setDisplayHeight(height);
    }

    @Override
    public int getInterpolationDelay() {
        return getHandle().getStartInterpolation();
    }

    @Override
    public void setInterpolationDelay(int ticks) {
        getHandle().setStartInterpolation(ticks);
    }

    @Override
    public Billboard getBillboard() {
        return Billboard.valueOf(getHandle().getBillboardMode().name());
    }

    @Override
    public void setBillboard(Billboard billboard) {
        Preconditions.checkArgument(billboard != null, "Billboard cannot be null");

        getHandle().setBillboardMode(net.minecraft.entity.decoration.DisplayEntity.BillboardMode.valueOf(billboard.name()));
    }

    @Override
    public Color getGlowColorOverride() {
        int color = getHandle().getGlowColorOverride();

        return (color == -1) ? null : Color.fromARGB(color);
    }

    @Override
    public void setGlowColorOverride(Color color) {
        if (color == null) {
            getHandle().setGlowColorOverride(-1);
        } else {
            getHandle().setGlowColorOverride(color.asARGB());
        }
    }

    @Override
    public Brightness getBrightness() {
        net.minecraft.entity.decoration.Brightness nms = getHandle().getBrightnessUnpacked();

        return (nms != null) ? new Brightness(nms.comp_1240(), nms.comp_1241()) : null;
    }

    @Override
    public void setBrightness(Brightness brightness) {
        if (brightness != null) {
            getHandle().setBrightness(new net.minecraft.entity.decoration.Brightness(brightness.getBlockLight(), brightness.getSkyLight()));
        } else {
            getHandle().setBrightness(null);
        }
    }
}
