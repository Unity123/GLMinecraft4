package p0nki.glmc4.packet;

import p0nki.glmc4.stream.IInputStream;
import p0nki.glmc4.tag.Tag;
import p0nki.glmc4.utils.TagSerializable;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public abstract class Packet implements TagSerializable {

    public abstract PacketID getID();

    public Packet() {

    }

    public Packet(Tag tag) {

    }

    public static Packet read(IInputStream inputStream) throws IOException {
        Tag tag = Tag.read(inputStream);
        PacketID id = PacketID.from(tag.asMap().get("id"));
        if (id == null) return null;
        try {
            return id.getPacketClass().getConstructor(Tag.class).newInstance(tag);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            System.err.println("NO CONSTRUCTOR FOR PACKET CLASS " + tag.asMap().get("id").prettyprint());
            return null;
        }
    }

}
