package p0nki.glmc4.packet;

import p0nki.glmc4.tag.Tag;

import java.util.Map;

public class PacketS2CDisconnect extends Packet {

    private final String reason;

    public PacketS2CDisconnect(String reason) {
        this.reason = reason;
    }

    public PacketS2CDisconnect(Tag tag) {
        this.reason = tag.asMap().get("reason").asString();
    }

    public String getReason() {
        return reason;
    }

    @Override
    public Tag toTag() {
        return Tag.of(Map.of(
                "id", getID().toTag(),
                "reason", Tag.of(reason)
        ));
    }

    @Override
    public PacketID getID() {
        return PacketID.S2C_DISCONNECT;
    }
}
