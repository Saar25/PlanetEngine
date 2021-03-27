package org.saar.core.renderer;

import org.saar.lwjgl.opengl.shaders.uniforms.Uniform;

import java.util.List;

public final class Renderers {

    private Renderers() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    public static List<Uniform> findUniforms(Object renderer) {
        return new UniformPropertiesLocator(renderer).getUniforms();
    }

    public static <T> List<UniformUpdater<T>> findUniformsUpdaters(Object renderer) {
        return new UniformPropertiesLocator(renderer).getUniformUpdaters();
    }
}
