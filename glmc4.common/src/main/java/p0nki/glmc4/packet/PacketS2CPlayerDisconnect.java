package p0nki.glmc4.packet;

import com.google.common.collect.ImmutableMap;
import p0nki.glmc4.data.PlayerMetadata;
import p0nki.glmc4.tag.Tag;

public class PacketS2CPlayerDisconnect extends Packet<PacketS2CPlayerDisconnect> {

    private final PlayerMetadata disconnectedPlayer;

    public PacketS2CPlayerDisconnect(PlayerMetadata disconnectedPlayer) {
        this.disconnectedPlayer = disconnectedPlayer;
    }

    public PacketS2CPlayerDisconnect(Tag tag) {
        disconnectedPlayer = new PlayerMetadata(tag.asMap().get("disconnectedPlayer"));
    }

    public PlayerMetadata getDisconnectedPlayer() {
        return disconnectedPlayer;
    }

    @Override
    public PacketType<PacketS2CPlayerDisconnect> getType() {
        return Packets.S2C_PLAYER_DISCONNECT;
    }

    @Override
    public Tag toTag() {
        return Tag.of(ImmutableMap.<String, Tag>builder().put("disconnectedPlayer", disconnectedPlayer.toTag()).build());
    }
}
