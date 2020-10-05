package org.saar.core.renderer;

import org.saar.lwjgl.opengl.shaders.uniforms.Uniform;
import org.saar.utils.reflection.FieldsLocator;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public final class UniformPropertiesLocator {

    private final FieldsLocator fieldsLocator;

    public UniformPropertiesLocator(Object object) {
        this.fieldsLocator = new FieldsLocator(object);
    }

    public List<Uniform> getUniforms() {
        final List<Field> fields = this.fieldsLocator.getAnnotatedFields(UniformProperty.class);
        return this.fieldsLocator.getValues(fields).stream().map(
                Uniform.class::cast).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public <T> List<InstanceUniformUpdater<T>> getInstanceUniformUpdaters() {
        final List<Field> fields = this.fieldsLocator.getAnnotatedFields(UniformUpdater.class);
        return this.fieldsLocator.getValues(fields).stream()
                .filter(InstanceUniformUpdater.class::isInstance)
                .map(u -> (InstanceUniformUpdater<T>) u)
                .collect(Collectors.toList());
    }
}
