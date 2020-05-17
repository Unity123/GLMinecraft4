package p0nki.glmc4.client.assets;

public class AtlasPosition {

    public int x;
    public int y;
    public int width;
    public int height;
    public int textureWidth;
    public int textureHeight;

    public double x0;
    public double y0;
    public double x1;
    public double y1;

    public AtlasPosition(int x, int y, int width, int height, int textureWidth, int textureHeight) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;

        x0 = (double) x / textureWidth;
        y0 = (double) y / textureHeight;
        x1 = (double) (x + width) / textureWidth;
        y1 = (double) (y + height) / textureHeight;
    }

}
