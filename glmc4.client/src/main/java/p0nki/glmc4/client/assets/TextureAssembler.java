package p0nki.glmc4.client.assets;

import p0nki.glmc4.client.gl.Texture;
import p0nki.glmc4.utils.Identifier;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class TextureAssembler {

    private static Map<Identifier, File> listFiles(Identifier identifier, String directory) {
        File file = new File(directory);
        String[] sub = file.list();
        if (sub == null) return new HashMap<>();
        Map<Identifier, File> map = new HashMap<>();
        for (String s : sub) {
            File f = new File(directory + "/" + s);
            String identifierString = s;
            if (identifierString.indexOf(".") > 0)
                identifierString = identifierString.substring(0, identifierString.indexOf("."));
            Identifier nextIdentifier = new Identifier(identifier.getNamespace(), identifier.getPath() + "_" + identifierString);
            if (f.isFile()) map.put(nextIdentifier, f);
            else {
                map.putAll(listFiles(nextIdentifier, directory + "/" + s));
            }
        }
        return map;
    }

    private static class Image {
        int x;
        int y;
        int width;
        int height;
        int[][] data;
        String path;

        int area() {
            return width * height;
        }
    }

    private final Map<Identifier, File> identifiers;
    private final Map<Identifier, Image> images;

    private Image read(Identifier identifier) throws IOException {
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
        image.path = identifiers.get(identifier).getAbsolutePath();
        return image;
    }

    private final int width = 1024;
    private final int height = 1024;
    private int pixelCount = 0;

    private boolean canPlace(int[][] data, Identifier identifier, int x, int y) {
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

    private void place(int[][] data, Identifier identifier, int x, int y) {
        int w = images.get(identifier).width;
        int h = images.get(identifier).height;
        int[][] pixels = images.get(identifier).data;
        for (int i = x; i < x + w; i++) {
            for (int j = y; j < y + h; j++) {
                data[i][j] = pixels[i - x][y + h - j - 1];
            }
        }
        pixelCount += w * h;
        images.get(identifier).x = x;
        images.get(identifier).y = y;
    }

    private void place(int[][] data, Identifier identifier) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (canPlace(data, identifier, x, y)) {
                    place(data, identifier, x, y);
                    return;
                }
            }
        }
        throw new AssertionError(identifier);
    }

    private final String directory;

    private TextureAssembler(Identifier identifier, String directory) throws IOException {
        this.directory = directory;
        identifiers = listFiles(identifier, Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(directory)).getFile());
        images = new HashMap<>();
        for (Identifier s : identifiers.keySet()) {
            images.put(s, read(s));
        }
        int[][] data = new int[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                data[x][y] = -1;
            }
        }
        List<Identifier> keys = new ArrayList<>(identifiers.keySet());
        keys.sort((o1, o2) -> {
            int i = Integer.compare(images.get(o1).area(), images.get(o2).area());
            if (i == 0) return o1.toString().compareTo(o2.toString());
            else return -i;
        });
        for (Identifier key : keys) {
            place(data, key);
        }
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                img.setRGB(x, y, data[x][y]);
            }
        }
        ImageIO.write(img, "png", new LocalLocation("atlas/" + directory + ".png").getFile());
        PrintWriter writer = new PrintWriter(new LocalLocation("atlas/" + directory + ".txt").getFile());
        writer.println("-----------------\nIMAGES WRITTEN: " + keys.size() + "\nPIXELS AVAILABLE: " + width * height + "\nPIXELS USED: " + pixelCount + "\n-----------------");
        for (Identifier key : keys) {
            writer.printf("%-64s %-9s %s\n", key, images.get(key).width + "x" + images.get(key).height, images.get(key).path);
        }
        writer.close();
    }

    //    private static final Map<String, Texture> loadedTextures = new HashMap<>();
    private static final Map<Identifier, TextureAssembler> assemblers = new HashMap<>();
    private Texture texture = null;

    public Texture getTexture() throws FileNotFoundException {
        if (texture == null) texture = new Texture(new LocalLocation("atlas/" + directory + ".png"));
        return texture;
    }

    public static TextureAssembler getAssembler(Identifier identifier) {
        return assemblers.get(identifier);
    }

    public AtlasPosition get(Identifier identifier) {
        Image image = images.get(identifier);
        if (image == null) return null;
        return new AtlasPosition(image.x, image.y, image.width, image.height, width, height);
    }

//    public AtlasPosition get(Identifier identifier) {
//        return Utils.firstNonNull(attemptGet(identifier), attemptGet(identifier.appendPath(".png")));
//    }

    public static TextureAssembler assembleTextures(Identifier identifier, String directory) throws IOException {
        TextureAssembler assembler = new TextureAssembler(identifier, directory);
        assemblers.put(identifier, assembler);
        return assembler;
    }

}
