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

    public static <T> List<UniformProperty.Stage<T>> findStageUniformProperties(Object renderer) {
        return new UniformPropertiesLocator(renderer).getStageUniformProperties();
    }

    public static <T, E> List<UniformProperty.Instance<T, E>> findInstanceUniformProperties(Object renderer) {
        return new UniformPropertiesLocator(renderer).getInstanceUniformProperties();
    }
}
