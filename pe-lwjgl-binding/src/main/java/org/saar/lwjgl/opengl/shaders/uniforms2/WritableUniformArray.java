package org.saar.lwjgl.opengl.shaders.uniforms2;

import org.saar.lwjgl.opengl.shaders.ShadersProgram;

import java.util.ArrayList;
import java.util.List;

public class WritableUniformArray<T> implements WritableUniform<T[]> {

    private final String name;
    private final int size;

    private final UniformValueSupplier<T> supplier;

    private final List<WritableUniform<T>> uniforms = new ArrayList<>();

    private int currentSize = 0;

    public WritableUniformArray(String name, int size, UniformValueSupplier<T> supplier) {
        this.name = name;
        this.size = size;
        this.supplier = supplier;
        this.currentSize = size;
    }

    @Override
    public void initialize(ShadersProgram shadersProgram) {
        this.uniforms.clear();
        for (int i = 0; i < this.size; i++) {
            final String current = this.name + "[" + i + "]";
            final UniformValue<T> uniform = this.supplier.supply(current, i);
            uniform.initialize(shadersProgram);
            this.uniforms.add(uniform);
        }
    }

    @Override
    public void load() {
        for (Uniform uniform : this.uniforms) {
            uniform.load();
        }
    }

    @Override
    public void setValue(T[] values) {
        this.currentSize = values.length;
        for (int i = 0; i < this.currentSize; i++) {
            this.uniforms.get(i).setValue(values[i]);
        }
    }

    public interface UniformValueSupplier<T> {
        UniformValue<T> supply(String name, int index);
    }

}
