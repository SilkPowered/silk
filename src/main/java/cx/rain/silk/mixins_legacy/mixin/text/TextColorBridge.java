package cx.rain.silk.mixins_legacy.mixin.text;

import cx.rain.silk.mixins_legacy.bridge.text.ITextColorBridge;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Map;
import java.util.stream.Collectors;

@Mixin(TextColor.class)
public class TextColorBridge implements ITextColorBridge {
    private static final Map<TextColor, Formatting> COLOR_TO_FORMAT = TextColor.FORMATTING_TO_COLOR.entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

    @Override
    public Formatting silk$getFormat() {
        return COLOR_TO_FORMAT.get((TextColor) (Object) this);
    }
}
