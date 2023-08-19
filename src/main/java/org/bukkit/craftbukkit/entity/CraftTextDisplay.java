package org.bukkit.craftbukkit.entity;

import com.google.common.base.Preconditions;
import net.minecraft.entity.decoration.DisplayEntity;
import org.bukkit.Color;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.util.CraftChatMessage;
import org.bukkit.entity.TextDisplay;

public class CraftTextDisplay extends CraftDisplay implements TextDisplay {

    public CraftTextDisplay(CraftServer server, net.minecraft.entity.decoration.DisplayEntity.TextDisplayEntity entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.entity.decoration.DisplayEntity.TextDisplayEntity getHandle() {
        return (net.minecraft.entity.decoration.DisplayEntity.TextDisplayEntity) super.getHandle();
    }

    @Override
    public String toString() {
        return "CraftTextDisplay";
    }

    @Override
    public String getText() {
        return CraftChatMessage.fromComponent(getHandle().getText());
    }

    @Override
    public void setText(String text) {
        getHandle().setText(CraftChatMessage.fromString(text, true)[0]);
    }

    @Override
    public int getLineWidth() {
        return getHandle().getLineWidth();
    }

    @Override
    public void setLineWidth(int width) {
        getHandle().getEntityData().set(DisplayEntity.TextDisplayEntity.DATA_LINE_WIDTH_ID, width);
    }

    @Override
    public Color getBackgroundColor() {
        int color = getHandle().getBackgroundColor();

        return (color == -1) ? null : Color.fromARGB(color);
    }

    @Override
    public void setBackgroundColor(Color color) {
        if (color == null) {
            getHandle().getEntityData().set(DisplayEntity.TextDisplayEntity.DATA_BACKGROUND_COLOR_ID, -1);
        } else {
            getHandle().getEntityData().set(DisplayEntity.TextDisplayEntity.DATA_BACKGROUND_COLOR_ID, color.asARGB());
        }
    }

    @Override
    public byte getTextOpacity() {
        return getHandle().getTextOpacity();
    }

    @Override
    public void setTextOpacity(byte opacity) {
        getHandle().setTextOpacity(opacity);
    }

    @Override
    public boolean isShadowed() {
        return getFlag(DisplayEntity.TextDisplayEntity.FLAG_SHADOW);
    }

    @Override
    public void setShadowed(boolean shadow) {
        setFlag(DisplayEntity.TextDisplayEntity.FLAG_SHADOW, shadow);
    }

    @Override
    public boolean isSeeThrough() {
        return getFlag(DisplayEntity.TextDisplayEntity.FLAG_SEE_THROUGH);
    }

    @Override
    public void setSeeThrough(boolean seeThrough) {
        setFlag(DisplayEntity.TextDisplayEntity.FLAG_SEE_THROUGH, seeThrough);
    }

    @Override
    public boolean isDefaultBackground() {
        return getFlag(DisplayEntity.TextDisplayEntity.FLAG_USE_DEFAULT_BACKGROUND);
    }

    @Override
    public void setDefaultBackground(boolean defaultBackground) {
        setFlag(DisplayEntity.TextDisplayEntity.FLAG_USE_DEFAULT_BACKGROUND, defaultBackground);
    }

    @Override
    public TextAlignment getAlignment() {
        DisplayEntity.TextDisplayEntity.TextAlignment nms = DisplayEntity.TextDisplayEntity.getAlign(getHandle().getFlags());
        return TextAlignment.valueOf(nms.name());
    }

    @Override
    public void setAlignment(TextAlignment alignment) {
        Preconditions.checkArgument(alignment != null, "Alignment cannot be null");

        switch (alignment) {
            case LEFT:
                setFlag(DisplayEntity.TextDisplayEntity.FLAG_ALIGN_LEFT, true);
                setFlag(DisplayEntity.TextDisplayEntity.FLAG_ALIGN_RIGHT, false);
                break;
            case RIGHT:
                setFlag(DisplayEntity.TextDisplayEntity.FLAG_ALIGN_LEFT, false);
                setFlag(DisplayEntity.TextDisplayEntity.FLAG_ALIGN_RIGHT, true);
                break;
            case CENTER:
                setFlag(DisplayEntity.TextDisplayEntity.FLAG_ALIGN_LEFT, false);
                setFlag(DisplayEntity.TextDisplayEntity.FLAG_ALIGN_RIGHT, false);
                break;
            default:
                throw new IllegalArgumentException("Unknown alignment " + alignment);
        }
    }

    private boolean getFlag(int flag) {
        return (getHandle().getFlags() & flag) != 0;
    }

    private void setFlag(int flag, boolean set) {
        byte flagBits = getHandle().getFlags();

        if (set) {
            flagBits |= flag;
        } else {
            flagBits &= ~flag;
        }

        getHandle().setFlags(flagBits);
    }
}
