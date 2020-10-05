package org.saar.core.renderer;

import org.saar.lwjgl.opengl.shaders.uniforms2.Uniform;

import java.util.List;

public final class Renderers {

    private Renderers() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    public static List<Uniform> findUniforms(Object renderer) {
        return new UniformPropertiesLocator(renderer).getUniforms();
    }

    public static List<StageUniformUpdater> findStageUniformsUpdaters(Object renderer) {
        return new UniformPropertiesLocator(renderer).getStageUniformUpdaters();
    }

    public static <T> List<InstanceUniformUpdater<T>> findInstanceUniformsUpdaters(Object renderer) {
        return new UniformPropertiesLocator(renderer).getInstanceUniformUpdaters();
    }
}
