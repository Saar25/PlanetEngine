package org.saar.lwjgl.opengl.shaders.uniforms;

import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;

public abstract class TextureUniform extends UniformBase implements Uniform {

    @Override
    public void doInitialize() {
        GL20.glUniform1i(getLocation(), getUnit());
    }

    @Override
    public final void doLoad() {
        getUniformValue().bind(getUnit());
    }

    public abstract ReadOnlyTexture getUniformValue();

    public abstract int getUnit();
}
