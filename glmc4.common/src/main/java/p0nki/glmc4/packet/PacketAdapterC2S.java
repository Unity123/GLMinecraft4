package p0nki.glmc4.packet;

public class PacketAdapterC2S implements PacketListener {

    public void onReceivePacketC2SChatMessage(PacketC2SChatMessage packet) {

    }

    public void onReceivePacketC2SPing(PacketC2SPing packet) {

    }

    @Override
    public final void onReceive(Packet<?> packet) {
        PacketType<?> type = packet.getType();
        if (type == Packets.C2S_CHAT_MESSAGE) {
            onReceivePacketC2SChatMessage((PacketC2SChatMessage) packet);
        } else if (type == Packets.C2S_PING) {
            onReceivePacketC2SPing((PacketC2SPing) packet);
        } else {
            throw new IllegalArgumentException("Bad type: " + type.toString());
        }
    }

}
