package p0nki.glmc4.block;

import org.joml.Vector2i;

import java.util.Optional;

public interface World {

    boolean isLoaded(Vector2i chunkPos);

    Optional<Chunk> getChunk(Vector2i chunkPos);



}
