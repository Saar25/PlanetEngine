package org.saar.core.renderer;

import org.saar.lwjgl.opengl.shaders.uniforms2.Uniform;
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

    public List<StageUniformUpdater> getStageUniformUpdaters() {
        final List<Field> fields = this.fieldsLocator.getAnnotatedFields(UniformUpdater.class);
        return this.fieldsLocator.getValues(fields).stream()
                .filter(StageUniformUpdater.class::isInstance)
                .map(u -> (StageUniformUpdater) u)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public <T> List<InstanceUniformUpdater<T>> getInstanceUniformUpdaters() {
        final List<Field> fields = this.fieldsLocator.getAnnotatedFields(UniformUpdater.class);
        return this.fieldsLocator.getValues(fields).stream()
                .filter(InstanceUniformUpdater.class::isInstance)
                .map(u -> (InstanceUniformUpdater<T>) u)
                .collect(Collectors.toList());
    }

    public List<org.saar.lwjgl.opengl.shaders.uniforms.UniformProperty> getUniformProperties() {
        final List<Field> fields = fieldsLocator.getAnnotatedFields(UniformProperty.class);
        final List<Object> values = fieldsLocator.getValues(fields);
        return values.stream().map(v -> (org.saar.lwjgl.opengl.shaders.uniforms.UniformProperty) v).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public <T> List<org.saar.lwjgl.opengl.shaders.uniforms.UniformProperty.Stage<T>> getStageUniformProperties() {
        return getUniformProperties(org.saar.lwjgl.opengl.shaders.uniforms.UniformProperty.Stage.class).stream()
                .map(u -> (org.saar.lwjgl.opengl.shaders.uniforms.UniformProperty.Stage<T>) u).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public <T, E> List<org.saar.lwjgl.opengl.shaders.uniforms.UniformProperty.Instance<T, E>> getInstanceUniformProperties() {
        return getUniformProperties(org.saar.lwjgl.opengl.shaders.uniforms.UniformProperty.Instance.class).stream()
                .map(u -> (org.saar.lwjgl.opengl.shaders.uniforms.UniformProperty.Instance<T, E>) u).collect(Collectors.toList());
    }

    private <T> List<T> getUniformProperties(Class<T> tClass) {
        final List<Field> fields = fieldsLocator.getAnnotatedFields(UniformProperty.class);
        final List<Object> values = fieldsLocator.getValues(fields);
        return values.stream().filter(tClass::isInstance)
                .map(tClass::cast).collect(Collectors.toList());
    }


}
