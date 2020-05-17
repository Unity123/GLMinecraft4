package p0nki.glmc4.client.rendering;

public class QuadTexture {

    public TextureLayer first;
    public TextureLayer second;

    public QuadTexture(TextureLayer first){
        this(first,TextureLayer.NONE);
    }

    public QuadTexture(TextureLayer first, TextureLayer second) {
        this.first = first;
        this.second = second;
    }
}
