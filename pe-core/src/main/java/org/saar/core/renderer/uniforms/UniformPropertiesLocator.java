package org.saar.core.renderer.uniforms;

import org.saar.core.util.reflection.FieldsLocator;
import org.saar.lwjgl.opengl.shaders.uniforms.Uniform;

import java.util.List;

public final class UniformPropertiesLocator {

    private final FieldsLocator fieldsLocator;

    public UniformPropertiesLocator(Object object) {
        this.fieldsLocator = new FieldsLocator(object);
    }

    public List<Uniform> getUniforms() {
        return this.fieldsLocator.getFilteredValues(Uniform.class, UniformProperty.class);
    }

    public List<Uniform> getUniformsByTrigger(UniformTrigger trigger) {
        return this.fieldsLocator.getFilteredValues(Uniform.class, UniformProperty.class,
                f -> f.getAnnotation(UniformProperty.class).value() == trigger);
    }
}
