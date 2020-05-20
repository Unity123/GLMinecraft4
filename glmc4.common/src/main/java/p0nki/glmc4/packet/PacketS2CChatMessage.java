package p0nki.glmc4.packet;

import p0nki.glmc4.data.ChatMessage;
import p0nki.glmc4.tag.Tag;

public class PacketS2CChatMessage extends Packet<PacketS2CChatMessage> {

    private final ChatMessage message;

    public PacketS2CChatMessage(ChatMessage message) {
        this.message = message;
    }

    public PacketS2CChatMessage(Tag tag) {
        this.message = new ChatMessage(tag.asMap().get("message"));
    }

    @Override
    public PacketType<PacketS2CChatMessage> getType() {
        return Packets.S2C_CHAT_MESSAGE;
    }

    public ChatMessage getMessage() {
        return message;
    }

    @Override
    public Tag toTag() {
        return Tag.of(Tag.mapBuilder().put("message", message.toTag()));
    }
}
