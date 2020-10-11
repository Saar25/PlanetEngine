package org.saar.lwjgl.opengl.shaders.uniforms;

import org.saar.lwjgl.opengl.shaders.ShadersProgram;

import java.util.ArrayList;
import java.util.List;

public class UniformArray implements Uniform {

    private final String name;
    private final int size;

    private final UniformSupplier supplier;

    private final List<Uniform> uniforms = new ArrayList<>();

    public UniformArray(String name, int size, UniformSupplier supplier) {
        this.name = name;
        this.size = size;
        this.supplier = supplier;
    }

    @Override
    public void initialize(ShadersProgram shadersProgram) {
        this.uniforms.clear();
        for (int i = 0; i < this.size; i++) {
            final String current = this.name + "[" + i + "]";
            final Uniform uniform = this.supplier.supply(current, i);
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

    public interface UniformSupplier {
        Uniform supply(String name, int index);
    }

}
