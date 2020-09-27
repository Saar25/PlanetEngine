package org.saar.core.renderer;

import org.saar.lwjgl.opengl.shaders.ShadersProgram;
import org.saar.lwjgl.opengl.shaders.uniforms.UniformProperty;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractRenderer implements Renderer {

    private final ShadersProgram shadersProgram;

    private final List<UniformProperty<?>> uniformProperties = new LinkedList<>();

    public AbstractRenderer(ShadersProgram shadersProgram) {
        this.shadersProgram = shadersProgram;
    }

    protected final void init() {
        for (UniformProperty<?> uniform : Renderers.findUniformProperties(this)) {
            addUniformProperty(uniform);
        }
    }

    protected final void addUniformProperty(UniformProperty<?> uniform) {
        this.shadersProgram.bind();
        uniform.initialize(this.shadersProgram);
        this.uniformProperties.add(uniform);
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

/*    protected final void updatePerRenderUniforms(RenderState<?> state) {
        for (UniformProperty<?> uniform : stageUniformProperties) {
            uniform.load(state);
        }
    }

    protected final void updatePerInstanceUniforms(RenderState<?> state) {
        for (UniformProperty<?> uniform : InstanceUniformProperties) {
            uniform.load(state);
        }
    }*/

}
