package p0nki.glmc4.packet;

import p0nki.glmc4.tag.Tag;

public class PacketS2CUnloadChunk extends Packet<PacketS2CUnloadChunk> {

    private final long coordinate;

    public PacketS2CUnloadChunk(long coordinate) {
        this.coordinate = coordinate;
    }

    public PacketS2CUnloadChunk(Tag tag) {
        coordinate = tag.asMap().get("coordinate").asLong();
    }

    public long getCoordinate() {
        return coordinate;
    }

    @Override
    public PacketType<PacketS2CUnloadChunk> getType() {
        return Packets.S2C_UNLOAD_CHUNK;
    }

    @Override
    public Tag toTag() {
        return Tag.of(Tag.mapBuilder().put("coordinate", Tag.of(coordinate)));
    }
}
