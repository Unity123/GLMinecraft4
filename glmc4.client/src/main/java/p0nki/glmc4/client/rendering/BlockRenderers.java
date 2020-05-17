package p0nki.glmc4.client.rendering;

import p0nki.glmc4.registry.Registry;

public class BlockRenderers {

    public static final Registry<BlockRenderer> REGISTRY = new Registry<>();

    public static final BlockRenderer AIR = new BlockRendererAir();
    public static final BlockRenderer GRASS = new BlockRendererGrass();
    public static final BlockRenderer DIRT = new BlockRendererDirt();
    public static final BlockRenderer STONE = new BlockRendererStone();

    static {
        REGISTRY.registerPlural(AIR, GRASS, DIRT, STONE);
    }

}
