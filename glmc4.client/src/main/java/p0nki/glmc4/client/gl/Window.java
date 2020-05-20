package p0nki.glmc4.client.gl;

import org.joml.Vector2i;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;

public class Window {

    private Window() {

    }

    private static long pointer;

    public static void initialize() {
        if (!glfwInit()) {
            throw new AssertionError();
        }
        glfwSetErrorCallback(new GLFWErrorCallback() {
            @Override
            public void invoke(int error, long description) {
                System.err.println("GLFW error [" + Integer.toHexString(error) + "]: " + GLFWErrorCallback.getDescription(description));
                System.exit(1);
            }
        });
        GLFWErrorCallback.createPrint();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        pointer = glfwCreateWindow(500, 500, "Title", 0, 0);
        glfwMakeContextCurrent(pointer);
        GL.createCapabilities();
    }

    public static boolean isKeyDown(char key) {
        return glfwGetKey(pointer, key) == GLFW_PRESS;
    }

    public static void setTitle(String title) {
        glfwSetWindowTitle(pointer, title);
    }

    public static void setSize(int width, int height) {
        glfwSetWindowSize(pointer, width, height);
    }

    public static Vector2i getSize() {
        int[] w = new int[1];
        int[] h = new int[1];
        glfwGetWindowSize(pointer, w, h);
        return new Vector2i(w[0], h[0]);
    }

    public static Vector2i getFramebufferSize() {
        int[] w = new int[1];
        int[] h = new int[1];
        glfwGetFramebufferSize(pointer, w, h);
        return new Vector2i(w[0], h[0]);
    }

    public static boolean shouldClose() {
        return glfwWindowShouldClose(pointer);
    }

    public static void endFrame() {
        glfwSwapBuffers(pointer);
        glfwPollEvents();
    }

    public static void setOpacity(float opacity) {
        glfwSetWindowOpacity(pointer, opacity);
    }

}
