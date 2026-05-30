package org.saar.core.renderer;

import org.saar.core.renderer.uniforms.UniformPropertiesLocator;
import org.saar.core.renderer.uniforms.UniformTrigger;
import org.saar.lwjgl.opengl.shader.uniforms.UniformContainer;

import java.util.List;

public final class Renderers {

    private Renderers() {
        throw new AssertionError("Cannot create instance of class " + getClass().getSimpleName());
    }

    public static List<UniformContainer> findUniformsByTrigger(Object renderer, UniformTrigger trigger) {
        return new UniformPropertiesLocator(renderer).getUniformsByTrigger(trigger);
    }
}
