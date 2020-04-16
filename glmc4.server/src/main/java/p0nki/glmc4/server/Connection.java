package p0nki.glmc4.server;

import p0nki.glmc4.data.ChatMessage;
import p0nki.glmc4.data.PlayerMetadata;
import p0nki.glmc4.packet.*;
import p0nki.glmc4.stream.ByteInputStream;
import p0nki.glmc4.stream.ByteOutputStream;
import p0nki.glmc4.stream.IInputStream;
import p0nki.glmc4.stream.IOutputStream;
import p0nki.glmc4.tag.Tag;

import java.io.IOException;
import java.net.Socket;
import java.nio.BufferUnderflowException;
import java.util.ArrayList;
import java.util.List;

public class Connection {

    private final Socket socket;
    private final IInputStream inputStream;
    private final IOutputStream outputStream;
    private final PlayerMetadata playerMetadata;
    private final List<Packet> outgoingPackets = new ArrayList<>();
    private long lastPing;
    private boolean isConnectionDead = false;
    private final List<PacketListener> packetListeners = new ArrayList<>();

    public PlayerMetadata getPlayerMetadata() {
        return playerMetadata;
    }

    public boolean isConnectionDead() {
        return isConnectionDead;
    }

    public void queuePacket(Packet packet) {
        synchronized (outgoingPackets) {
            outgoingPackets.add(packet);
        }
    }

    public Connection(Socket socket, PlayerMetadata playerMetadata) throws IOException {
        this.socket = socket;
        inputStream = new ByteInputStream(socket.getInputStream());
        outputStream = new ByteOutputStream(socket.getOutputStream());
        this.playerMetadata = playerMetadata;
        lastPing = System.currentTimeMillis();
        ServerInstance.INSTANCE.queueGlobalPacket(new PacketS2CChatMessage(new ChatMessage("SYSTEM", playerMetadata.getUuid() + " CONNECTED")));
        ServerInstance.INSTANCE.queueInfoResponse();
        packetListeners.add(new PacketAdapterC2S() {
            @Override
            public void onReceivePacketC2SChatMessage(PacketC2SChatMessage packet) {
                ServerInstance.INSTANCE.queueGlobalPacket(new PacketS2CChatMessage(new ChatMessage(playerMetadata.getUuid(), packet.getMessage())));
            }

            @Override
            public void onReceivePacketC2SPing(PacketC2SPing packet) {
                lastPing = System.currentTimeMillis();
            }
        });
        startThread();
    }

    private void startThread() {
        new Thread(() -> {
            while (true) {
                this.readInput();
            }
        }).start();
    }

    public void readInput() {
        try {
            Packet packet = Packet.read(this.inputStream);
            if (packet == null) {
                isConnectionDead = true;
                System.out.println("READ NULL PACKET");
                return;
            }
            for (PacketListener packetListener : packetListeners) {
                packetListener.onReceive(packet);
            }
        } catch (IOException | BufferUnderflowException e) {
            isConnectionDead = true;
        }
    }

    public void disconnect(String reason) {
        queuePacket(new PacketS2CDisconnect(reason));
        isConnectionDead = true;
    }

    public void flushOutput() {
        if (System.currentTimeMillis() - lastPing > ServerInstance.INSTANCE.getConfig().getMaxPingDelay()) {
            disconnect((System.currentTimeMillis() - lastPing) + "ms since last ping");
        }
        synchronized (outgoingPackets) {
            for (Packet packet : outgoingPackets) {
                try {
                    Tag.write(outputStream, packet.toTag());
                } catch (IOException | BufferUnderflowException e) {
                    isConnectionDead = true;
                }
            }
            outgoingPackets.clear();
        }
    }

    public void close() throws IOException {
        socket.close();
    }

}
