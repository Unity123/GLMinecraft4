package p0nki.glmc4.block;

import p0nki.glmc4.registry.Registry;

public class Blocks {

    public static final Registry<Block> REGISTRY = new Registry<>();

    public static final Block GRASS = new BlockGrass();
    public static final Block DIRT = new BlockDirt();
    public static final Block STONE = new BlockStone();

    static {
        REGISTRY.registerPlural(GRASS, DIRT, STONE);
    }

}
