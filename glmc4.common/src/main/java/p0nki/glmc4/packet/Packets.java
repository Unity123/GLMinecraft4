package p0nki.glmc4.packet;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class Packets {

    public static final PacketType<PacketC2SChatMessage> C2S_CHAT_MESSAGE = new PacketType<>("C2S_CHAT_MESSAGE", PacketC2SChatMessage::new);
    public static final PacketType<PacketC2SPing> C2S_PING = new PacketType<>("C2S_PING", PacketC2SPing::new);

    public static final PacketType<PacketS2CChatMessage> S2C_CHAT_MESSAGE = new PacketType<>("S2C_CHAT_MESSAGE", PacketS2CChatMessage::new);
    public static final PacketType<PacketS2CDisconnect> S2C_DISCONNECT = new PacketType<>("S2C_DISCONNECT", PacketS2CDisconnect::new);
    public static final PacketType<PacketS2COnJoin> S2C_ON_JOIN = new PacketType<>("S2C_ON_JOIN", PacketS2COnJoin::new);
    public static final PacketType<PacketS2CPlayerConnect> S2C_PLAYER_CONNECT = new PacketType<>("S2C_PLAYER_CONNECT", PacketS2CPlayerConnect::new);
    public static final PacketType<PacketS2CPlayerDisconnect> S2C_PLAYER_DISCONNECT = new PacketType<>("S2C_PLAYER_DISCONNECT", PacketS2CPlayerDisconnect::new);
    public static final PacketType<PacketS2CLoadChunk> S2C_LOAD_CHUNK = new PacketType<>("S2C_LOAD_CHUNK", PacketS2CLoadChunk::new);
    public static final PacketType<PacketS2CUnloadChunk> S2C_UNLOAD_CHUNK = new PacketType<>("S2C_UNLOAD_CHUNK", PacketS2CUnloadChunk::new);

    private static final Map<String, PacketType<?>> PACKET_TYPES = ImmutableMap.<String, PacketType<?>>builder()
            .put("C2S_CHAT_MESSAGE", C2S_CHAT_MESSAGE)
            .put("C2S_PING", C2S_PING)
            .put("S2C_CHAT_MESSAGE", S2C_CHAT_MESSAGE)
            .put("S2C_DISCONNECT", S2C_DISCONNECT)
            .put("S2C_ON_JOIN", S2C_ON_JOIN)
            .put("S2C_PLAYER_CONNECT", S2C_PLAYER_CONNECT)
            .put("S2C_PLAYER_DISCONNECT", S2C_PLAYER_DISCONNECT)
            .put("S2C_LOAD_CHUNK", S2C_LOAD_CHUNK)
            .put("S2C_UNLOAD_CHUNK", S2C_UNLOAD_CHUNK)
            .build();

    public static boolean has(String name) {
        return PACKET_TYPES.containsKey(name);
    }

    public static PacketType<?> get(String name) {
        return PACKET_TYPES.get(name);
    }

}
