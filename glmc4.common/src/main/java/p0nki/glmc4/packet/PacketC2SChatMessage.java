package p0nki.glmc4.packet;

import p0nki.glmc4.tag.Tag;

import java.util.Map;

public class PacketC2SChatMessage extends Packet {

    private final String message;

    public PacketC2SChatMessage(String message) {
        this.message = message;
    }

    public PacketC2SChatMessage(Tag tag) {
        this.message = tag.asMap().get("message").asString();
    }

    @Override
    public PacketID getID() {
        return PacketID.C2S_CHAT_MESSAGE;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public Tag toTag() {
        return Tag.of(Map.of(
                "id", getID().toTag(),
                "message", Tag.of(message)
        ));
    }
}
