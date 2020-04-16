package p0nki.glmc4.packet;

public class PacketAdapterC2S implements PacketListener {

    public void onReceivePacketC2SPing(PacketC2SPing packet) {

    }

    public void onReceivePacketC2SChatMessage(PacketC2SChatMessage packet) {

    }

    @Override
    public final void onReceive(Packet packet) {
        switch (packet.getID()) {
            case C2S_PING:
                onReceivePacketC2SPing((PacketC2SPing) packet);
                break;
            case C2S_CHAT_MESSAGE:
                onReceivePacketC2SChatMessage((PacketC2SChatMessage) packet);
                break;
            default:
                throw new UnsupportedOperationException(packet.getID().toString());
        }
    }

}
