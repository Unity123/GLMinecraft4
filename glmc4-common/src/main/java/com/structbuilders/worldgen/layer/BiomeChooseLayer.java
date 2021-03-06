package com.structbuilders.worldgen.layer;

import com.structbuilders.worldgen.Biomes;
import com.structbuilders.worldgen.PerlinNoiseSampler;
import com.structbuilders.worldgen.RandomContext;

import java.util.Random;

public class BiomeChooseLayer implements InitLayer {

    private final static int WARM = Biomes.BADLANDS.id, COLD = Biomes.MOUNTAINS.id, NORMAL = Biomes.PLAINS.id;

    public BiomeChooseLayer(long seed) {
        this.seed = seed;
        int r = 200;
        points[0] = new point(-r, -r);
        points[1] = new point(-r, +r);
        points[2] = new point(+r, -r);
        points[3] = new point(+r, +r);
        Random rand = new Random(this.seed);
        int d = 500;
        final int[][] temps = new int[][]{
                {0, 1, 2, 3},
                {0, 1, 3, 2},
                {0, 2, 1, 3},
                {0, 2, 3, 1},
                {0, 3, 1, 2},
                {0, 3, 2, 1},
                {1, 0, 2, 3},
                {1, 0, 3, 2},
                {1, 2, 0, 3},
                {1, 2, 3, 0},
                {1, 3, 0, 2},
                {1, 3, 2, 0},
                {2, 0, 1, 3},
                {2, 0, 3, 1},
                {2, 1, 0, 3},
                {2, 1, 3, 0},
                {2, 3, 0, 1},
                {2, 3, 1, 0},
                {3, 0, 1, 2},
                {3, 0, 2, 1},
                {3, 1, 0, 2},
                {3, 1, 2, 0},
                {3, 2, 0, 1},
                {3, 2, 1, 0}
        };
        int indx = rand.nextInt(temps.length);
        for (int i = 0; i < 4; i++) {
            points[i].a += rand.nextInt(d) - d / 2;
            points[i].b += rand.nextInt(d) - d / 2;
            points[i].t = temps[indx][i];
            if (points[i].t == 0) points[i].t = Biomes.OCEAN.id;
            else if (points[i].t == 1) points[i].t = NORMAL;
            else if (points[i].t == 2) points[i].t = COLD;
            else if (points[i].t == 3) points[i].t = WARM;
        }
    }

    class point {
        int a, b, t;

        public point(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }

    point[] points = new point[4];

    private long seed;

    private double dist(int i, double x, double z) {
        return Math.sqrt((points[i].a - x) * (points[i].a - x) + (points[i].b - z) * (points[i].b - z));
    }

    @Override
    public int sample(RandomContext context, int x, int z) {
        int best = 0;
        double dx = x;
        double dz = z;
        PerlinNoiseSampler sampler = context.getNoiseSampler();
        double d = 40;
        double r = 50;
        dx += sampler.sample(x / d, z / d, -5, 0, 0) * r;
        dz += sampler.sample(x / d, z / d, 5, 0, 0) * r;
        for (int i = 1; i < 4; i++) {
            if (dist(i, dx, dz) < dist(best, dx, dz)) best = i;
        }
        if (points[best].t == 0) {
            best = (best + 1) % 4;
            for (int i = 0; i < 4; i++) {
                if (dist(i, dx, dz) < dist(best, dx, dz) && points[i].t != 0) best = i;
            }
            if (best == WARM) return Biomes.WARM_OCEAN.id;
            if (best == COLD) return Biomes.COLD_OCEAN.id;
            if (best == NORMAL) return Biomes.LUKEWARM_OCEAN.id;
            return Biomes.DEEP_OCEAN.id;
        }
        return points[best].t;
    }
}
