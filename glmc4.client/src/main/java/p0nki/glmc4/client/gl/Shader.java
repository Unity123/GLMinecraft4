package p0nki.glmc4.client.gl;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import p0nki.glmc4.client.assets.ResourceLocation;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;

import static org.lwjgl.opengl.GL41.*;

public class Shader {

    protected final int id;

    private enum ShaderType {
        VERTEX,
        FRAGMENT,
        PROGRAM
    }

    private final String name;
    private boolean errored = false;

    private void checkCompile(int id, ShaderType type) {
        if (type == ShaderType.PROGRAM) {
            glLinkProgram(id);
            int[] success = new int[1];
            glGetProgramiv(id, GL_LINK_STATUS, success);
            if (success[0] == GL_FALSE) {
                String error = glGetProgramInfoLog(id);
                System.err.println("ERROR LINKING PROGRAM " + name + ":\n" + error);
                errored = true;
            }
        } else {
            glCompileShader(id);
            int[] success = new int[1];
            glGetShaderiv(id, GL_COMPILE_STATUS, success);
            if (success[0] == GL_FALSE) {
                String error = glGetShaderInfoLog(id);
                System.err.println("ERROR COMPILING SHADER " + name + "," + type.toString() + ":\n" + error);
                errored = true;
            }
        }
    }

    public Shader(String name) throws IOException {
        String vertCode = Files.readString(new ResourceLocation("shader/" + name + ".vert").getFile().toPath());
        String fragCode = Files.readString(new ResourceLocation("shader/" + name + ".frag").getFile().toPath());

        this.name = name;
        int vert = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vert, vertCode);
        checkCompile(vert, ShaderType.VERTEX);

        int frag = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(frag, fragCode);
        checkCompile(frag, ShaderType.FRAGMENT);

        id = glCreateProgram();
        glAttachShader(id, frag);
        glAttachShader(id, vert);
        checkCompile(id, ShaderType.PROGRAM);

        if (errored) throw new AssertionError();
    }

    public void setMatrix4f(String name, Matrix4f value) {
        FloatBuffer buf = BufferUtils.createFloatBuffer(16);
        buf.put(value.get(new float[16]));
        buf.flip();
        glUniformMatrix4fv(glGetUniformLocation(id, name), false, buf);
    }

    public void setVector3f(String name, Vector3f value) {
        glUniform3f(glGetUniformLocation(id, name), value.x, value.y, value.z);
    }

    public void setTexture(String name, Texture value, int unit) {
        glUniform1i(glGetUniformLocation(id, name), unit);
        value.bind(unit);
    }

    protected void bind() {
        glUseProgram(id);
    }

}
