package p0nki.glmc4.client.gl;

import org.lwjgl.opengl.GL41;

public class Utils {

    public static void clear() {
        GL41.glClearColor(0.2F, 0.5F, 0.8F, 1);
        GL41.glClear(GL41.GL_DEPTH_BUFFER_BIT | GL41.GL_COLOR_BUFFER_BIT);
    }

}
