package p0nki.glmc4.packet;

import p0nki.glmc4.tag.Tag;

public class PacketS2CDisconnect extends Packet<PacketS2CDisconnect> {

    private final String reason;

    public PacketS2CDisconnect(String reason) {
        this.reason = reason;
    }

    public PacketS2CDisconnect(Tag tag) {
        this.reason = tag.asMap().get("reason").asString();
    }

    @Override
    public PacketType<PacketS2CDisconnect> getType() {
        return Packets.S2C_DISCONNECT;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public Tag toTag() {
        return Tag.of(Tag.mapBuilder().put("reason", Tag.of(reason)));
    }
}
