package cx.rain.silk.mixins.mixin.text;

import com.google.common.collect.ImmutableMap;
import cx.rain.silk.mixins.bridge.text.ITextColorBridge;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Map;
import java.util.stream.Collectors;

@Mixin(TextColor.class)
public class TextColorBridge extends TextColor implements ITextColorBridge {
    private static final Map<TextColor, Formatting> COLOR_TO_FORMAT = TextColor.FORMATTING_TO_COLOR.entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

    public TextColorBridge(int rgb) {
        super(rgb);
    }

    @Override
    public Formatting silk$getFormat() {
        return COLOR_TO_FORMAT.get((TextColor) (Object) this);
    }
}
