package org.bukkit.unrealized.craftbukkit.block.data.type;

import org.bukkit.unrealized.Instrument;
import org.bukkit.unrealized.Note;
import org.bukkit.unrealized.block.data.type.NoteBlock;
import org.bukkit.unrealized.craftbukkit.block.data.CraftBlockData;

public abstract class CraftNoteBlock extends CraftBlockData implements NoteBlock {

    private static final net.minecraft.world.level.block.state.properties.BlockStateEnum<?> INSTRUMENT = getEnum("instrument");
    private static final net.minecraft.world.level.block.state.properties.BlockStateInteger NOTE = getInteger("note");

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
}
