package p0nki.glmc4.packet;

import p0nki.glmc4.tag.Tag;
import p0nki.glmc4.utils.TagSerializable;

public enum PacketID implements TagSerializable {

    C2S_PING("minecraft:c2s_ping", PacketType.C2S, PacketC2SPing.class),
    C2S_CHAT_MESSAGE("minecraft:c2s_chat_message", PacketType.C2S, PacketC2SChatMessage.class),

    S2C_ON_JOIN("minecraft:s2c_on_join", PacketType.S2C, PacketS2COnJoin.class),
    S2C_PLAYER_CONNECT("minecraft:s2c_player_connect", PacketType.S2C, PacketS2CPlayerConnect.class),
    S2C_PLAYER_DISCONNECT("minecraft:s2c_player_disconnect", PacketType.S2C, PacketS2CPlayerDisconnect.class),
    S2C_DISCONNECT("minecraft:s2c_disconnect", PacketType.S2C, PacketS2CDisconnect.class),
    S2C_CHAT_MESSAGE("minecraft:s2c_chat_message", PacketType.S2C, PacketS2CChatMessage.class),
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
