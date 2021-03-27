package org.saar.core.renderer;

import org.saar.core.renderer.shaders.ShadersHelper;
import org.saar.lwjgl.opengl.shaders.Shader;
import org.saar.lwjgl.opengl.shaders.ShaderCompileException;
import org.saar.lwjgl.opengl.shaders.ShadersProgram;
import org.saar.lwjgl.opengl.shaders.uniforms.Uniform;

public abstract class AbstractRenderer implements Renderer {

    private ShadersProgram shadersProgram;

    public AbstractRenderer(ShadersProgram shadersProgram) {
        this.shadersProgram = shadersProgram;
    }

    public AbstractRenderer() {
    }

    protected void buildShadersProgram() throws ShaderCompileException {
        ShadersHelper helper = ShadersHelper.empty();
        for (Shader shader : Renderers.findVertexShaders(this)) {
            helper = helper.addShader(shader);
        }
        for (Shader shader : Renderers.findFragmentShaders(this)) {
            helper = helper.addShader(shader);
        }

        this.shadersProgram = helper.createProgram();
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

        this.preRender(context);
        this.onRender(context);
        this.postRender(context);

        shadersProgram.unbind();
    }

    @Override
    public final void delete() {
        this.onDelete();
        this.shadersProgram.delete();
    }

    protected void preRender(RenderContext context) {
    }

    protected void onRender(RenderContext context) {
    }

    protected void postRender(RenderContext context) {
    }

    protected void onDelete() {
    }

}
