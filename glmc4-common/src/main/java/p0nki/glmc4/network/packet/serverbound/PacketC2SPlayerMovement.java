package p0nki.glmc4.network.packet.serverbound;

import org.joml.Vector3f;
import p0nki.glmc4.network.PacketByteBuf;
import p0nki.glmc4.network.packet.Packet;
import p0nki.glmc4.network.packet.PacketTypes;

public class PacketC2SPlayerMovement extends Packet<ServerPacketListener> {

    private boolean forward;
    private boolean back;
    private boolean left;
    private boolean right;
    private Vector3f lookAt;

    public PacketC2SPlayerMovement() {
        super(PacketTypes.C2S_PLAYER_MOVEMENT);
    }

    public PacketC2SPlayerMovement(boolean forward, boolean back, boolean left, boolean right, Vector3f lookAt) {
        super(PacketTypes.C2S_PLAYER_MOVEMENT);
        this.forward = forward;
        this.back = back;
        this.left = left;
        this.right = right;
        this.lookAt = lookAt;
    }

    public boolean isForward() {
        return forward;
    }

    public boolean isBack() {
        return back;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public Vector3f getLookAt() {
        return lookAt;
    }

    @Override
    public void read(PacketByteBuf buf) {
        forward = buf.readBoolean();
        back = buf.readBoolean();
        left = buf.readBoolean();
        right = buf.readBoolean();
        lookAt = buf.read3f();
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeBoolean(forward);
        buf.writeBoolean(back);
        buf.writeBoolean(left);
        buf.writeBoolean(right);
        buf.write3f(lookAt);
    }

    @Override
    public void apply(ServerPacketListener listener) {
        listener.onPlayerMovement(this);
    }
}
