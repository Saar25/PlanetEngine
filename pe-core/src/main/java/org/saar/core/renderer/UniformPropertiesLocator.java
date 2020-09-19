package org.saar.core.renderer;

import org.saar.lwjgl.opengl.shaders.uniforms.UniformProperty;
import org.saar.utils.reflection.FieldsLocator;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public final class UniformPropertiesLocator {

    private final FieldsLocator fieldsLocator;

    public UniformPropertiesLocator(Object object) {
        this.fieldsLocator = new FieldsLocator(object);
    }

    public List<UniformProperty<?>> getUniformProperties() {
        final List<Field> fields = fieldsLocator.getAnnotatedFields(AUniformProperty.class);
        final List<Object> values = fieldsLocator.getValues(fields);
        return values.stream().map(v -> (UniformProperty<?>) v).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public <T> List<UniformProperty.Stage<T>> getStageUniformProperties() {
        return getUniformProperties(UniformProperty.Stage.class).stream()
                .map(u -> (UniformProperty.Stage<T>) u).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public <T, E> List<UniformProperty.Instance<T, E>> getInstanceUniformProperties() {
        return getUniformProperties(UniformProperty.Instance.class).stream()
                .map(u -> (UniformProperty.Instance<T, E>) u).collect(Collectors.toList());
    }

    private <T> List<T> getUniformProperties(Class<T> tClass) {
        final List<Field> fields = fieldsLocator.getAnnotatedFields(AUniformProperty.class);
        final List<Object> values = fieldsLocator.getValues(fields);
        return values.stream().filter(tClass::isInstance)
                .map(tClass::cast).collect(Collectors.toList());
    }


}
