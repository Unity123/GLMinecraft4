package p0nki.glmc4.packet;

import com.google.common.collect.ImmutableMap;
import p0nki.glmc4.block.Chunk;
import p0nki.glmc4.tag.Tag;

import java.nio.ByteBuffer;

public class PacketS2CChunk extends Packet<PacketS2CChunk> {

    private final int x;
    private final int y;
    private final int z;
    private final Chunk data;

    public PacketS2CChunk(int x, int y, int z, Chunk data) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.data = data;
    }

    public PacketS2CChunk(Tag tag) {
        this.x = tag.asMap().get("x").asInt();
        this.y = tag.asMap().get("y").asInt();
        this.z = tag.asMap().get("z").asInt();
        this.data = new Chunk(tag.asMap().get("data").asByteArray());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public Chunk getData() {
        return data;
    }

    @Override
    public PacketType<PacketS2CChunk> getType() {
        return Packets.S2C_CHUNK;
    }

    @Override
    public Tag toTag() {
        return Tag.of(ImmutableMap.<String, Tag>builder()
                .put("x", Tag.of(x))
                .put("y", Tag.of(y))
                .put("z", Tag.of(z))
                .put("data", Tag.of(data.asByteArray()))
                .build());
    }
}
