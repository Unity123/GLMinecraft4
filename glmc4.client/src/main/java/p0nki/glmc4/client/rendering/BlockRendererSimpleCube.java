package p0nki.glmc4.client.rendering;

import p0nki.glmc4.block.Direction;
import p0nki.glmc4.client.assets.AtlasPosition;
import p0nki.glmc4.client.assets.TextureAssembler;
import p0nki.glmc4.client.gl.MeshData;
import p0nki.glmc4.utils.Identifier;
import p0nki.glmc4.utils.Lazy;
import p0nki.glmc4.utils.SixFaces;

import java.util.List;

public abstract class BlockRendererSimpleCube extends BlockRenderer {

    private final SixFaces.Immutable<QuadTexture> faces;

    public BlockRendererSimpleCube(SixFaces.Immutable<QuadTexture> faces) {
        this.faces = faces;
    }

    private static final Lazy<TextureAssembler> ASSEMBLER_BLOCK = new Lazy<>(() -> TextureAssembler.getAssembler(new Identifier("minecraft:block")));

    private static List<Double> genUVs(TextureLayer layer) {
        AtlasPosition pos = ASSEMBLER_BLOCK.get().get(layer.identifier);
        return List.of(pos.x0, pos.y0, pos.x1, pos.y0, pos.x0, pos.y1, pos.x1, pos.y1);
    }

    private static List<Double> genColors(TextureLayer layer) {
        return List.of((double) layer.tint.x, (double) layer.tint.y, (double) layer.tint.z, (double) layer.tint.x, (double) layer.tint.y, (double) layer.tint.z, (double) layer.tint.x, (double) layer.tint.y, (double) layer.tint.z, (double) layer.tint.x, (double) layer.tint.y, (double) layer.tint.z);
    }

    private void meshSide(MeshData data, Direction direction) {
        int n = data.buffer(0).data.size() / 3;
        data.append(List.of(n, n + 1, n + 2, n + 1, n + 2, n + 3));
        data.buffer(0).append3f(List.of(
                direction.getStart(),
                direction.getStart().add(direction.getD0()),
                direction.getStart().add(direction.getD1()),
                direction.getStart().add(direction.getD0()).add(direction.getD1())
        ));
        data.buffer(1).append(genUVs(faces.get(direction).first));
        data.buffer(2).append(genColors(faces.get(direction).first));
        data.buffer(3).append(genUVs(faces.get(direction).second));
        data.buffer(4).append(genColors(faces.get(direction).second));
    }

    @Override
    public void mesh(MeshData data, SixFaces<Boolean, ?> visibility) {
        for (Direction direction : Direction.values()) {
            if (visibility.get(direction)) meshSide(data, direction);
        }
    }

}
