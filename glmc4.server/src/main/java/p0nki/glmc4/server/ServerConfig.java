package p0nki.glmc4.server;

public class ServerConfig {

    private final int port;
    private final long tickLength;
    private final long maxPingDelay;

    public ServerConfig(int port,long tickLength,long maxPingDelay) {
        this.port = port;
        this.tickLength=tickLength;
        this.maxPingDelay=maxPingDelay;
    }

    public int getPort() {
        return port;
    }

    public long getTickLength() {
        return tickLength;
    }

    public long getMaxPingDelay() {
        return maxPingDelay;
    }
}
