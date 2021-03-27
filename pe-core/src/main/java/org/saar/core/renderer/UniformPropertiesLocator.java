package org.saar.core.renderer;

import org.saar.core.util.reflection.FieldsLocator;
import org.saar.lwjgl.opengl.shaders.uniforms.Uniform;

import java.util.List;
import java.util.stream.Collectors;

public final class UniformPropertiesLocator {

    private final FieldsLocator fieldsLocator;

    public UniformPropertiesLocator(Object object) {
        this.fieldsLocator = new FieldsLocator(object);
    }

    public List<Uniform> getUniforms() {
        return this.fieldsLocator.getFilteredValues(Uniform.class, UniformProperty.class);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> List<UniformUpdater<T>> getUniformUpdaters() {
        final List<UniformUpdater> values = this.fieldsLocator.getFilteredValues(
                UniformUpdater.class, UniformUpdaterProperty.class);
        return values.stream().map(value -> (UniformUpdater<T>) value)
                .collect(Collectors.toList());
    }
}
