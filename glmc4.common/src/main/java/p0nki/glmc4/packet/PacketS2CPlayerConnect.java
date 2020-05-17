package p0nki.glmc4.packet;

import com.google.common.collect.ImmutableMap;
import p0nki.glmc4.data.PlayerMetadata;
import p0nki.glmc4.tag.Tag;

public class PacketS2CPlayerConnect extends Packet<PacketS2CPlayerConnect> {

    private final PlayerMetadata connectedPlayer;

    public PacketS2CPlayerConnect(PlayerMetadata connectedPlayer) {
        this.connectedPlayer = connectedPlayer;
    }

    public PacketS2CPlayerConnect(Tag tag) {
        connectedPlayer = new PlayerMetadata(tag.asMap().get("connectedPlayer"));
    }

    public PlayerMetadata getConnectedPlayer() {
        return connectedPlayer;
    }

    @Override
    public PacketType<PacketS2CPlayerConnect> getType() {
        return Packets.S2C_PLAYER_CONNECT;
    }

    @Override
    public Tag toTag() {
        return Tag.of(ImmutableMap.<String, Tag>builder().put("connectedPlayer", connectedPlayer.toTag()).build());
    }
}
