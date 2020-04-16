package p0nki.glmc4.packet;

import p0nki.glmc4.tag.Tag;
import p0nki.glmc4.utils.TagSerializable;

public enum PacketID implements TagSerializable {

    C2S_PING("minecraft:c2s:ping", PacketType.C2S, PacketC2SPing.class),
    C2S_CHAT_MESSAGE("minecraft:c2s:chat_message", PacketType.C2S, PacketC2SChatMessage.class),

    S2C_INFO_RESPONSE("minecraft:s2c:info:response", PacketType.S2C, PacketS2CInfoResponse.class),
    S2C_DISCONNECT("minecraft:s2c:disconnect", PacketType.S2C, PacketS2CDisconnect.class),
    S2C_CHAT_MESSAGE("minecraft:s2c:chat_message", PacketType.S2C, PacketS2CChatMessage.class),
    ;

    private final String name;
    private final PacketType packetType;
    private final Class<? extends Packet> packetClass;

    PacketID(String name, PacketType packetType, Class<? extends Packet> packetClass) {
        this.name = name;
        this.packetType = packetType;
        this.packetClass = packetClass;
    }

    public Class<? extends Packet> getPacketClass() {
        return packetClass;
    }

    @Override
    public String toString() {
        return name;
    }

    public PacketType getPacketType() {
        return packetType;
    }

    public static PacketID from(Tag tag) {
        for (PacketID value : values()) {
            if (value.name.equals(tag.asString())) return value;
        }
        return null;
    }

    @Override
    public Tag toTag() {
        return Tag.of(name);
    }
}
