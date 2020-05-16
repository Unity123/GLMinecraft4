package p0nki.glmc4.registry;

import com.google.common.collect.ImmutableMap;
import p0nki.glmc4.block.Block;
import p0nki.glmc4.tag.Tag;
import p0nki.glmc4.tag.TagArray;
import p0nki.glmc4.tag.TagMap;
import p0nki.glmc4.utils.Identifiable;
import p0nki.glmc4.utils.Identifier;
import p0nki.glmc4.utils.TagSerializable;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

public class Registry<T extends Registrable<T> & Identifiable> implements TagSerializable {

    public class RegistryEntry {
        private final int rawID;
        private final Identifier key;
        private final T value;

        public RegistryEntry(int rawID, Identifier key, T value) {
            this.rawID = rawID;
            this.key = key;
            this.value = value;
        }

        public int getRawID() {
            return rawID;
        }

        public Identifier getKey() {
            return key;
        }

        public T getValue() {
            return value;
        }

    }

    private final List<RegistryEntry> entries;
    private final Map<Integer, RegistryEntry> rawIDMap;
    private final Map<Identifier, RegistryEntry> keyMap;
    private final Map<T, RegistryEntry> valueMap;
//    private boolean locked;

    public Registry() {
        entries = new ArrayList<>();
        rawIDMap = new HashMap<>();
        keyMap = new HashMap<>();
        valueMap = new HashMap<>();
//        locked = false;
    }

    public void register(@Nonnull T value) {
//        if (locked) throw new IllegalStateException();
        Objects.requireNonNull(value);
        int rawID = entries.size();
        Identifier key = value.getID();
        if (keyMap.containsKey(key)) {
            throw new IllegalArgumentException(key.toString());
        }
        RegistryEntry entry = new RegistryEntry(rawID, key, value);
        entries.add(entry);

        rawIDMap.put(rawID, entry);
        keyMap.put(key, entry);
        valueMap.put(value, entry);

    }

    public RegistryEntry get(int rawID) {
        return rawIDMap.get(rawID);
    }

    public RegistryEntry get(Identifier key) {
        return keyMap.get(key);
    }

    public RegistryEntry get(T value) {
        return valueMap.get(value);
    }

    @SafeVarargs
    public final void registerPlural(T... values) {
        for (T t : values) register(t);
    }

//    public void lock() {
//        locked = true;
//    }

    public boolean matches(Tag tag) {
        if (!tag.isArray()) throw new IllegalArgumentException();
        Tag[] otherEntries = tag.asArray();
        if (otherEntries.length != entries.size()) return false;
        for (int i = 0; i < otherEntries.length; i++) {
            Map<String, Tag> map = otherEntries[i].asMap();
            if (map.get("rawID").asInt() != entries.get(i).rawID) return false;
            if (!map.get("key").asIdentifier().equals(entries.get(i).key)) return false;
            if (!map.get("value").asIdentifier().equals(entries.get(i).value.getID())) return false;
        }
        return true;
    }

    @Override
    public Tag toTag() {
        return new TagArray(
                entries.stream().map(entry -> new TagMap(ImmutableMap.<String, Tag>builder()
                        .put("rawID", Tag.of(entry.rawID))
                        .put("key", Tag.of(entry.key))
                        .put("value", Tag.of(entry.value.getID()))
                        .build())).collect(Collectors.toList())
        );
    }

}
