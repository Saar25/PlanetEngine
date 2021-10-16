package org.saar.core.renderer;

import org.saar.core.renderer.shaders.ShaderPropertiesLocator;
import org.saar.core.renderer.uniforms.UniformPropertiesLocator;
import org.saar.core.renderer.uniforms.UniformTrigger;
import org.saar.lwjgl.opengl.shaders.Shader;
import org.saar.lwjgl.opengl.shaders.uniforms.UniformContainer;

import java.util.List;

public final class Renderers {

    private Renderers() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    public static List<Shader> findShaders(Object renderer) {
        return new ShaderPropertiesLocator(renderer).getShaders();
    }

    public static List<Shader> findVertexShaders(Object renderer) {
        return new ShaderPropertiesLocator(renderer).getVertexShaders();
    }

    public static List<Shader> findFragmentShaders(Object renderer) {
        return new ShaderPropertiesLocator(renderer).getFragmentShaders();
    }

    public static List<UniformContainer> findUniforms(Object renderer) {
        return new UniformPropertiesLocator(renderer).getUniforms();
    }

    public static List<UniformContainer> findUniformsByTrigger(Object renderer, UniformTrigger trigger) {
        return new UniformPropertiesLocator(renderer).getUniformsByTrigger(trigger);
    }
}
