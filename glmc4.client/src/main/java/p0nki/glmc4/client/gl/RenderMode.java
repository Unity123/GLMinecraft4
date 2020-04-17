package p0nki.glmc4.client.gl;

import org.lwjgl.opengl.GL41;

public enum RenderMode {

    TRIANGLES(GL41.GL_TRIANGLES),
    LINES(GL41.GL_LINES);

    private final int constant;

    RenderMode(int constant){
        this.constant=constant;
    }

    public int getConstant(){
        return constant;
    }

}
