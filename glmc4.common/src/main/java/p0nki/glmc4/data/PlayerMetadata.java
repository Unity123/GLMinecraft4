package p0nki.glmc4.data;

import p0nki.glmc4.tag.Tag;
import p0nki.glmc4.utils.TagSerializable;

import java.util.Map;

public class PlayerMetadata implements TagSerializable {

    private final String name;
    private final String uuid;

    public PlayerMetadata(String name, String uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    public PlayerMetadata(Tag tag) {
        name = tag.asMap().get("name").asString();
        uuid = tag.asMap().get("uuid").asString();
    }

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public Tag toTag() {
        return Tag.of(Map.of(
                "name", Tag.of(name),
                "uuid", Tag.of(uuid)
        ));
    }

}
