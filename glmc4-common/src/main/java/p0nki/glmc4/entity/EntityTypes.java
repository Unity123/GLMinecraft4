package p0nki.glmc4.entity;

import org.joml.Vector3f;
import p0nki.glmc4.registry.Registry;
import p0nki.glmc4.tag.CompoundTag;
import p0nki.glmc4.utils.Identifier;

public class EntityTypes {

    public static final Registry<EntityType<?>> REGISTRY = new Registry<>();

    public static final EntityType<PlayerEntity> PLAYER = new EntityType<>(1.75F, new Vector3f(0.6F, 1.8F, 0.6F), PlayerEntity::new);
    public static final EntityType<TestEntity> TEST = new EntityType<>(0.5F, new Vector3f(1, 1, 1), TestEntity::new);

    public static Entity from(CompoundTag tag) {
        Identifier type = tag.getIdentifier("type");
        if (REGISTRY.hasKey(type)) {
            return REGISTRY.get(type).getValue().create(tag);
        }
        throw new IllegalArgumentException("Illegal entity type " + type);
    }

    public static void initialize() {
        REGISTRY.register(new Identifier("minecraft:player"), PLAYER);
        REGISTRY.register(new Identifier("minecraft:test"), TEST);
    }

}
