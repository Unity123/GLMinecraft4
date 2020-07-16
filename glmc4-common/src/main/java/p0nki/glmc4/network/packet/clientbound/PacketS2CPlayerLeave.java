package p0nki.glmc4.network.packet.clientbound;

import p0nki.glmc4.network.PacketReadBuf;
import p0nki.glmc4.network.PacketWriteBuf;
import p0nki.glmc4.network.packet.Packet;
import p0nki.glmc4.network.packet.PacketDirection;
import p0nki.glmc4.network.packet.PacketTypes;

import java.util.UUID;

public class PacketS2CPlayerLeave extends Packet<ClientPacketListener> {

    private UUID uuid;

    public PacketS2CPlayerLeave() {
        super(PacketDirection.SERVER_TO_CLIENT, PacketTypes.S2C_PLAYER_LEAVE);
    }

    public PacketS2CPlayerLeave(UUID uuid) {
        super(PacketDirection.SERVER_TO_CLIENT, PacketTypes.S2C_PLAYER_LEAVE);
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public void read(PacketReadBuf input) {
        uuid = input.readUuid();
    }

    @Override
    public void write(PacketWriteBuf output) {
        output.writeUuid(uuid);
    }

    @Override
    public void apply(ClientPacketListener listener) {
        listener.onPlayerLeave(this);
    }

}
