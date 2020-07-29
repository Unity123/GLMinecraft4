package p0nki.glmc4.wgen;

import java.util.Random;

public class RandomContext {

    private final long worldSeed;
    private final PerlinNoiseSampler noiseSampler;
    private long localSeed;

    public RandomContext(long seed, long salt) {
        worldSeed = addSalt(seed, salt);
        noiseSampler = new PerlinNoiseSampler(new Random(seed));
    }

    private static long addSalt(long seed, long salt) {
        long l = Utils.mixSeed(salt, salt);
        l = Utils.mixSeed(l, salt);
        l = Utils.mixSeed(l, salt);
        long m = Utils.mixSeed(seed, l);
        m = Utils.mixSeed(m, l);
        m = Utils.mixSeed(m, l);
        return m;
    }

    public int choose(int a, int b, int c, int d) {
        int i = nextInt(4);
        if (i == 0) return a;
        if (i == 1) return b;
        if (i == 2) return c;
        if (i == 3) return d;
        return -1;
    }

    public void initSeed(int x, int z) {
        long l = worldSeed;
        l = Utils.mixSeed(l, x);
        l = Utils.mixSeed(l, z);
        l = Utils.mixSeed(l, x);
        l = Utils.mixSeed(l, z);
        localSeed = l;
    }

    public int nextInt(int bound) {
        int i = (int) Math.floorMod(localSeed >> 24, (long) bound);
        localSeed = Utils.mixSeed(localSeed, worldSeed);
        return i;
    }

    public PerlinNoiseSampler getNoiseSampler() {
        return noiseSampler;
    }

}
