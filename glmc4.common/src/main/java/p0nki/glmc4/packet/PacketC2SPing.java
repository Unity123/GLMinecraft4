package p0nki.glmc4.packet;

import p0nki.glmc4.tag.Tag;

import java.util.Map;

public class PacketC2SPing extends Packet {

    private final long timeSent;

    public PacketC2SPing(long timeSent) {
        this.timeSent = timeSent;
    }

    public PacketC2SPing(Tag tag) {
        this.timeSent = tag.asMap().get("timeSent").asLong();
    }

    @Override
    public PacketID getID() {
        return PacketID.C2S_PING;
    }

    public long getTimeSent() {
        return timeSent;
    }

    @Override
    public Tag toTag() {
        return Tag.of(Map.of(
                "id", getID().toTag(),
                "timeSent", Tag.of(timeSent)
        ));
    }
}
