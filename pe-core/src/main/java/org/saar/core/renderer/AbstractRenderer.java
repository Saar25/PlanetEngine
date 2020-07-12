package org.saar.core.renderer;

import org.saar.lwjgl.opengl.shaders.RenderState;
import org.saar.lwjgl.opengl.shaders.ShadersProgram;
import org.saar.lwjgl.opengl.shaders.uniforms.UniformProperty;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractRenderer<T> implements Renderer {

    private final ShadersProgram shadersProgram;

    private final List<UniformProperty<T>> perRenderUniforms = new LinkedList<>();
    private final List<UniformProperty<T>> perInstanceUniforms = new LinkedList<>();

    public AbstractRenderer(ShadersProgram shadersProgram) {
        this.shadersProgram = shadersProgram;
    }

    public void addPerRenderUniform(UniformProperty<T> uniform) {
        this.shadersProgram.bind();
        uniform.initialize(this.shadersProgram);
        perRenderUniforms.add(uniform);
    }

    public void addPerInstanceUniform(UniformProperty<T> uniform) {
        this.shadersProgram.bind();
        uniform.initialize(this.shadersProgram);
        perInstanceUniforms.add(uniform);
    }

    public void updatePerRenderUniforms(RenderState<T> state) {
        for (UniformProperty<T> uniform : perRenderUniforms) {
            uniform.load(state);
        }
    }

    public void updatePerInstanceUniforms(RenderState<T> state) {
        for (UniformProperty<T> uniform : perInstanceUniforms) {
            uniform.load(state);
        }
    }

}
