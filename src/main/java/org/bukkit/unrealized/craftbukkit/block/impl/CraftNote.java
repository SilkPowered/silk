/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.unrealized.craftbukkit.block.impl;

import org.bukkit.unrealized.Instrument;
import org.bukkit.unrealized.Note;
import org.bukkit.unrealized.block.data.Powerable;
import org.bukkit.unrealized.block.data.type.NoteBlock;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public final class CraftNote extends CraftBlockData implements NoteBlock, Powerable {

    public CraftNote() {
        super();
    }

    public CraftNote(net.minecraft.world.level.block.state.IBlockData state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.type.CraftNoteBlock

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> INSTRUMENT = getEnum(net.minecraft.world.level.block.BlockNote.class, "instrument");
    private static final net.minecraft.world.level.block.state.properties.BlockStateInteger NOTE = getInteger(net.minecraft.world.level.block.BlockNote.class, "note");

    @Override
    public Instrument getInstrument() {
        return get(INSTRUMENT, Instrument.class);
    }

    @Override
    public void setInstrument(Instrument instrument) {
        set(INSTRUMENT, instrument);
    }

    @Override
    public Note getNote() {
       return new Note(get(NOTE));
    }

    @Override
    public void setNote(Note note) {
        set(NOTE, (int) note.getId());
    }

    // org.bukkit.craftbukkit.block.data.CraftPowerable

    private static final net.minecraft.world.level.block.state.properties.BlockStateBoolean POWERED = getBoolean(net.minecraft.world.level.block.BlockNote.class, "powered");

    @Override
    public boolean isPowered() {
        return get(POWERED);
    }

    @Override
    public void setPowered(boolean powered) {
        set(POWERED, powered);
    }
}
