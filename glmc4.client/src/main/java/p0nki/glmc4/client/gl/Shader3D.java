package p0nki.glmc4.client.gl;

import java.io.IOException;
import java.net.URISyntaxException;

public class Shader3D extends Shader {

    public Shader3D(String name) throws IOException, URISyntaxException {
        super(name);
    }

    public void bind(RenderContext ctx) {
        bind();
        setMatrix4f("perspective", ctx.getPerspective());
        setMatrix4f("view", ctx.getView());
    }

}
