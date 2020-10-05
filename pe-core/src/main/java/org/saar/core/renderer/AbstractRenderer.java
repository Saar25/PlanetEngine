package org.saar.core.renderer;

import org.saar.lwjgl.opengl.shaders.ShadersProgram;
import org.saar.lwjgl.opengl.shaders.uniforms.Uniform;

public abstract class AbstractRenderer implements Renderer {

    private final ShadersProgram shadersProgram;

    public AbstractRenderer(ShadersProgram shadersProgram) {
        this.shadersProgram = shadersProgram;
    }

    protected final void init() {
        this.shadersProgram.bind();
        for (Uniform uniform : Renderers.findUniforms(this)) {
            uniform.initialize(this.shadersProgram);
        }
    }

    @Override
    public final void render(RenderContext context) {
        shadersProgram.bind();

        this.onRender(context);

        shadersProgram.unbind();
    }

    @Override
    public final void delete() {
        this.onDelete();
        this.shadersProgram.delete();
    }

    protected void onRender(RenderContext context) {
    }

    protected void onDelete() {
    }

}
