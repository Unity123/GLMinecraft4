package p0nki.glmc4.data;

import p0nki.glmc4.tag.Tag;
import p0nki.glmc4.utils.TagSerializable;

import java.util.Map;

public class ChatMessage implements TagSerializable {

    private final String fromUUID;
    private final String message;

    public ChatMessage(String fromUUID, String message) {
        this.fromUUID = fromUUID;
        this.message = message;
    }

    public ChatMessage(Tag tag) {
        this.fromUUID = tag.asMap().get("fromUUID").asString();
        this.message = tag.asMap().get("message").asString();
    }

    public String getFromUUID() {
        return fromUUID;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public Tag toTag() {
        return Tag.of(Map.of(
                "fromUUID", Tag.of(fromUUID),
                "message", Tag.of(message)
        ));
    }
}
