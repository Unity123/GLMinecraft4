package p0nki.glmc4.packet;

import p0nki.glmc4.tag.Tag;

import java.util.function.Function;

public class PacketType<T extends Packet<T>> {

    private final String name;
    private final Function<Tag, T> fromTag;

    public PacketType(String name, Function<Tag, T> fromTag) {
        this.name = name;
        this.fromTag = fromTag;
    }

    @Override
    public String toString() {
        return "PacketType{" +
                "name='" + name + '\'' +
                ", fromTag=" + fromTag +
                '}';
    }

    public String getName() {
        return name;
    }

    public T fromTag(Tag tag) {
        return fromTag.apply(tag);
    }
}
