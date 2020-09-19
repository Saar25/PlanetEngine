package org.saar.lwjgl.opengl.shaders.uniforms;

import org.saar.lwjgl.opengl.shaders.InstanceRenderState;
import org.saar.lwjgl.opengl.shaders.ShadersProgram;
import org.saar.lwjgl.opengl.shaders.StageRenderState;

import java.util.ArrayList;
import java.util.List;

public abstract class UniformArrayProperty<T> implements UniformProperty<T[]> {

    private final String name;

    public UniformArrayProperty(String name) {
        this.name = name;
    }

    @Override
    public void initialize(ShadersProgram shadersProgram) {
        for (UniformProperty<T> uniform : getUniforms()) {
            uniform.initialize(shadersProgram);
        }
    }

    @Override
    public void loadValue(T[] value) {
        for (int i = 0; i < value.length; i++) {
            getUniforms().get(i).loadValue(value[i]);
        }
    }

    public String getName() {
        return name;
    }

    protected abstract List<? extends UniformProperty<T>> getUniforms();

    private static <T> List<T> toList(String name, int length, UniformSupplier<T> supplier) {
        final List<T> list = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            final String current = name + "[" + i + "]";
            list.add(supplier.create(current, i));
        }
        return list;
    }

    public interface UniformSupplier<T> {
        T create(String name, int index);
    }

    public static class Stage<T> extends UniformArrayProperty<T> implements UniformProperty.Stage<T[]> {

        private final List<UniformProperty.Stage<T>> uniforms;

        public Stage(String name, int length, UniformSupplier<UniformProperty.Stage<T>> supplier) {
            super(name);
            this.uniforms = toList(name, length, supplier);
        }

        @Override
        public List<UniformProperty.Stage<T>> getUniforms() {
            return this.uniforms;
        }

        @Override
        public void loadOnStage(StageRenderState state) {
            for (UniformProperty.Stage<T> uniform : getUniforms()) {
                uniform.loadOnStage(state);
            }
        }
    }

    public static class Instance<T, E> extends UniformArrayProperty<E> implements UniformProperty.Instance<T, E[]> {

        private final List<UniformProperty.Instance<T, E>> uniforms;

        public Instance(String name, int length, UniformSupplier<UniformProperty.Instance<T, E>> supplier) {
            super(name);
            this.uniforms = toList(name, length, supplier);
        }

        @Override
        public List<UniformProperty.Instance<T, E>> getUniforms() {
            return this.uniforms;
        }

        @Override
        public void loadOnInstance(InstanceRenderState<T> state) {
            for (UniformProperty.Instance<T, E> uniform : getUniforms()) {
                uniform.loadOnInstance(state);
            }
        }
    }
}
