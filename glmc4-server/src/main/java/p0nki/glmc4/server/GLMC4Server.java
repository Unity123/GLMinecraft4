package p0nki.glmc4.server;

import p0nki.glmc4.network.ClientConnection;
import p0nki.glmc4.network.packet.NetworkProtocol;
import p0nki.glmc4.network.packet.PacketType;
import p0nki.glmc4.network.packet.serverbound.ServerPacketListener;

import java.io.IOException;
import java.net.ServerSocket;

public class GLMC4Server {

    public static void main(String[] args) throws IOException {
        new MinecraftServer();
        ServerSocket serverSocket = new ServerSocket(3333);
        System.out.println("Listening on port 3333");
        NetworkProtocol networkProtocol = new NetworkProtocol();
        while (true) {
            ClientConnection<ServerPacketListener> connection = new ClientConnection<>(serverSocket.accept(), networkProtocol, PacketType.SERVERBOUND, PacketType.CLIENTBOUND);
            ServerPacketListener packetListener = new ServerPacketListener(connection);
            connection.setPacketListener(packetListener);
            MinecraftServer.INSTANCE.joinPlayer(connection);
            connection.startLoop();
        }
    }

}