package org.saar.core.renderer.deferred;

import org.saar.core.renderer.UniformUpdater;
import org.saar.core.renderer.UpdatersHelper;
import org.saar.core.renderer.Renderers;
import org.saar.core.renderer.UniformsHelper;
import org.saar.lwjgl.opengl.shaders.ShadersProgram;
import org.saar.lwjgl.opengl.shaders.uniforms.Uniform;

import java.util.List;

public abstract class RenderPassBase implements RenderPass {

    private final ShadersProgram shadersProgram;

    public RenderPassBase(ShadersProgram shadersProgram) {
        this.shadersProgram = shadersProgram;
    }

    protected UniformsHelper buildHelper(UniformsHelper helper) {
        for (Uniform uniform : Renderers.findUniforms(this)) {
            helper = helper.addUniform(uniform);
        }

        this.shadersProgram.bind();
        helper.initialize(this.shadersProgram);

        return helper;
    }

    protected <T> UpdatersHelper<T> buildHelper(UpdatersHelper<T> helper) {
        final List<UniformUpdater<T>> uniformsUpdaters =
                Renderers.findUniformsUpdaters(this);

        for (UniformUpdater<T> uniform : uniformsUpdaters) {
            helper = helper.addUpdater(uniform);
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
