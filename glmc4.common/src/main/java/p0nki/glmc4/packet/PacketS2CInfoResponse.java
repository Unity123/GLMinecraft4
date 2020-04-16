package p0nki.glmc4.packet;

import p0nki.glmc4.data.PlayerMetadata;
import p0nki.glmc4.tag.Tag;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PacketS2CInfoResponse extends Packet {

    private final List<PlayerMetadata> players;
    private final PlayerMetadata yourPlayerMetadata;

    public PacketS2CInfoResponse(List<PlayerMetadata> players, PlayerMetadata yourPlayerMetadata) {
        this.players = players;
        this.yourPlayerMetadata = yourPlayerMetadata;
    }

    public PacketS2CInfoResponse(Tag tag) {
        this.players = Arrays.stream(tag.asMap().get("players").asArray()).map(PlayerMetadata::new).collect(Collectors.toList());
        this.yourPlayerMetadata = new PlayerMetadata(tag.asMap().get("yourPlayerMetadata"));
    }

    public PlayerMetadata getYourPlayerMetadata() {
        return yourPlayerMetadata;
    }

    public List<PlayerMetadata> getPlayers() {
        return players;
    }

    @Override
    public PacketID getID() {
        return PacketID.S2C_INFO_RESPONSE;
    }

    @Override
    public Tag toTag() {
        return Tag.of(Map.of(
                "id", getID().toTag(),
                "players", Tag.of(players.stream().map(PlayerMetadata::toTag)),
                "yourPlayerMetadata", yourPlayerMetadata.toTag()
        ));
    }
}
