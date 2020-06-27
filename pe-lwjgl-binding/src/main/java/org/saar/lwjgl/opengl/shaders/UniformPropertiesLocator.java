package org.saar.lwjgl.opengl.shaders;

import org.saar.lwjgl.opengl.shaders.annotations.InstanceUniformProperty;
import org.saar.lwjgl.opengl.shaders.annotations.StageUniformProperty;
import org.saar.lwjgl.opengl.shaders.uniforms.UniformProperty;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class UniformPropertiesLocator {

    private final ShadersProgram<?> shadersProgram;

    public UniformPropertiesLocator(ShadersProgram<?> shadersProgram) {
        this.shadersProgram = shadersProgram;
    }

    public List<UniformProperty<?>> getInstanceUniformProperties() {
        final List<Field> annotatedFields = getAnnotatedFields(
                getAllFields(), InstanceUniformProperty.class);
        return mapToUniformProperties(annotatedFields);
    }

    public List<UniformProperty<?>> getStageUniformProperties() {
        final List<Field> annotatedFields = getAnnotatedFields(
                getAllFields(), StageUniformProperty.class);
        return mapToUniformProperties(annotatedFields);
    }

    public List<UniformProperty<?>> getUniformProperties() {
        return mapToUniformProperties(getAllFields());
    }

    private List<Field> getAllFields() {
        final ArrayList<Field> fields = new ArrayList<>();
        final Class<?> iClass = this.shadersProgram.getClass();
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
                final Object uniform = field.get(this.shadersProgram);
                uniforms.add((UniformProperty<?>) uniform);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return uniforms;
    }

}
