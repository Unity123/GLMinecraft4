package p0nki.glmc4.server;

import p0nki.glmc4.data.ChatMessage;
import p0nki.glmc4.data.PlayerMetadata;
import p0nki.glmc4.packet.Packet;
import p0nki.glmc4.packet.PacketS2CChatMessage;
import p0nki.glmc4.packet.PacketS2CPlayerList;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.logging.LogManager;
import java.util.stream.Collectors;

public class ServerInstance {

    public static ServerInstance INSTANCE;

    public void queueResendPlayerList() {
        synchronized (resendPlayerListLock) {
            resendPlayerList = true;
        }
    }

    public ServerConfig getConfig() {
        return config;
    }

    public void queueGlobalPacket(Packet packet) {
        synchronized (queuedGlobalPackets) {
            queuedGlobalPackets.add(packet);
        }
        if (packet instanceof PacketS2CChatMessage) {
            ChatMessage message = ((PacketS2CChatMessage) packet).getMessage();
            System.err.println(message.getFromUUID() + ": " + message.getMessage());
        }
    }

    public List<PlayerMetadata> getPlayers() {
        return players;
    }

    private final ServerConfig config;

    private final Object resendPlayerListLock = new Object();
    private boolean resendPlayerList = false;
    private final List<Packet> queuedGlobalPackets = new ArrayList<>();
    private List<PlayerMetadata> players = new ArrayList<>();
    private Map<String, Connection> connections = new HashMap<>();
    private final ServerSocket serverSocket;
    private Timer gameloopTimer;
    private Thread acceptorThread;
    private final Object addPlayersLock = new Object();

    public ServerInstance(ServerConfig serverConfig) throws IOException {
        INSTANCE = this;
        this.config = serverConfig;
        serverSocket = new ServerSocket(config.getPort());
        System.out.println("SERVER ON PORT " + config.getPort());
        initializeServer();
        startAcceptorThread();
        startGameLoopThread();
    }

    private void initializeServer() {

    }

    private PlayerMetadata createNewMetadata() {
        return new PlayerMetadata("PLAYERNAME", "" + (int) (10000 * Math.random()));
    }

    private void startAcceptorThread() {
        acceptorThread = new Thread(() -> {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    synchronized (addPlayersLock) {
                        PlayerMetadata metadata = createNewMetadata();
                        players.add(metadata);
                        connections.put(metadata.getUuid(), new Connection(socket, metadata));
                        queueGlobalPacket(new PacketS2CChatMessage(new ChatMessage("SYSTEM", metadata.getUuid() + " CONNECTED")));
                        queueResendPlayerList();
                    }
                } catch (IOException ignored) {

                }
            }
        });
        acceptorThread.setDaemon(true);
        acceptorThread.start();
    }

    private void iterateGameLoop() {
        synchronized (addPlayersLock) {
            List<String> removeUUIDs = new ArrayList<>();
            for (PlayerMetadata metadata : players) {
                connections.get(metadata.getUuid()).flushOutput();
                if (connections.get(metadata.getUuid()).isConnectionDead()) removeUUIDs.add(metadata.getUuid());
            }
            int startSize = players.size();
            players = players.stream().filter(playerMetadata -> !removeUUIDs.contains(playerMetadata.getUuid())).collect(Collectors.toList());
            int endSize = players.size();
            if (endSize != startSize) queueResendPlayerList();
            connections = connections.entrySet().stream().filter(entry -> {
                boolean b = !removeUUIDs.contains(entry.getKey());
                if (!b) {
                    try {
                        entry.getValue().close();
                        queueGlobalPacket(new PacketS2CChatMessage(new ChatMessage("SYSTEM", entry.getKey() + " DISCONNECTED")));
                    } catch (IOException ignored) {

                    }
                }
                return b;
            }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            synchronized (queuedGlobalPackets) {
                for (Packet packet : queuedGlobalPackets) {
                    for (Connection connection : connections.values()) {
                        connection.queuePacket(packet);
                    }
                }
                queuedGlobalPackets.clear();
            }
            synchronized (resendPlayerListLock) {
                if (resendPlayerList) {
                    resendPlayerList = false;
                    for (Connection connection : connections.values()) {
                        connection.queuePacket(new PacketS2CPlayerList(players, connection.getPlayerMetadata()));
                    }
                }
            }
        }
    }

    private void startGameLoopThread() {
        gameloopTimer = new Timer(false);
        gameloopTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                iterateGameLoop();
            }
        }, 0, config.getTickLength());
    }

    private void stop() throws IOException {//NOTE: this can be called inside iterateGameLoop() and it will exit gracefully according to javadoc
        gameloopTimer.cancel();
        acceptorThread.interrupt();
        serverSocket.close();
    }

}
