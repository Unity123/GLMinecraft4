package p0nki.glmc4.server;

import p0nki.glmc4.data.PlayerMetadata;
import p0nki.glmc4.packet.Packet;
import p0nki.glmc4.packet.PacketListener;
import p0nki.glmc4.packet.PacketS2CDisconnect;
import p0nki.glmc4.server.listeners.ChatListener;
import p0nki.glmc4.server.listeners.PingListener;
import p0nki.glmc4.stream.ByteInputStream;
import p0nki.glmc4.stream.ByteOutputStream;
import p0nki.glmc4.stream.IInputStream;
import p0nki.glmc4.stream.IOutputStream;

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
    private final List<Packet<?>> outgoingPackets = new ArrayList<>();
    public long lastPing;
    private boolean isConnectionDead = false;
    private final List<PacketListener> packetListeners = new ArrayList<>();

    public PlayerMetadata getPlayerMetadata() {
        return playerMetadata;
    }

    public boolean isConnectionDead() {
        return isConnectionDead;
    }

    public void queuePacket(Packet<?> packet) {
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
        packetListeners.add(new PingListener(this));
        packetListeners.add(new ChatListener(this));
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
            Packet<?> packet = Packet.read(this.inputStream);
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
            for (Packet<?> packet : outgoingPackets) {
                try {
                    packet.write(outputStream);
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
