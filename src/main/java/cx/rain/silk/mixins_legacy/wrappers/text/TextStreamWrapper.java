package cx.rain.silk.mixins_legacy.wrappers.text;

import com.google.common.collect.Streams;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.stream.Stream;

public class TextStreamWrapper implements Iterable<Text> {
    private Text text;

    public TextStreamWrapper(Text text) {
        this.text = text;
    }

    public Stream<Text> stream() {
        return Streams.concat(Stream.of(text), text.getSiblings().stream().flatMap(t -> new TextStreamWrapper(t).stream()));
    }

    @NotNull
    @Override
    public Iterator<Text> iterator() {
        return stream().iterator();
    }
}
