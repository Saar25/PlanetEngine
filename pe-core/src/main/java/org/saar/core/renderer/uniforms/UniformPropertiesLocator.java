package org.saar.core.renderer.uniforms;

import org.saar.core.util.reflection.FieldsLocator;
import org.saar.lwjgl.opengl.shader.uniforms.UniformContainer;

import java.util.List;

public final class UniformPropertiesLocator {

    private final FieldsLocator fieldsLocator;

    public UniformPropertiesLocator(Object object) {
        this.fieldsLocator = new FieldsLocator(object);
    }

    public List<UniformContainer> getUniforms() {
        return this.fieldsLocator.getFilteredValues(UniformContainer.class, UniformProperty.class);
    }

    public List<UniformContainer> getUniformsByTrigger(UniformTrigger trigger) {
        return this.fieldsLocator.getFilteredValues(UniformContainer.class, UniformProperty.class,
                f -> f.getAnnotation(UniformProperty.class).value() == trigger);
    }
}
