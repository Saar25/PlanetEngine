package org.saar.core.renderer;

import org.saar.core.renderer.annotations.InstanceUniformProperty;
import org.saar.core.renderer.annotations.StageUniformProperty;
import org.saar.lwjgl.opengl.shaders.uniforms.UniformProperty;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class UniformPropertiesLocator {

    private final Object object;

    public UniformPropertiesLocator(Object object) {
        this.object = object;
    }

    public List<UniformProperty<?>> getInstanceUniformProperties() {
        return getUniformProperties(InstanceUniformProperty.class);
    }

    public List<UniformProperty<?>> getStageUniformProperties() {
        return getUniformProperties(StageUniformProperty.class);
    }

    private List<UniformProperty<?>> getUniformProperties(Class<? extends Annotation> annotation) {
        return mapToUniformProperties(getAnnotatedFields(getAllFields(), annotation));
    }

    private List<Field> getAllFields() {
        final ArrayList<Field> fields = new ArrayList<>();
        final Class<?> iClass = this.object.getClass();
        Collections.addAll(fields, iClass.getDeclaredFields());
        return fields;
    }

    private List<Field> getAnnotatedFields(List<Field> fields, Class<? extends Annotation> annotation) {
        return fields.stream().filter(field -> field.isAnnotationPresent(annotation)).collect(Collectors.toList());
    }

    private List<UniformProperty<?>> mapToUniformProperties(List<Field> fields) {
        final List<UniformProperty<?>> uniforms = new ArrayList<>(fields.size());

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                final Object uniform = field.get(this.object);
                uniforms.add((UniformProperty<?>) uniform);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return uniforms;
    }

}
