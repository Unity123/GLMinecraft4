package p0nki.glmc4.client.gl;

import static org.lwjgl.glfw.GLFW.*;

public class Window {

    private final long pointer;

    public Window() {
        pointer = glfwCreateWindow(500, 500, "Title", 0, 0);
    }

    public void setTitle(String title) {
        glfwSetWindowTitle(pointer, title);
    }

    public void setSize(int width, int height) {
        glfwSetWindowSize(pointer, width, height);
    }

}
