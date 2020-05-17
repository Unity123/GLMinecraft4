package p0nki.glmc4.client.rendering;

import p0nki.glmc4.block.Block;
import p0nki.glmc4.client.gl.MeshData;
import p0nki.glmc4.registry.Registrable;
import p0nki.glmc4.registry.Registry;
import p0nki.glmc4.utils.Identifiable;
import p0nki.glmc4.utils.Identifier;
import p0nki.glmc4.utils.SixFaces;

import javax.annotation.Nonnull;

public abstract class BlockRenderer extends Registrable<BlockRenderer> implements Identifiable {

    @Override
    public Registry<BlockRenderer> getRegistry() {
        return BlockRenderers.REGISTRY;
    }

    public abstract Block getBlock();

    @Nonnull
    @Override
    public Identifier getID() {
        return getBlock().getID();
    }

    public abstract void mesh(MeshData data, SixFaces<Boolean, ?> visibility);

}
