package org.saar.core.renderer.deferred;

import org.saar.core.renderer.Renderers;
import org.saar.core.renderer.UniformsHelper;
import org.saar.lwjgl.opengl.shaders.ShadersProgram;
import org.saar.lwjgl.opengl.shaders.uniforms.UniformProperty;

import java.util.List;

public abstract class RenderPassBase implements RenderPass {

    private final ShadersProgram shadersProgram;

    public RenderPassBase(ShadersProgram shadersProgram) {
        this.shadersProgram = shadersProgram;
    }

    protected final void init() {
        this.shadersProgram.bind();
        for (UniformProperty<?> uniform : Renderers.findUniformProperties(this)) {
            uniform.initialize(this.shadersProgram);
        }
    }

    protected <T> UniformsHelper<T> buildHelper(UniformsHelper<T> helper) {
        this.shadersProgram.bind();

        for (UniformProperty<?> uniform : Renderers.findUniformProperties(this)) {
            uniform.initialize(this.shadersProgram);
        }

        final List<UniformProperty.Stage<T>> stage =
                Renderers.findStageUniformProperties(this);
        for (UniformProperty.Stage<T> uniform : stage) {
            uniform.initialize(this.shadersProgram);
            helper = helper.addUniform(uniform);
        }

        final List<UniformProperty.Instance<T, Object>> instance =
                Renderers.findInstanceUniformProperties(this);
        for (UniformProperty.Instance<T, ?> uniform : instance) {
            uniform.initialize(this.shadersProgram);
            helper = helper.addUniform(uniform);
        }

        return helper;
    }

    @Override
    public void render(DeferredRenderingBuffers buffers) {
        this.shadersProgram.bind();

        onRender(buffers);

        this.shadersProgram.unbind();
    }

    @Override
    public void delete() {
        this.shadersProgram.delete();
    }

    protected abstract void onRender(DeferredRenderingBuffers buffers);
}
