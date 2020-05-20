package p0nki.glmc4.packet;

import p0nki.glmc4.block.Chunk;
import p0nki.glmc4.tag.Tag;

public class PacketS2CLoadChunk extends Packet<PacketS2CLoadChunk> {

    private final long coordinate;
    private final Chunk data;

    public PacketS2CLoadChunk(long coordinate, Chunk data) {
        this.coordinate = coordinate;
        this.data = data;
    }

    public PacketS2CLoadChunk(Tag tag) {
        coordinate = tag.asMap().get("coordinate").asLong();
        data = new Chunk(tag.asMap().get("data").asByteArray());
    }

    public long getCoordinate() {
        return coordinate;
    }

    public Chunk getData() {
        return data;
    }

    @Override
    public PacketType<PacketS2CLoadChunk> getType() {
        return Packets.S2C_LOAD_CHUNK;
    }

    @Override
    public Tag toTag() {
        return Tag.of(Tag.mapBuilder()
                .put("coordinate", Tag.of(coordinate))
                .put("data", Tag.of(data.asByteArray()))
                .build());
    }
}
