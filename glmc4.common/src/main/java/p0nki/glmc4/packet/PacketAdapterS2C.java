package p0nki.glmc4.packet;

//import static p0nki.glmc4.packet.Packets.*;

public class PacketAdapterS2C implements PacketListener {

    public void onReceiveS2CChatMessage(PacketS2CChatMessage packet) {

    }

    public void onReceiveS2CDisconnect(PacketS2CDisconnect packet) {

    }

    public void onReceiveS2COnJoin(PacketS2COnJoin packet) {

    }

    public void onReceiveS2CPlayerConnect(PacketS2CPlayerConnect packet) {

    }

    public void onReceiveS2CPlayerDisconnect(PacketS2CPlayerDisconnect packet) {

    }

    public void onReceiveS2CLoadChunk(PacketS2CLoadChunk packet) {

    }

    public void onReceiveS2CUnloadChunk(PacketS2CUnloadChunk packet) {

    }

    @Override
    public final void onReceive(Packet<?> packet) {
        PacketType<?> type = packet.getType();
        if (type == Packets.S2C_CHAT_MESSAGE) {
            onReceiveS2CChatMessage((PacketS2CChatMessage) packet);
        } else if (type == Packets.S2C_DISCONNECT) {
            onReceiveS2CDisconnect((PacketS2CDisconnect) packet);
        } else if (type == Packets.S2C_ON_JOIN) {
            onReceiveS2COnJoin((PacketS2COnJoin) packet);
        } else if (type == Packets.S2C_PLAYER_CONNECT) {
            onReceiveS2CPlayerConnect((PacketS2CPlayerConnect) packet);
        } else if (type == Packets.S2C_PLAYER_DISCONNECT) {
            onReceiveS2CPlayerDisconnect((PacketS2CPlayerDisconnect) packet);
        } else if (type == Packets.S2C_LOAD_CHUNK) {
            onReceiveS2CLoadChunk((PacketS2CLoadChunk) packet);
        } else if (type == Packets.S2C_UNLOAD_CHUNK) {
            onReceiveS2CUnloadChunk((PacketS2CUnloadChunk) packet);
        } else {
            throw new UnsupportedOperationException(type.getName());
        }
    }

}
