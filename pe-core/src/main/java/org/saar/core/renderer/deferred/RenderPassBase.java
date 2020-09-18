package org.saar.core.renderer.deferred;

import org.saar.core.renderer.Renderers;
import org.saar.lwjgl.opengl.shaders.ShadersProgram;
import org.saar.lwjgl.opengl.shaders.uniforms.UniformProperty;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;

public abstract class RenderPassBase implements RenderPass {

    private final ShadersProgram shadersProgram;

    public RenderPassBase(ShadersProgram shadersProgram) {
        this.shadersProgram = shadersProgram;
    }

    protected final void init() {
        for (UniformProperty<?> uniform : Renderers.findUniformProperties(this)) {
            uniform.initialize(this.shadersProgram);
        }
    }

    @Override
    public void render(ReadOnlyTexture image) {
        this.shadersProgram.bind();

        onRender(image);

        this.shadersProgram.unbind();
    }

    @Override
    public void delete() {
        this.shadersProgram.delete();
    }

    protected abstract void onRender(ReadOnlyTexture image);
}
