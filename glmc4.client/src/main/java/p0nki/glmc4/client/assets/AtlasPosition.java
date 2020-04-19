package p0nki.glmc4.client.assets;

public class AtlasPosition {

    public int x, y, width, height, textureWidth, textureHeight;

    public float x0, y0, x1, y1;

    public AtlasPosition(int x, int y, int width, int height, int textureWidth, int textureHeight) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;

        x0 = (float) x / textureWidth;
        y0 = (float) y / textureHeight;
        x1 = (float) (x + width) / textureWidth;
        y1 = (float) (y + height) / textureHeight;
    }

}
