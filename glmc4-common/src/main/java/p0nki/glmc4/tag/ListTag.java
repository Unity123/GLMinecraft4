package p0nki.glmc4.tag;

import org.apache.commons.lang3.ArrayUtils;
import p0nki.glmc4.network.PacketByteBuf;
import p0nki.glmc4.utils.DataStreamUtils;

import java.util.AbstractList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListTag extends AbstractList<Tag> implements Tag {

    public static final TagReader<ListTag> READER = buf -> {
        int length = buf.readInt();
        Tag[] values = new Tag[length];
        for (int i = 0; i < length; i++) values[i] = DataStreamUtils.readTag(buf);
        return new ListTag(values);
    };

    private Tag[] values;

    private ListTag(Tag... values) {
        this.values = values;
    }

    public static ListTag of(Tag... values) {
        return new ListTag(values);
    }

    public static ListTag of(Stream<Tag> stream) {
        return new ListTag(stream.collect(Collectors.toList()).toArray(Tag[]::new));
    }

    @Override
    public Tag get(int index) {
        return values[index];
    }

    @Override
    public int size() {
        return values.length;
    }

    @Override
    public Tag set(int index, Tag element) {
        Tag original = values[index];
        values[index] = element;
        return original;
    }

    @Override
    public void add(int index, Tag element) {
        values = ArrayUtils.add(values, index, element);
    }

    @Override
    public Tag remove(int index) {
        Tag original = values[index];
        values = ArrayUtils.remove(values, index);
        return original;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeInt(values.length);
        for (Tag value : values) {
            DataStreamUtils.writeTag(buf, value);
        }
    }

    @Override
    public TagReader<ListTag> reader() {
        return READER;
    }

    @Override
    public byte type() {
        return LIST;
    }
}
