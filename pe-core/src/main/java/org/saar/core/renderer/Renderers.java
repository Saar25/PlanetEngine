package org.saar.core.renderer;

import org.saar.lwjgl.opengl.shaders.uniforms.UniformProperty;

import java.util.List;

public final class Renderers {

    private Renderers() {
        throw new AssertionError("Cannot create instance of class " + getClass().getSimpleName());
    }

    public static List<UniformProperty<?>> findUniformProperties(Object renderer) {
        return new UniformPropertiesLocator(renderer).getUniformProperties();
    }
}
