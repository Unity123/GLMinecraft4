package p0nki.glmc4.client;

import p0nki.glmc4.client.gl.GLUtils;
import p0nki.glmc4.client.gl.Window;
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

public class ClientInstance {

    private final ClientConfig config;
    private final Socket socket;
    private final IInputStream inputStream;
    private final IOutputStream outputStream;
    private final List<Packet> outgoingPackets = new ArrayList<>();
    private final List<PacketListener> packetListeners = new ArrayList<>();

    public void queueOutput(Packet packet) {
        synchronized (outgoingPackets) {
            outgoingPackets.add(packet);
        }
    }

    private List<PlayerMetadata> players;
    private PlayerMetadata yourPlayerMetadata = null;

    public ClientInstance(ClientConfig clientConfig) throws IOException {
        this.config = clientConfig;
        socket = new Socket(config.getHost(), config.getPort());
        inputStream = new ByteInputStream(socket.getInputStream());
        outputStream = new ByteOutputStream(socket.getOutputStream());
        packetListeners.add(new PacketAdapterS2C() {
            @Override
            public void onReceiveS2CDisconnect(PacketS2CDisconnect packet) {
                System.out.println("DISCONNECTED: " + packet.getReason());
                System.exit(1);
            }

            @Override
            public void onReceiveS2CChatMessage(PacketS2CChatMessage packet) {
                System.err.println(packet.getMessage().getFromUUID() + ": " + packet.getMessage().getMessage());
            }

            @Override
            public void onReceiveS2CInfoResponse(PacketS2CInfoResponse packet) {
                players = packet.getPlayers();
                if (yourPlayerMetadata != null && (!yourPlayerMetadata.getUuid().equals(packet.getYourPlayerMetadata().getUuid()) || !yourPlayerMetadata.getName().equals(packet.getYourPlayerMetadata().getName()))) {
                    throw new RuntimeException("INVALID INFO PACKET");
                }
                PlayerMetadata original = yourPlayerMetadata;
                yourPlayerMetadata = packet.getYourPlayerMetadata();
                if (original == null) {
                    System.out.println("your player ID = " + yourPlayerMetadata.getUuid());
                }
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
        startOpenGLThread();
    }

    private void startInputThread() {
        new Thread(() -> {
            while (true) {
                try {
                    Packet packet = Packet.read(inputStream);
                    if (packet == null) {
                        System.out.println("READ NULL PACKET");
                        System.exit(1);
                        return;
                    }
//                    System.out.println("READ " + packet.getID().toString());
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

    private void startOpenGLThread() throws IOException {
        Window.initialize();
        Window.setOpacity(0.5F);
        Window.setSize(500, 500);
        Window.setTitle("Your Player ID: " + yourPlayerMetadata.getUuid()+" - ignore this window pls kthx");
        while (!Window.shouldClose()) {
            GLUtils.clear();
            Window.endFrame();
        }
        socket.close();
        System.exit(0);
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
            for (Packet packet : outgoingPackets) {
                try {
                    Tag.write(outputStream, packet.toTag());
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
