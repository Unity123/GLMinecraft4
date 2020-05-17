package p0nki.glmc4.client;

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
import java.util.*;
import java.util.stream.Collectors;

public class ClientInstance {

    private final ClientConfig config;
    private final Socket socket;
    private final IInputStream inputStream;
    private final IOutputStream outputStream;
    private final List<Packet<?>> outgoingPackets = new ArrayList<>();
    private final List<PacketListener> packetListeners = new ArrayList<>();

    public void queueOutput(Packet<?> packet) {
        synchronized (outgoingPackets) {
            outgoingPackets.add(packet);
        }
    }

    private List<PlayerMetadata> players = null;
    private PlayerMetadata yourPlayerMetadata = null;

    public ClientInstance(ClientConfig clientConfig) throws IOException {
        this.config = clientConfig;
        socket = new Socket(config.getHost(), config.getPort());
        inputStream = new ByteInputStream(socket.getInputStream());
        outputStream = new ByteOutputStream(socket.getOutputStream());
        packetListeners.add(new PacketAdapterS2C() {
            @Override
            public void onReceiveS2CDisconnect(PacketS2CDisconnect packet) {
                System.out.println("SERVER FORCED DISCONNECT. REASON: " + packet.getReason());
                System.exit(1);
            }

            @Override
            public void onReceiveS2CChatMessage(PacketS2CChatMessage packet) {
                System.err.println("CHAT MESSAGE PACKET " + packet.getMessage().getFromUUID() + ": " + packet.getMessage().getMessage());
            }

            @Override
            public void onReceiveS2COnJoin(PacketS2COnJoin packet) {
                yourPlayerMetadata = packet.getPlayerMetadata();
                players = packet.getPlayers();
                System.out.println("JOIN PACKET----\nYOU: " + yourPlayerMetadata.toString() + "\nALL ONLINE PLAYERS: " + players.stream().map(Object::toString).collect(Collectors.joining(", ")) + "\n-------");
            }

            @Override
            public void onReceiveS2CPlayerConnect(PacketS2CPlayerConnect packet) {
                System.out.println("PLAYER CONNECT PACKET: " + packet.getConnectedPlayer());
                players.add(packet.getConnectedPlayer());
            }

            @Override
            public void onReceiveS2CPlayerDisconnect(PacketS2CPlayerDisconnect packet) {
                System.out.println("PLAYER DISCONNECT PACKET: " + packet.getDisconnectedPlayer());
                players.remove(packet.getDisconnectedPlayer());
            }
        });
        startInputThread();
        startOutputThread();
        new Thread(() -> {
            Scanner sc = new Scanner(System.in);
            while (true) {
                try {
                    if (sc.hasNextLine()) {
                        queueOutput(new PacketC2SChatMessage(sc.nextLine()));
                    }
                } catch (NoSuchElementException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void startInputThread() {
        new Thread(() -> {
            while (true) {
                try {
                    Packet<?> packet = Packet.read(inputStream);
                    if (packet == null) {
                        System.out.println("READ NULL PACKET");
                        System.exit(1);
                        return;
                    }
                    for (PacketListener packetListener : packetListeners) {
                        packetListener.onReceive(packet);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }).start();
    }

    private void startOutputThread() {
        Timer t = new Timer(false);
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                flushOutput();
                queueOutput(new PacketC2SPing(System.currentTimeMillis()));

            }
        }, 0, 1);
    }

    private void flushOutput() {
        synchronized (outgoingPackets) {
            for (Packet<?> packet : outgoingPackets) {
                try {
                    packet.write(outputStream);
                } catch (IOException | BufferUnderflowException e) {
                    e.printStackTrace();
                    System.exit(1);
                    return;
                }
            }
            outgoingPackets.clear();
        }
    }

}
