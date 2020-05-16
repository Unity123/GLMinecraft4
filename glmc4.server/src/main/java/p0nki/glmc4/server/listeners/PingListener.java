package p0nki.glmc4.server.listeners;

import p0nki.glmc4.packet.PacketAdapterC2S;
import p0nki.glmc4.packet.PacketC2SPing;
import p0nki.glmc4.server.Connection;

public class PingListener extends PacketAdapterC2S {

    private final Connection connection;

    public PingListener(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void onReceivePacketC2SPing(PacketC2SPing packet) {
        connection.lastPing = System.currentTimeMillis();
    }

}
