package p0nki.glmc4.packet;

public class PacketAdapterS2C implements PacketListener {

    public void onReceiveS2CChatMessage(PacketS2CChatMessage packet) {

    }

    public void onReceiveS2CDisconnect(PacketS2CDisconnect packet) {

    }

    public void onReceiveS2CInfoResponse(PacketS2CInfoResponse packet) {

    }

    @Override
    public final void onReceive(Packet packet) {
        switch (packet.getID()) {
            case S2C_CHAT_MESSAGE:
                onReceiveS2CChatMessage((PacketS2CChatMessage) packet);
                break;
            case S2C_DISCONNECT:
                onReceiveS2CDisconnect((PacketS2CDisconnect) packet);
                break;
            case S2C_INFO_RESPONSE:
                onReceiveS2CInfoResponse((PacketS2CInfoResponse) packet);
                break;
            default:
                throw new UnsupportedOperationException(packet.getID().toString());
        }
    }

}
