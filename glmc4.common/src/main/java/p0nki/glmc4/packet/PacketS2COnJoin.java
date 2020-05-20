package p0nki.glmc4.packet;

import p0nki.glmc4.data.PlayerMetadata;
import p0nki.glmc4.tag.Tag;
import p0nki.glmc4.utils.TagSerializable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PacketS2COnJoin extends Packet<PacketS2COnJoin> {

    private final PlayerMetadata playerMetadata;
    private final List<PlayerMetadata> players;

    public PacketS2COnJoin(PlayerMetadata playerMetadata, List<PlayerMetadata> players) {
        this.playerMetadata = playerMetadata;
        this.players = players;
    }

    public PacketS2COnJoin(Tag tag) {
        playerMetadata = new PlayerMetadata(tag.asMap().get("playerMetadata"));
        players = Arrays.stream(tag.asMap().get("players").asArray()).map(PlayerMetadata::new).collect(Collectors.toList());
    }

    @Override
    public PacketType<PacketS2COnJoin> getType() {
        return Packets.S2C_ON_JOIN;
    }

    public PlayerMetadata getPlayerMetadata() {
        return playerMetadata;
    }

    public List<PlayerMetadata> getPlayers() {
        return players;
    }

    @Override
    public Tag toTag() {
        return Tag.of(Tag.mapBuilder()
                .put("playerMetadata", playerMetadata.toTag())
                .put("players", Tag.of(players.stream().map(TagSerializable::toTag).collect(Collectors.toList())))
                .build());
    }
}
