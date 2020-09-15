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


}
