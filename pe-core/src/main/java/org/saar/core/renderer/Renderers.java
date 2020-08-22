package org.saar.core.renderer;

import org.saar.lwjgl.opengl.shaders.uniforms.UniformProperty;

import java.util.List;

public final class Renderers {

    private Renderers() {
        throw new AssertionError("Cannot create instance of class " + getClass().getSimpleName());
    }

    public static List<UniformProperty<?>> findStageUniformProperties(Renderer renderer) {
        return new UniformPropertiesLocator(renderer).getStageUniformProperties();
    }

    public static List<UniformProperty<?>> findInstanceUniformProperties(Renderer renderer) {
        return new UniformPropertiesLocator(renderer).getInstanceUniformProperties();
    }
}
