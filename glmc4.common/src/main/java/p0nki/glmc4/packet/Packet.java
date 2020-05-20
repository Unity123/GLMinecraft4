package p0nki.glmc4.packet;

import com.google.common.collect.ImmutableMap;
import p0nki.glmc4.stream.IInputStream;
import p0nki.glmc4.stream.IOutputStream;
import p0nki.glmc4.tag.Tag;
import p0nki.glmc4.utils.TagSerializable;

import java.io.IOException;

public abstract class Packet<T extends Packet<T>> implements TagSerializable {

    public Packet() {

    }

    public Packet(Tag tag) {

    }

    public static Packet<?> read(IInputStream inputStream) throws IOException {
        Tag tag = Tag.read(inputStream);
        if (!tag.isMap()) return null;
        String name = tag.asMap().get("name").asString();
        if (Packets.has(name)) {
            return Packets.get(name).fromTag(tag.asMap().get("data"));
        }
        System.err.println("UNKNOWN PACKET TYPE: " + name);
        return null;
    }

    public abstract PacketType<T> getType();

    public final void write(IOutputStream outputStream) throws IOException {
        Tag.write(outputStream, Tag.of(ImmutableMap.<String, Tag>builder().put("name", Tag.of(getType().getName())).put("data", toTag()).build()));
    }

}
