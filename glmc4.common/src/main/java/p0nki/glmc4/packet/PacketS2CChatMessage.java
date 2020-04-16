package p0nki.glmc4.packet;

import p0nki.glmc4.data.ChatMessage;
import p0nki.glmc4.tag.Tag;

import java.util.Map;

public class PacketS2CChatMessage extends Packet {

    private final ChatMessage message;

    public PacketS2CChatMessage(ChatMessage message) {
        this.message = message;
    }

    @Override
    public PacketID getID() {
        return PacketID.S2C_CHAT_MESSAGE;
    }

    public PacketS2CChatMessage(Tag tag){
        this.message=new ChatMessage(tag.asMap().get("message"));
    }

    public ChatMessage getMessage() {
        return message;
    }

    @Override
    public Tag toTag() {
        return Tag.of(Map.of(
                "id", getID().toTag(),
                "message", message.toTag()
        ));
    }
}
