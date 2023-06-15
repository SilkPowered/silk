package org.bukkit.craftbukkit.util;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class CraftNBTTagConfigSerializer {

    private static final Pattern ARRAY = Pattern.compile("^\\[.*]");
    private static final Pattern INTEGER = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)?i", Pattern.CASE_INSENSITIVE);
    private static final Pattern DOUBLE = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?d", Pattern.CASE_INSENSITIVE);
    private static final StringNbtReader MOJANGSON_PARSER = new StringNbtReader(new StringReader(""));

    public static Object serialize(NbtElement base) {
        if (base instanceof NbtCompound) {
            Map<String, Object> innerMap = new HashMap<>();
            for (String key : ((NbtCompound) base).getKeys()) {
                innerMap.put(key, serialize(((NbtCompound) base).get(key)));
            }

            return innerMap;
        } else if (base instanceof NbtList) {
            List<Object> baseList = new ArrayList<>();
            for (int i = 0; i < ((NbtList) base).size(); i++) {
                baseList.add(serialize(((NbtList) base).get(i)));
            }

            return baseList;
        } else if (base instanceof NbtString) {
            return base.asString();
        } else if (base instanceof NbtInt) { // No need to check for doubles, those are covered by the double itself
            return base + "i";
        }

        return base.toString();
    }

    public static NbtElement deserialize(Object object) {
        if (object instanceof Map) {
            NbtCompound compound = new NbtCompound();
            for (Map.Entry<String, Object> entry : ((Map<String, Object>) object).entrySet()) {
                compound.put(entry.getKey(), deserialize(entry.getValue()));
            }

            return compound;
        } else if (object instanceof List) {
            List<Object> list = (List<Object>) object;
            if (list.isEmpty()) {
                return new NbtList(); // Default
            }

            NbtList tagList = new NbtList();
            for (Object tag : list) {
                tagList.add(deserialize(tag));
            }

            return tagList;
        } else if (object instanceof String) {
            String string = (String) object;

            if (ARRAY.matcher(string).matches()) {
                try {
                    return new StringNbtReader(new StringReader(string)).parseElementPrimitiveArray();
                } catch (CommandSyntaxException e) {
                    throw new RuntimeException("Could not deserialize found list ", e);
                }
            } else if (INTEGER.matcher(string).matches()) { //Read integers on our own
                return NbtInt.of(Integer.parseInt(string.substring(0, string.length() - 1)));
            } else if (DOUBLE.matcher(string).matches()) {
                return NbtDouble.of(Double.parseDouble(string.substring(0, string.length() - 1)));
            } else {
                NbtElement nbtBase = MOJANGSON_PARSER.parsePrimitive(string);

                if (nbtBase instanceof NbtInt) { // If this returns an integer, it did not use our method from above
                    return NbtString.of(nbtBase.asString()); // It then is a string that was falsely read as an int
                } else if (nbtBase instanceof NbtDouble) {
                    return NbtString.of(String.valueOf(((NbtDouble) nbtBase).doubleValue())); // Doubles add "d" at the end
                } else {
                    return nbtBase;
                }
            }
        }

        throw new RuntimeException("Could not deserialize NBTBase");
    }
}
