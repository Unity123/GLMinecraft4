package p0nki.glmc4.client.rendering;

import org.joml.Vector3f;
import p0nki.glmc4.utils.Identifier;

public class TextureLayer {

    public Identifier identifier;
    public Vector3f tint;

    public TextureLayer(Identifier identifier, Vector3f tint) {
        this.identifier = identifier;
        this.tint = tint;
    }
}
