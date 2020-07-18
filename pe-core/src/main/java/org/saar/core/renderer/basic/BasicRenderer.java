package org.saar.core.renderer.basic;

import org.saar.core.renderer.Renderer;
import org.saar.lwjgl.opengl.shaders.Shader;
import org.saar.lwjgl.opengl.shaders.ShadersProgram;

public class BasicRenderer implements Renderer {

    private static final String vertex = "shaders/basic/vertex.glsl";
    private static final String fragment = "shaders/basic/fragment.glsl";

    private final BasicRenderNode renderNode;
    private final ShadersProgram shadersProgram;

    public BasicRenderer(BasicRenderNode renderNode) throws Exception {
        this.shadersProgram = ShadersProgram.create(
                Shader.createVertex(vertex),
                Shader.createFragment(fragment));
        this.renderNode = renderNode;

        this.shadersProgram.bindAttributes("in_position", "in_colour");
    }

    @Override
    public void render() {
        this.shadersProgram.bind();

        this.renderNode.getModel().draw();

        this.shadersProgram.unbind();
    }

    @Override
    public void delete() {
        this.shadersProgram.delete();
    }
}
