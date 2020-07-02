package p0nki.glmc4.tag;

import p0nki.glmc4.utils.DataStreamUtils;

import javax.annotation.Nonnull;
import java.io.DataOutput;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CompoundTag extends AbstractMap<String, Tag<?>> implements Tag<CompoundTag>, ToTag<CompoundTag> {

    public static final TagReader<CompoundTag> READER = input -> {
        int size = input.readInt();
        Map<String, Tag<?>> values = new HashMap<>();
        for (int i = 0; i < size; i++) {
            values.put(DataStreamUtils.readString(input), DataStreamUtils.readTag(input));
        }
        return new CompoundTag(values);
    };

    private final Map<String, Tag<?>> values;

    public CompoundTag() {
        values = new HashMap<>();
    }

    public CompoundTag(Map<String, Tag<?>> values) {
        this.values = new HashMap<>(values);
    }

    public CompoundTag insert(String key, Object value) {
        values.put(key, Tag.of(value));
        return this;
    }

    public ByteArrayTag getByteArray(String key) {
        return (ByteArrayTag) values.get(key);
    }

    public byte getByte(String key) {
        return ((ByteTag) values.get(key)).get();
    }

    public CompoundTag getCompound(String key) {
        return (CompoundTag) values.get(key);
    }

    public IntArrayTag getIntArray(String key) {
        return (IntArrayTag) values.get(key);
    }

    public int getInt(String key) {
        return ((IntTag) values.get(key)).get();
    }

    public ListTag getList(String key) {
        return (ListTag) values.get(key);
    }

    public LongArrayTag getLongArray(String key) {
        return (LongArrayTag) values.get(key);
    }

    public long getLong(String key) {
        return ((LongTag) values.get(key)).get();
    }

    public String getString(String key) {
        return ((StringTag) values.get(key)).get();
    }

    @Override
    public Tag<?> put(String key, Tag<?> value) {
        Tag<?> original = values.getOrDefault(key, null);
        values.put(key, value);
        return original;
    }

    @Override
    @Nonnull
    public Set<Entry<String, Tag<?>>> entrySet() {
        return values.entrySet();
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeInt(values.size());
        for (Entry<String, Tag<?>> entry : values.entrySet()) {
            DataStreamUtils.writeString(output, entry.getKey());
            DataStreamUtils.writeTag(output, entry.getValue());
        }
    }

    @Override
    public TagReader<CompoundTag> reader() {
        return READER;
    }

    @Override
    public byte type() {
        return COMPOUND;
    }

}