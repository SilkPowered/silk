package org.bukkit.craftbukkit.util;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.JsonParseException;
import cx.rain.silk.mixins.bridge.text.ITextColorBridge;
import cx.rain.silk.mixins.wrappers.text.TextStreamWrapper;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CraftChatMessage {

    private static final Pattern LINK_PATTERN = Pattern.compile("((?:(?:https?):\\/\\/)?(?:[-\\w_\\.]{2,}\\.[a-z]{2,4}.*?(?=[\\.\\?!,;:]?(?:[" + String.valueOf(ChatColor.COLOR_CHAR) + " \\n]|$))))");
    private static final Map<Character, Formatting> formatMap;

    static {
        Builder<Character, Formatting> builder = ImmutableMap.builder();
        for (Formatting format : Formatting.values()) {
            builder.put(Character.toLowerCase(format.toString().charAt(1)), format);
        }
        formatMap = builder.build();
    }

    public static Formatting getColor(ChatColor color) {
        return formatMap.get(color.getChar());
    }

    public static ChatColor getColor(Formatting format) {
        return ChatColor.getByChar(format.getCode());
    }

    private static final class StringMessage {
        private static final Pattern INCREMENTAL_PATTERN = Pattern.compile("(" + String.valueOf(ChatColor.COLOR_CHAR) + "[0-9a-fk-orx])|((?:(?:https?):\\/\\/)?(?:[-\\w_\\.]{2,}\\.[a-z]{2,4}.*?(?=[\\.\\?!,;:]?(?:[" + String.valueOf(ChatColor.COLOR_CHAR) + " \\n]|$))))|(\\n)", Pattern.CASE_INSENSITIVE);
        // Separate pattern with no group 3, new lines are part of previous string
        private static final Pattern INCREMENTAL_PATTERN_KEEP_NEWLINES = Pattern.compile("(" + String.valueOf(ChatColor.COLOR_CHAR) + "[0-9a-fk-orx])|((?:(?:https?):\\/\\/)?(?:[-\\w_\\.]{2,}\\.[a-z]{2,4}.*?(?=[\\.\\?!,;:]?(?:[" + String.valueOf(ChatColor.COLOR_CHAR) + " ]|$))))", Pattern.CASE_INSENSITIVE);
        // ChatColor.b does not explicitly reset, its more of empty
        private static final Style RESET = Style.EMPTY.withBold(false).withItalic(false).withUnderline(false).withStrikethrough(false).withObfuscated(false);

        private final List<Text> list = new ArrayList<>();
        private MutableText currentChatComponent = Text.empty();
        private Style modifier = Style.EMPTY;
        private final Text[] output;
        private int currentIndex;
        private StringBuilder hex;
        private final String message;

        private StringMessage(String message, boolean keepNewlines, boolean plain) {
            this.message = message;
            if (message == null) {
                output = new Text[]{currentChatComponent};
                return;
            }
            list.add(currentChatComponent);

            Matcher matcher = (keepNewlines ? INCREMENTAL_PATTERN_KEEP_NEWLINES : INCREMENTAL_PATTERN).matcher(message);
            String match = null;
            boolean needsAdd = false;
            while (matcher.find()) {
                int groupId = 0;
                while ((match = matcher.group(++groupId)) == null) {
                    // NOOP
                }
                int index = matcher.start(groupId);
                if (index > currentIndex) {
                    needsAdd = false;
                    appendNewComponent(index);
                }
                switch (groupId) {
                case 1:
                    char c = match.toLowerCase(java.util.Locale.ENGLISH).charAt(1);
                    Formatting format = formatMap.get(c);

                    if (c == 'x') {
                        hex = new StringBuilder("#");
                    } else if (hex != null) {
                        hex.append(c);

                        if (hex.length() == 7) {
                            modifier = RESET.withColor(TextColor.parse(hex.toString()));
                            hex = null;
                        }
                    } else if (format.isModifier() && format != Formatting.RESET) {
                        switch (format) {
                        case BOLD:
                            modifier = modifier.withBold(Boolean.TRUE);
                            break;
                        case ITALIC:
                            modifier = modifier.withItalic(Boolean.TRUE);
                            break;
                        case STRIKETHROUGH:
                            modifier = modifier.withStrikethrough(Boolean.TRUE);
                            break;
                        case UNDERLINE:
                            modifier = modifier.withUnderline(Boolean.TRUE);
                            break;
                        case OBFUSCATED:
                            modifier = modifier.withObfuscated(Boolean.TRUE);
                            break;
                        default:
                            throw new AssertionError("Unexpected message format");
                        }
                    } else { // Color resets formatting
                        modifier = RESET.withColor(format);
                    }
                    needsAdd = true;
                    break;
                case 2:
                    if (plain) {
                        appendNewComponent(matcher.end(groupId));
                    } else {
                        if (!(match.startsWith("http://") || match.startsWith("https://"))) {
                            match = "http://" + match;
                        }
                        modifier = modifier.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, match));
                        appendNewComponent(matcher.end(groupId));
                        modifier = modifier.withClickEvent(null);
                    }
                    break;
                case 3:
                    if (needsAdd) {
                        appendNewComponent(index);
                    }
                    currentChatComponent = null;
                    break;
                }
                currentIndex = matcher.end(groupId);
            }

            if (currentIndex < message.length() || needsAdd) {
                appendNewComponent(message.length());
            }

            output = list.toArray(new Text[list.size()]);
        }

        private void appendNewComponent(int index) {
            Text addition = Text.literal(message.substring(currentIndex, index)).setStyle(modifier);
            currentIndex = index;
            if (currentChatComponent == null) {
                currentChatComponent = Text.empty();
                list.add(currentChatComponent);
            }
            currentChatComponent.append(addition);
        }

        private Text[] getOutput() {
            return output;
        }
    }

    public static Text fromStringOrNull(String message) {
        return fromStringOrNull(message, false);
    }

    public static Text fromStringOrNull(String message, boolean keepNewlines) {
        return (message == null || message.isEmpty()) ? null : fromString(message, keepNewlines)[0];
    }

    public static Text[] fromString(String message) {
        return fromString(message, false);
    }

    public static Text[] fromString(String message, boolean keepNewlines) {
        return fromString(message, keepNewlines, false);
    }

    public static Text[] fromString(String message, boolean keepNewlines, boolean plain) {
        return new StringMessage(message, keepNewlines, plain).getOutput();
    }

    public static String toJSON(Text component) {
        return Text.Serializer.toJson(component);
    }

    public static String toJSONOrNull(Text component) {
        if (component == null) return null;
        return toJSON(component);
    }

    public static Text fromJSON(String jsonMessage) throws JsonParseException {
        // Note: This also parses plain Strings to text components.
        // Note: An empty message (empty, or only consisting of whitespace) results in null rather than a parse exception.
        return Text.Serializer.fromJson(jsonMessage);
    }

    public static Text fromJSONOrNull(String jsonMessage) {
        if (jsonMessage == null) return null;
        try {
            return fromJSON(jsonMessage); // Can return null
        } catch (JsonParseException ex) {
            return null;
        }
    }

    public static Text fromJSONOrString(String message) {
        return fromJSONOrString(message, false);
    }

    public static Text fromJSONOrString(String message, boolean keepNewlines) {
        return fromJSONOrString(message, false, keepNewlines);
    }

    private static Text fromJSONOrString(String message, boolean nullable, boolean keepNewlines) {
        if (message == null) message = "";
        if (nullable && message.isEmpty()) return null;
        Text component = fromJSONOrNull(message);
        if (component != null) {
            return component;
        } else {
            return fromString(message, keepNewlines)[0];
        }
    }

    public static String fromJSONOrStringToJSON(String message) {
        return fromJSONOrStringToJSON(message, false);
    }

    public static String fromJSONOrStringToJSON(String message, boolean keepNewlines) {
        return fromJSONOrStringToJSON(message, false, keepNewlines, Integer.MAX_VALUE, false);
    }

    public static String fromJSONOrStringOrNullToJSON(String message) {
        return fromJSONOrStringOrNullToJSON(message, false);
    }

    public static String fromJSONOrStringOrNullToJSON(String message, boolean keepNewlines) {
        return fromJSONOrStringToJSON(message, true, keepNewlines, Integer.MAX_VALUE, false);
    }

    public static String fromJSONOrStringToJSON(String message, boolean nullable, boolean keepNewlines, int maxLength, boolean checkJsonContentLength) {
        if (message == null) message = "";
        if (nullable && message.isEmpty()) return null;
        // If the input can be parsed as JSON, we use that:
        Text component = fromJSONOrNull(message);
        if (component != null) {
            if (checkJsonContentLength) {
                String content = fromComponent(component);
                String trimmedContent = trimMessage(content, maxLength);
                if (content != trimmedContent) { // identity comparison is fine here
                    // Note: The resulting text has all non-plain text features stripped.
                    return fromStringToJSON(trimmedContent, keepNewlines);
                }
            }
            return message;
        } else {
            // Else we interpret the input as legacy text:
            message = trimMessage(message, maxLength);
            return fromStringToJSON(message, keepNewlines);
        }
    }

    public static String trimMessage(String message, int maxLength) {
        if (message != null && message.length() > maxLength) {
            return message.substring(0, maxLength);
        } else {
            return message;
        }
    }

    public static String fromStringToJSON(String message) {
        return fromStringToJSON(message, false);
    }

    public static String fromStringToJSON(String message, boolean keepNewlines) {
        Text component = CraftChatMessage.fromString(message, keepNewlines)[0];
        return CraftChatMessage.toJSON(component);
    }

    public static String fromStringOrNullToJSON(String message) {
        Text component = CraftChatMessage.fromStringOrNull(message);
        return CraftChatMessage.toJSONOrNull(component);
    }

    public static String fromJSONComponent(String jsonMessage) {
        Text component = CraftChatMessage.fromJSONOrNull(jsonMessage);
        return CraftChatMessage.fromComponent(component);
    }

    public static String fromComponent(Text component) {
        if (component == null) return "";
        StringBuilder out = new StringBuilder();

        boolean hadFormat = false;
        for (Text c : new TextStreamWrapper(component)) {
            Style modi = c.getStyle();
            TextColor color = modi.getColor();
            if (c.getContent() != TextContent.EMPTY || color != null) {
                if (color != null) {
                    if (((ITextColorBridge) color).silk$getFormat() != null) {
                        out.append(((ITextColorBridge) color).silk$getFormat());
                    } else {
                        out.append(ChatColor.COLOR_CHAR).append("x");
                        for (char magic : color.getName().substring(1).toCharArray()) {
                            out.append(ChatColor.COLOR_CHAR).append(magic);
                        }
                    }
                    hadFormat = true;
                } else if (hadFormat) {
                    out.append(ChatColor.RESET);
                    hadFormat = false;
                }
            }
            if (modi.isBold()) {
                out.append(Formatting.BOLD);
                hadFormat = true;
            }
            if (modi.isItalic()) {
                out.append(Formatting.ITALIC);
                hadFormat = true;
            }
            if (modi.isUnderlined()) {
                out.append(Formatting.UNDERLINE);
                hadFormat = true;
            }
            if (modi.isStrikethrough()) {
                out.append(Formatting.STRIKETHROUGH);
                hadFormat = true;
            }
            if (modi.isObfuscated()) {
                out.append(Formatting.OBFUSCATED);
                hadFormat = true;
            }
            c.getContent().visit((x) -> {
                out.append(x);
                return Optional.empty();
            });
        }
        return out.toString();
    }

    public static Text fixComponent(MutableText component) {
        Matcher matcher = LINK_PATTERN.matcher("");
        return fixComponent(component, matcher);
    }

    private static Text fixComponent(MutableText component, Matcher matcher) {
        if (component.getContent() instanceof LiteralTextContent) {
            LiteralTextContent text = ((LiteralTextContent) component.getContent());
            String msg = text.string();
            if (matcher.reset(msg).find()) {
                matcher.reset();

                Style modifier = component.getStyle();
                List<Text> extras = new ArrayList<>();
                List<Text> extrasOld = new ArrayList<>(component.getSiblings());
                component = Text.empty();

                int pos = 0;
                while (matcher.find()) {
                    String match = matcher.group();

                    if (!(match.startsWith("http://") || match.startsWith("https://"))) {
                        match = "http://" + match;
                    }

                    MutableText prev = Text.literal(msg.substring(pos, matcher.start()));
                    prev.setStyle(modifier);
                    extras.add(prev);

                    MutableText link = Text.literal(matcher.group());
                    Style linkModi = modifier.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, match));
                    link.setStyle(linkModi);
                    extras.add(link);

                    pos = matcher.end();
                }

                MutableText prev = Text.literal(msg.substring(pos));
                prev.setStyle(modifier);
                extras.add(prev);
                extras.addAll(extrasOld);

                for (Text c : extras) {
                    component.append(c);
                }
            }
        }

        List<Text> extras = component.getSiblings();
        for (int i = 0; i < extras.size(); i++) {
            Text comp = extras.get(i);
            if (comp.getStyle() != null && comp.getStyle().getClickEvent() == null) {
                extras.set(i, fixComponent(comp.copy(), matcher));
            }
        }

        if (component.getContent() instanceof TranslatableTextContent) {
            Object[] subs = ((TranslatableTextContent) component.getContent()).getArgs();
            for (int i = 0; i < subs.length; i++) {
                Object comp = subs[i];
                if (comp instanceof Text) {
                    Text c = (Text) comp;
                    if (c.getStyle() != null && c.getStyle().getClickEvent() == null) {
                        subs[i] = fixComponent(c.copy(), matcher);
                    }
                } else if (comp instanceof String && matcher.reset((String) comp).find()) {
                    subs[i] = fixComponent(Text.literal((String) comp), matcher);
                }
            }
        }

        return component;
    }

    private CraftChatMessage() {
    }
}
