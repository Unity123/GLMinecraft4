package p0nki.glmc4.client.assets;

import p0nki.glmc4.client.gl.Texture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class TextureAssembler {

    private static Map<String, File> listFiles(String identifier, String directory) {
        File file = new File(directory);
        String[] sub = file.list();
        if (sub == null) return new HashMap<>();
        Map<String, File> map = new HashMap<>();
        for (String s : sub) {
            File f = new File(directory + "/" + s);
            if (f.isFile()) map.put(identifier + ":" + s, f);
            else {
                map.putAll(listFiles(identifier + ":" + s, directory + "/" + s));
            }
        }
        return map;
    }

    private static class Image {
        int width;
        int height;
        int[][] data;

        int area() {
            return width * height;
        }
    }

    private final Map<String, File> identifiers;
    private final Map<String, Image> images;

    private Image read(String identifier) throws IOException {
        BufferedImage img = ImageIO.read(identifiers.get(identifier));
        Image image = new Image();
        image.width = img.getWidth();
        image.height = img.getHeight();
        image.data = new int[img.getWidth()][img.getHeight()];
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                image.data[x][y] = img.getRGB(x, y);
            }
        }
        return image;
    }

    private final int width = 512;
    private final int height = 512;
    private int[][] data = new int[width][height];

    private boolean canPlace(String identifier, int x, int y) {
        int w = images.get(identifier).width;
        int h = images.get(identifier).height;
        for (int i = x; i < x + w; i++) {
            for (int j = y; j < y + h; j++) {
                if (i < 0 || j < 0 || i >= width || j >= height) return false;
                if (data[i][j] != -1) return false;
            }
        }
        return true;
    }

    private void place(String identifier, int x, int y) {
        int w = images.get(identifier).width;
        int h = images.get(identifier).height;
        int[][] pixels = images.get(identifier).data;
        for (int i = x; i < x + w; i++) {
            System.arraycopy(pixels[i - x], 0, data[i], y, h);
        }
    }

    private void place(String identifier) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (canPlace(identifier, x, y)) {
                    place(identifier, x, y);
                    return;
                }
            }
        }
        throw new AssertionError(identifier);
    }

    private TextureAssembler(String directory) throws IOException {
        identifiers = listFiles(directory, Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(directory)).getFile());
        images = new HashMap<>();
        for (String s : identifiers.keySet()) {
            images.put(s, read(s));
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                data[x][y] = -1;
            }
        }
        List<String> keys = new ArrayList<>(identifiers.keySet());
        keys.sort((o1, o2) -> {
            int i = Integer.compare(images.get(o1).area(), images.get(o2).area());
            if (i == 0) return o1.compareTo(o2);
            else return i;
        });
        for (String key : keys) {
            place(key);
        }
//        for (int x = 0; x < width; x++) {
//            for (int y = 0; y < height; y++) {
//                if (data[x][y] == -1) data[x][y] = Color.BLACK.getRGB();
//            }
//        }
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                img.setRGB(x, y, data[x][y]);
            }
        }
        ImageIO.write(img, "png", new File("RUN_FOLDER/atlas_" + directory + ".png"));
    }

    private static Map<String, Texture> loadedTextures = new HashMap<>();

    public static Texture getTexture(String directory) {
        if (!loadedTextures.containsKey(directory))
            loadedTextures.put(directory, new Texture("RUN_FOLDER/atlas_" + directory + ".png"));
        return loadedTextures.get(directory);
    }

    public static void assembleTextures(String directory) throws IOException {
        new TextureAssembler(directory);
    }

}
