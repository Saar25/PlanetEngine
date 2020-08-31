package org.saar.core.renderer;

import org.saar.lwjgl.opengl.shaders.ShadersProgram;
import org.saar.lwjgl.opengl.shaders.uniforms.UniformProperty;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractRenderer implements Renderer {

    private final ShadersProgram shadersProgram;

    private final List<UniformProperty<?>> stageUniformProperties = new LinkedList<>();
    private final List<UniformProperty<?>> InstanceUniformProperties = new LinkedList<>();

    public AbstractRenderer(ShadersProgram shadersProgram) {
        this.shadersProgram = shadersProgram;
    }

    protected final void init() {
        for (UniformProperty<?> uniform : Renderers.findInstanceUniformProperties(this)) {
            addInstanceUniformProperty(uniform);
        }
        for (UniformProperty<?> uniform : Renderers.findStageUniformProperties(this)) {
            addStageUniformProperty(uniform);
        }
    }

    protected final void addStageUniformProperty(UniformProperty<?> uniform) {
        this.shadersProgram.bind();
        uniform.initialize(this.shadersProgram);
        this.stageUniformProperties.add(uniform);
    }

    protected final void addInstanceUniformProperty(UniformProperty<?> uniform) {
        this.shadersProgram.bind();
        uniform.initialize(this.shadersProgram);
        this.InstanceUniformProperties.add(uniform);
    }

    @Override
    public final void render() {
        shadersProgram.bind();

        this.onRender();

        shadersProgram.unbind();
    }

    @Override
    public final void delete() {
        this.onDelete();
        this.shadersProgram.delete();
    }

    protected void onRender() {
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
