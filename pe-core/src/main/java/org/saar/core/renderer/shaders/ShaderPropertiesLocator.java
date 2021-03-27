package org.saar.core.renderer.shaders;

import org.saar.core.util.reflection.FieldsLocator;
import org.saar.lwjgl.opengl.shaders.Shader;
import org.saar.lwjgl.opengl.shaders.ShaderType;

import java.util.List;

public final class ShaderPropertiesLocator {

    private final FieldsLocator fieldsLocator;

    public ShaderPropertiesLocator(Object object) {
        this.fieldsLocator = new FieldsLocator(object);
    }

    public List<Shader> getShaders() {
        return this.fieldsLocator.getFilteredValues(
                Shader.class, ShaderProperty.class);
    }

    public List<Shader> getVertexShaders() {
        return getShadersByType(ShaderType.VERTEX);
    }

    public List<Shader> getFragmentShaders() {
        return getShadersByType(ShaderType.FRAGMENT);
    }

    private List<Shader> getShadersByType(ShaderType type) {
        return this.fieldsLocator.getFilteredValues(Shader.class, ShaderProperty.class,
                f -> f.getAnnotation(ShaderProperty.class).value() == type);
    }
}
