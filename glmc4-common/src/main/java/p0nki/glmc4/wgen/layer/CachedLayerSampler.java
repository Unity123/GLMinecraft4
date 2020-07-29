package p0nki.glmc4.wgen.layer;

import p0nki.glmc4.wgen.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CachedLayerSampler implements LayerSampler {
    private final int cacheCapacity;
    private final List<Long> coords;
    private final Map<Long, Integer> cache;
    private final LayerSampler sampler;

    public CachedLayerSampler(int cacheCapacity, LayerSampler sampler) {
        this.cacheCapacity = cacheCapacity;
        this.sampler = sampler;
        coords = new ArrayList<>();
        cache = new HashMap<>();
    }

    public static CachedLayerSampler create(LayerSampler sampler) {
        return new CachedLayerSampler(25, sampler);
    }

    @Override
    public int sample(int x, int z) {
        long l = Utils.hash(x, z);
        if (cache.containsKey(l)) return cache.get(l);
        int i = sampler.sample(x, z);
        cache.put(l, i);
        coords.add(l);
        if (cache.size() >= cacheCapacity) {
            cache.remove(coords.get(0));
            coords.remove(0);
        }
        return i;
    }
}