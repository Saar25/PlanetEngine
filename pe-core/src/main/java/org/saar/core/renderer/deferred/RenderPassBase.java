package org.saar.core.renderer.deferred;

import org.saar.core.renderer.Renderers;
import org.saar.core.renderer.uniforms.UniformUpdater;
import org.saar.core.renderer.uniforms.UniformsHelper;
import org.saar.core.renderer.uniforms.UpdatersHelper;
import org.saar.core.renderer.shaders.ShadersHelper;
import org.saar.lwjgl.opengl.shaders.Shader;
import org.saar.lwjgl.opengl.shaders.ShaderCompileException;
import org.saar.lwjgl.opengl.shaders.ShadersProgram;
import org.saar.lwjgl.opengl.shaders.uniforms.Uniform;

import java.util.List;

public abstract class RenderPassBase implements RenderPass {

    protected ShadersProgram shadersProgram;

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
