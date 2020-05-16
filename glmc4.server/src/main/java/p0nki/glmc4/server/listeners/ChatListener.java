package p0nki.glmc4.server.listeners;

import p0nki.glmc4.data.ChatMessage;
import p0nki.glmc4.packet.PacketAdapterC2S;
import p0nki.glmc4.packet.PacketC2SChatMessage;
import p0nki.glmc4.packet.PacketS2CChatMessage;
import p0nki.glmc4.server.Connection;
import p0nki.glmc4.server.ServerInstance;

public class ChatListener extends PacketAdapterC2S {

    private final Connection connection;

    public ChatListener(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void onReceivePacketC2SChatMessage(PacketC2SChatMessage packet) {
        ServerInstance.INSTANCE.queueGlobalPacket(new PacketS2CChatMessage(new ChatMessage(connection.getPlayerMetadata().getUuid(), packet.getMessage())));
    }


}
