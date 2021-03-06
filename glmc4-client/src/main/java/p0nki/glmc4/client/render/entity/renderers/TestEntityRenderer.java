package p0nki.glmc4.client.render.entity.renderers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import p0nki.glmc4.client.GLMC4Client;
import p0nki.glmc4.client.gl.Mesh;
import p0nki.glmc4.client.gl.Shader;
import p0nki.glmc4.client.gl.Texture;
import p0nki.glmc4.client.render.WorldRenderContext;
import p0nki.glmc4.client.render.entity.EntityRenderer;
import p0nki.glmc4.entity.TestEntity;

import java.nio.file.Path;

public class TestEntityRenderer extends EntityRenderer<TestEntity> {

    private static final Logger LOGGER = LogManager.getLogger();

    private Texture texture;
    private Shader shader;
    private Mesh head;
    private Mesh body;

    @Override
    public void initialize() {
        texture = new Texture(Path.of(GLMC4Client.resourcePath("entity"), "cow.png"));
        shader = Shader.create("entity");

        head = new Mesh(createCube(0, 14, 8, 8, 6, 64, 32, false).mult4(0, new Matrix4f().scale(8, 8, 6).scale(2.0F / 8.0F)));
        body = new Mesh(createCube(18, 32, 12, 18, 10, 64, 32, false).mult4(0, new Matrix4f().scale(12, 18, 10).scale(3.0F / 18.0F)));
    }

    @Override
    protected void renderType(WorldRenderContext context, TestEntity entity, Vector3f extrapolatedPosition) {
        shader.use();
        shader.set(context);
        shader.setMat4f("model", new Matrix4f().translate(extrapolatedPosition));
        shader.set3f("color", entity.getColor());
        shader.setTexture("tex", texture, 0);
        body.triangles();
    }

}
