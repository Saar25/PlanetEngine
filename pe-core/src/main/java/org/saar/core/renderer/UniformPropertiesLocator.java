package org.saar.core.renderer;

import org.saar.core.renderer.annotations.InstanceUniformProperty;
import org.saar.core.renderer.annotations.StageUniformProperty;
import org.saar.lwjgl.opengl.shaders.uniforms.UniformProperty;
import org.saar.utils.reflection.FieldsLocator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public final class UniformPropertiesLocator {

    private final FieldsLocator fieldsLocator;

    public UniformPropertiesLocator(Object object) {
        this.fieldsLocator = new FieldsLocator(object);
    }

    public List<UniformProperty<?>> getInstanceUniformProperties() {
        return getUniformProperties(InstanceUniformProperty.class);
    }

    public List<UniformProperty<?>> getStageUniformProperties() {
        return getUniformProperties(StageUniformProperty.class);
    }

    public List<UniformProperty<?>> getUniformProperties(Class<? extends Annotation> annotation) {
        final List<Field> fields = fieldsLocator.getAnnotatedFields(annotation);
        return fieldsLocator.getValues(fields).stream().map(v -> (UniformProperty<?>) v).collect(Collectors.toList());
    }


}
