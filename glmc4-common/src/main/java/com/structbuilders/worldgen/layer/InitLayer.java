package com.structbuilders.worldgen.layer;

import com.structbuilders.worldgen.RandomContext;

public interface InitLayer {

    default LayerFactory create(RandomContext context) {
        return () -> CachedLayerSampler.create((x, z) -> {
            context.initSeed(x, z);
            return sample(context, x, z);
        });
    }

    int sample(RandomContext context, int x, int z);

}
